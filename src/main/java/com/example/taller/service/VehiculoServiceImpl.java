package com.example.taller.service;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.taller.dto.CrearVehiculoParaPropietarioDTO;
import com.example.taller.dto.VehiculoDTO;
import com.example.taller.model.Propietario;
import com.example.taller.model.Vehiculo;
import com.example.taller.repository.VehiculoRepository;
import com.example.taller.repository.PropietarioRepository; // Cambia esto según tu estructura de paquetes

@Service
public class VehiculoServiceImpl implements VehiculoService {
      @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PropietarioRepository propietarioRepository;

     @Override
    public List<Vehiculo> obtenerVehiculosPorDni(String dni) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByPropietarioDni(dni);
    
        if (vehiculos.isEmpty()) {
            throw new RuntimeException("No se encontraron vehículos para el propietario con DNI: " + dni);
        }
        
        return vehiculos;
    }

    @Override
    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public Vehiculo obtenerVehiculoPorPatente(String patente) {
        return vehiculoRepository.findById(patente).orElse(null);
    }

    @Override
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return vehiculoRepository.findAll();
    }

    @Override
    public void eliminarVehiculo(String patente) {
        vehiculoRepository.deleteById(patente);
    }

    @Transactional(propagation = Propagation.REQUIRED)
public Vehiculo agregarVehiculoAPropietario(String dni, CrearVehiculoParaPropietarioDTO vehiculoDTO) {
    // Buscar al propietario en la base de datos por DNI
    Propietario propietario = propietarioRepository.findById(dni)
            .orElseThrow(() -> new RuntimeException("Propietario con DNI " + dni + " no encontrado"));

    // Buscar si el vehículo ya existe en la base de datos
    Optional<Vehiculo> vehiculoEnBD = vehiculoRepository.findById(vehiculoDTO.getPatente());

    Vehiculo nuevoVehiculo;

    if (vehiculoEnBD.isPresent()) {
        // Si el vehículo ya existe, verifica si está asociado al mismo propietario
        nuevoVehiculo = vehiculoEnBD.get();
        
        // Si el vehículo ya está asociado a este propietario, lanzar error
        if (nuevoVehiculo.getPropietario() != null && nuevoVehiculo.getPropietario().equals(propietario)) {
            throw new RuntimeException("El vehículo ya está registrado para este propietario.");
        }

        // Detach para asegurar que no haya conflicto con la sesión actual
        entityManager.detach(nuevoVehiculo);
        
        // Actualizar la asociación del vehículo con el propietario
        nuevoVehiculo.setPropietario(propietario);
    } else {
        // Si el vehículo no existe, creamos uno nuevo
        nuevoVehiculo = Vehiculo.builder()
                .patente(vehiculoDTO.getPatente())
                .marca(vehiculoDTO.getMarca())
                .modelo(vehiculoDTO.getModelo())
                .propietario(propietario) // Asignamos el propietario
                .build();
    }

    // Agregar el vehículo al propietario
    propietario.addVehiculo(nuevoVehiculo);

    // Guardar los cambios en el vehículo y en el propietario
    vehiculoRepository.save(nuevoVehiculo);
    propietarioRepository.save(propietario);

    return nuevoVehiculo;
}
}

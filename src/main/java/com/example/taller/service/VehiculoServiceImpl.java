package com.example.taller.service;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.taller.generics.exception.VehiculoYaRegistradoException;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioRepository;
import com.example.taller.vehiculos.application.CrearVehiculoParaPropietarioRequestDTO;
import com.example.taller.vehiculos.application.NuevoPropietarioRequestDTO;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoRepository;
import com.example.taller.vehiculos.infrastructure.port.VehiculoServicePort;

@Service
public class VehiculoServiceImpl implements VehiculoServicePort {
      @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PropietarioRepository propietarioRepository;

     @Override
    public List<VehiculoEntity> obtenerVehiculosPorDni(String dni) {
        List<VehiculoEntity> vehiculos = vehiculoRepository.findByPropietarioDni(dni);
    
        if (vehiculos.isEmpty()) {
            throw new RuntimeException("No se encontraron vehículos para el propietario con DNI: " + dni);
        }
        
        return vehiculos;
    }

    @Override
    public VehiculoEntity crearVehiculo(VehiculoEntity vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public VehiculoEntity obtenerVehiculoPorPatente(String patente) {
        return vehiculoRepository.findById(patente).orElse(null);
    }

    @Override
    public List<VehiculoEntity> obtenerTodosLosVehiculos() {
        return vehiculoRepository.findAll();
    }

    @Override
    public void eliminarVehiculo(String patente) {
        vehiculoRepository.deleteById(patente);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public VehiculoEntity agregarVehiculoAPropietario(String dni, CrearVehiculoParaPropietarioRequestDTO vehiculoDTO) {
        // Buscar al propietario en la base de datos por DNI
        PropietarioEntity propietario = propietarioRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Propietario con DNI " + dni + " no encontrado"));
    
        // Buscar si el vehículo ya existe en la base de datos
        Optional<VehiculoEntity> vehiculoEnBD = vehiculoRepository.findById(vehiculoDTO.getPatente());
    
        // Declarar la variable nuevoVehiculo
        VehiculoEntity nuevoVehiculo;
    
        if (vehiculoEnBD.isPresent()) {
            // Si el vehículo ya existe, verificar si está asociado a un propietario
            VehiculoEntity vehiculoExistente = vehiculoEnBD.get();
    
            // Si el vehículo ya tiene un propietario diferente, lanzar error
            if (vehiculoExistente.getPropietario() != null && !vehiculoExistente.getPropietario().equals(propietario)) {
                throw new VehiculoYaRegistradoException("El vehículo con patente " + vehiculoDTO.getPatente() + " ya está registrado para otro propietario.");
            }
    
            // Si el vehículo ya está asociado a este propietario, lanzar error
            if (vehiculoExistente.getPropietario().equals(propietario)) {
                throw new VehiculoYaRegistradoException("El vehículo con patente " + vehiculoDTO.getPatente() + " ya está registrado para este propietario.");
            }
    
            // Detach para asegurar que no haya conflicto con la sesión actual
            entityManager.detach(vehiculoExistente);
            
            // Actualizar la asociación del vehículo con el propietario
            vehiculoExistente.setPropietario(propietario);
            propietario.addVehiculo(vehiculoExistente);
    
            // Asignar el vehículo existente a nuevoVehiculo para devolverlo
            nuevoVehiculo = vehiculoExistente;
        } else {
            // Si el vehículo no existe, creamos uno nuevo
            nuevoVehiculo = VehiculoEntity.builder()
                    .patente(vehiculoDTO.getPatente())
                    .marca(vehiculoDTO.getMarca())
                    .modelo(vehiculoDTO.getModelo())
                    .propietario(propietario) // Asignamos el propietario
                    .build();
    
            // Agregar el vehículo al propietario
            propietario.addVehiculo(nuevoVehiculo);
            
            // Guardar el vehículo en la base de datos
            vehiculoRepository.save(nuevoVehiculo);
        }
    
        // Guardar los cambios en el propietario (esto asegura la relación bidireccional)
        propietarioRepository.save(propietario);
    
        return nuevoVehiculo; // Devolver el vehículo (nuevo o existente)
    }

    @Override
    public PropietarioEntity crearPropietario(NuevoPropietarioRequestDTO propietarioDTO) {
        // Convertir NuevoPropietarioDTO a Propietario
        PropietarioEntity propietario = new PropietarioEntity();
        propietario.setDni(propietarioDTO.getDni());
        propietario.setNombre(propietarioDTO.getNombre());
        propietario.setApellido(propietarioDTO.getApellido());
        propietario.setDireccion(propietarioDTO.getDireccion());
        propietario.setTelefono(propietarioDTO.getTelefono());

        // Guardar propietario
        return propietarioRepository.save(propietario);
    }
    
    
}

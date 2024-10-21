package com.example.taller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.dto.VehiculoDTO;
import com.example.taller.model.Propietario;
import com.example.taller.model.Vehiculo;
import com.example.taller.repository.VehiculoRepository;
import com.example.taller.repository.PropietarioRepository; // Cambia esto según tu estructura de paquetes

@Service
public class VehiculoServiceImpl implements VehiculoService {
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

    @Override
    public Vehiculo agregarVehiculoAPropietario(String dni, VehiculoDTO vehiculoDTO) {
        // Buscar al propietario en la base de datos por DNI
        Propietario propietario = propietarioRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Propietario con DNI " + dni + " no encontrado"));

        // Crear un nuevo objeto Vehiculo con la información proporcionada
        Vehiculo nuevoVehiculo = Vehiculo.builder()
                .patente(vehiculoDTO.getPatente())
                .marca(vehiculoDTO.getMarca())
                .modelo(vehiculoDTO.getModelo())
                .propietario(propietario) // Relacionar con el propietario
                .build();

        // Agregar el vehículo al propietario
        propietario.addVehiculo(nuevoVehiculo);

        // Guardar el vehículo en la base de datos
        vehiculoRepository.save(nuevoVehiculo);

        // Guardar los cambios en el propietario (esto asegura la relación bidireccional)
        propietarioRepository.save(propietario);

        return nuevoVehiculo;
    }
}

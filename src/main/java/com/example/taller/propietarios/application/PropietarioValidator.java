package com.example.taller.propietarios.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.taller.propietarios.infrastructure.adapter.PropietarioRepository;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoRepository;
import com.example.taller.vehiculos.infrastructure.port.VehiculoServicePort;

@Component
public class PropietarioValidator {

 // Inyectar los repositorios para verificar existencia
    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private VehiculoServicePort vehiculoServicePort;

    public void validarPropietarioYVehiculo(PropietarioRequestDTO propietarioDTO, VehiculoRequestDTO vehiculoDTO) {
        // Validar campos obligatorios del propietario
        if (propietarioDTO.getDni() == null || propietarioDTO.getDni().isEmpty()) {
            throw new IllegalArgumentException("El DNI del propietario no puede estar vacío.");
        }
        if (propietarioDTO.getNombre() == null || propietarioDTO.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del propietario no puede estar vacío.");
        }
        
        // Validar si el propietario ya existe en la base de datos
        if (propietarioRepository.findByDni(propietarioDTO.getDni()).isPresent()) {
            throw new IllegalArgumentException("El propietario con el DNI " + propietarioDTO.getDni() + " ya está registrado.");
        }

        // Validar campos obligatorios del vehículo
        if (vehiculoDTO.getPatente() == null || vehiculoDTO.getPatente().isEmpty()) {
            throw new IllegalArgumentException("La patente del vehículo no puede estar vacía.");
        }
        
        // Validar si el vehículo ya existe en la base de datos
        if (vehiculoServicePort.findByPatente(vehiculoDTO.getPatente()).isPresent()) {
            throw new IllegalArgumentException("El vehículo con la patente " + vehiculoDTO.getPatente() + " ya está registrado.");
        }

        // Otras validaciones adicionales según las necesidades del negocio
    }
}

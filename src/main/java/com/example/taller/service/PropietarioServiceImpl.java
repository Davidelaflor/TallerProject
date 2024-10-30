package com.example.taller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.propietarios.application.PropietarioRequestDTO;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioRepository;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoRepository;

@Service
public class PropietarioServiceImpl implements PropietarioServicePort {
    @Autowired
    private PropietarioRepository propietarioRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public List<PropietarioEntity> listarPropietarios() {
        return propietarioRepository.findAll();
    }

    @Override
    public PropietarioEntity crearPropietarioConVehiculo(PropietarioRequestDTO propietarioDTO, VehiculoRequestDTO vehiculoDTO) {
          // Verificar si el propietario ya existe
          if (propietarioRepository.existsByDni(propietarioDTO.getDni())) {
            throw new IllegalArgumentException("El propietario con DNI " + propietarioDTO.getDni() + " ya está registrado.");
        }

        // Verificar si el vehículo ya existe
        if (vehiculoRepository.existsByPatente(vehiculoDTO.getPatente())) {
            throw new IllegalArgumentException("El vehículo con patente " + vehiculoDTO.getPatente() + " ya está registrado.");
        }
        
        PropietarioEntity entity = generatePropietarioEntity(propietarioDTO);
        VehiculoEntity vehiculoEntity = generateVehiculoEntity(vehiculoDTO);
        
        entity.addVehiculo(vehiculoEntity); 

        return propietarioRepository.save(entity);
        //return entity;
    }

    private VehiculoEntity generateVehiculoEntity(VehiculoRequestDTO dto) {
        return VehiculoEntity.builder()
                .patente(dto.getPatente())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .build();
    }

    private PropietarioEntity generatePropietarioEntity(PropietarioRequestDTO dto) {
        return PropietarioEntity.builder()
                .dni(dto.getDni())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .build();
    }

    @Override
    public PropietarioEntity obtenerPropietarioPorDni(String dni) {
        return propietarioRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));
    }

    @Override
    public void eliminarPropietario(String dni) {
        propietarioRepository.deleteById(dni);
    }

}

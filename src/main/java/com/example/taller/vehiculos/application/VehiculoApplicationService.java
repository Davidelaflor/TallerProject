package com.example.taller.vehiculos.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.generics.exception.VehiculoYaRegistradoException;
import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.vehiculos.domain.VehiculoDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;
import com.example.taller.vehiculos.infrastructure.port.VehiculoServicePort;

import jakarta.transaction.Transactional;

@Service
public class VehiculoApplicationService {
    @Autowired
    private VehiculoServicePort vehiculoService;

    @Autowired
    private PropietarioServicePort propietarioService;

    public List<VehiculoDTO> obtenerVehiculosPorDni(String dni) {
        List<VehiculoDTO> vehiculos = vehiculoService.obtenerVehiculosPorDni(dni);
        if (vehiculos.isEmpty()) {
            throw new RuntimeException("No se encontraron vehículos para el propietario con DNI: " + dni);
        }
        return vehiculos;
    }
    public List<VehiculoDTO> obtenerTodosLosVehiculos() {
        return vehiculoService.obtenerTodosLosVehiculos(); // Asegúrate de que este método existe en el puerto
    }
    @Transactional
    public void eliminarVehiculo(String patente) {
        // Llama al servicio para eliminar el vehículo por su patente
        vehiculoService.eliminarVehiculo(patente);
    }
    public VehiculoDTO crearVehiculo(VehiculoRequestDTO vehiculoDTO) {
        return vehiculoService.crearVehiculo(vehiculoDTO);
    }

    public Optional<VehiculoDTO> obtenerVehiculoPorPatente(String patente) {
        return vehiculoService.obtenerVehiculoPorPatente(patente);
    }

    @Transactional
    public VehiculoDTO agregarVehiculoAPropietario(String dni, CrearVehiculoParaPropietarioRequestDTO vehiculoDTO) {
        // Obtener el propietario en formato DTO
        PropietarioDTO propietarioDTO = propietarioService.obtenerPropietarioPorDni(dni);
        // Convertir el DTO de propietario a entidad
        PropietarioEntity propietario = convertToEntity(propietarioDTO);
    
        // Intentar obtener el vehículo por patente
        Optional<VehiculoDTO> vehiculoExistenteOpt = vehiculoService.obtenerVehiculoPorPatente(vehiculoDTO.getPatente());
        VehiculoEntity vehiculoExistente;
    
        // Manejo del vehículo existente o creación de uno nuevo
        if (vehiculoExistenteOpt.isPresent()) {
            vehiculoExistente = convertToEntity(vehiculoExistenteOpt.get());
            // Verificamos si el vehículo ya tiene un propietario diferente
            if (vehiculoExistente.getPropietario() != null 
                    && !vehiculoExistente.getPropietario().getDni().equals(propietario.getDni())) {
                throw new VehiculoYaRegistradoException("El vehículo ya está registrado para otro propietario.");
            }
        } else {
             // Si no existe, creamos uno nuevo. Crear el DTO para el nuevo vehículo.
        VehiculoRequestDTO nuevoVehiculoDTO = new VehiculoRequestDTO(
            vehiculoDTO.getPatente(),
            vehiculoDTO.getMarca(),
            vehiculoDTO.getModelo());
    
    // Ahora guarda el nuevo vehículo pasando el DTO
    vehiculoExistente = convertToEntity(vehiculoService.crearVehiculo(nuevoVehiculoDTO));
}
        // Asociar el vehículo al propietario
        propietario.addVehiculo(vehiculoExistente);
    
        // Guarda el propietario
        propietarioService.guardarPropietario(convertToDTO(propietario));
    
        // Retorna el DTO del vehículo existente
        return convertToDTO(vehiculoExistente);
    }
    

    private VehiculoEntity convertToEntity(CrearVehiculoParaPropietarioRequestDTO dto) {
        if (dto == null) {
            return null; // Manejo de nulos
        }
        return new VehiculoEntity(dto.getPatente(), dto.getMarca(), dto.getModelo());
    }
    
    private VehiculoEntity convertToEntity(VehiculoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new VehiculoEntity(dto.getPatente(), dto.getMarca(), dto.getModelo());
    }
    
    private PropietarioEntity convertToEntity(PropietarioDTO dto) {
        if (dto == null) {
            return null;
        }
        PropietarioEntity entity = new PropietarioEntity();
        entity.setDni(dto.getDni());
        entity.setNombre(dto.getNombre());
        // Rellena otros campos si es necesario
        return entity;
    }
    
    private PropietarioDTO convertToDTO(PropietarioEntity entity) {
        if (entity == null) {
            return null;
        }
        PropietarioDTO dto = new PropietarioDTO();
        dto.setDni(entity.getDni());
        dto.setNombre(entity.getNombre());
        // Rellena otros campos si es necesario
        return dto;
    }
    
    private VehiculoDTO convertToDTO(VehiculoEntity vehiculo) {
        if (vehiculo == null) {
            return null;
        }
        return new VehiculoDTO(vehiculo.getPatente(), vehiculo.getMarca(), vehiculo.getModelo(), null);
    }
    
}

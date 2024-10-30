package com.example.taller.propietarios.application;

import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import java.util.List;

import org.springframework.stereotype.Service;
 
@Service
public class PropietarioApplicationService {
    private final PropietarioServicePort propietarioServicePort;

    public PropietarioApplicationService(PropietarioServicePort propietarioServicePort) {
        this.propietarioServicePort = propietarioServicePort;

    }
       public List<PropietarioDTO> listarPropietarios() {
        return propietarioServicePort.listarPropietarios();
    }

    public PropietarioDTO crearPropietarioConVehiculo(PropietarioRequestDTO propietarioDTO, VehiculoRequestDTO vehiculoDTO) {
        validarPropietarioYVehiculo(propietarioDTO, vehiculoDTO);
        return propietarioServicePort.crearPropietarioConVehiculo(propietarioDTO, vehiculoDTO);
    }
    public PropietarioDTO obtenerPropietarioPorDni(String dni) {
        return propietarioServicePort.obtenerPropietarioPorDni(dni);
    }

    public void eliminarPropietario(String dni) {
        propietarioServicePort.eliminarPropietario(dni);
    }

    private void validarPropietarioYVehiculo(PropietarioRequestDTO propietarioDTO, VehiculoRequestDTO vehiculoDTO) {
        // Lógica de validación específica
        if (propietarioDTO.getDni() == null || propietarioDTO.getDni().isEmpty()) {
            throw new IllegalArgumentException("El DNI del propietario no puede estar vacío.");
        }
        if (vehiculoDTO.getPatente() == null || vehiculoDTO.getPatente().isEmpty()) {
            throw new IllegalArgumentException("La patente del vehículo no puede estar vacía.");
        }
        // Agregar más validaciones según las necesidades del negocio
    }
}

package com.example.taller.propietarios.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
 
@Service
public class PropietarioApplicationService {
    private final PropietarioServicePort propietarioServicePort;


    public PropietarioApplicationService(PropietarioServicePort propietarioServicePort) {
        this.propietarioServicePort = propietarioServicePort;

    }

     @Autowired
    private PropietarioValidator validator;

    public List<PropietarioDTO> listarPropietarios() {
        return propietarioServicePort.listarPropietarios();
    }

    public PropietarioDTO crearPropietarioConVehiculo(PropietarioRequestDTO propietarioDTO, VehiculoRequestDTO vehiculoDTO) {
        validator.validarPropietarioYVehiculo(propietarioDTO, vehiculoDTO);
        return propietarioServicePort.crearPropietarioConVehiculo(propietarioDTO, vehiculoDTO);
    }

    public PropietarioDTO obtenerPropietarioPorDni(String dni) {
        return propietarioServicePort.obtenerPropietarioPorDni(dni);
    }

    public void eliminarPropietario(String dni) {
        propietarioServicePort.eliminarPropietario(dni);
    }

   
}

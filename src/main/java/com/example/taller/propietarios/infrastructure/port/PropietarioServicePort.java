package com.example.taller.propietarios.infrastructure.port;

import java.util.List;

import com.example.taller.propietarios.application.PropietarioRequestDTO;
import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;

public interface PropietarioServicePort {
    PropietarioDTO crearPropietarioConVehiculo(PropietarioRequestDTO propietarioDTO, VehiculoRequestDTO vehiculoDTO);

    PropietarioDTO obtenerPropietarioPorDni(String dni);

    PropietarioDTO guardarPropietario(PropietarioDTO propietarioDTO); // Cambia a DTO

    List<PropietarioDTO> listarPropietarios();

    void eliminarPropietario(String dni);
}
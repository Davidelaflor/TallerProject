package com.example.taller.propietarios.infrastructure.port;

import java.util.List;
import java.util.Optional;

import com.example.taller.propietarios.application.PropietarioRequestDTO;
import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;

public interface PropietarioServicePort {
    PropietarioDTO crearPropietarioConVehiculo(PropietarioRequestDTO propietarioDTO, VehiculoRequestDTO vehiculoDTO);

    PropietarioDTO obtenerPropietarioPorDni(String dni);

    //PropietarioDTO guardarPropietario(PropietarioDTO propietarioDTO);

    List<PropietarioDTO> listarPropietarios();

    void eliminarPropietario(String dni);

    Optional<PropietarioEntity> findByDni(String dni);

    boolean existsByDni(String dni);

    PropietarioEntity save(PropietarioEntity propietario);

    Optional<PropietarioEntity> findById(String dni);

}

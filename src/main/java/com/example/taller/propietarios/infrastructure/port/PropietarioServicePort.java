package com.example.taller.propietarios.infrastructure.port;

import java.util.List;

import com.example.taller.propietarios.application.PropietarioRequestDTO;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.vehiculos.application.NuevoPropietarioRequestDTO;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;

public interface PropietarioServicePort {
    PropietarioEntity crearPropietarioConVehiculo(PropietarioRequestDTO propietario, VehiculoRequestDTO vehiculo);

    PropietarioEntity obtenerPropietarioPorDni(String dni);


    List<PropietarioEntity> listarPropietarios();

    void eliminarPropietario(String dni);
}

package com.example.taller.service;

import java.util.List;

import com.example.taller.dto.NuevoPropietarioDTO;
import com.example.taller.dto.PropietarioDTO;
import com.example.taller.model.Propietario;
import com.example.taller.dto.VehiculoDTO;

public interface PropietarioService {
    Propietario crearPropietarioConVehiculo(PropietarioDTO propietario, VehiculoDTO vehiculo);

    Propietario obtenerPropietarioPorDni(String dni);

Propietario crearPropietario (NuevoPropietarioDTO propietarioDTO);

    List<Propietario> listarPropietarios();

    void eliminarPropietario(String dni);
}

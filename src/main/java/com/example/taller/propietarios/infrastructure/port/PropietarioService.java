package com.example.taller.propietarios.infrastructure.port;

import java.util.List;

import com.example.taller.propietarios.application.dto.NuevoPropietarioDTO;
import com.example.taller.propietarios.application.dto.PropietarioDTO;
import com.example.taller.propietarios.domain.model.Propietario;
import com.example.taller.vehiculos.application.dto.VehiculoDTO;

public interface PropietarioService {
    Propietario crearPropietarioConVehiculo(PropietarioDTO propietario, VehiculoDTO vehiculo);

    Propietario obtenerPropietarioPorDni(String dni);


    List<Propietario> listarPropietarios();

    void eliminarPropietario(String dni);
}

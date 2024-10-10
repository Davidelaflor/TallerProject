package com.example.taller.service;

import com.example.taller.dto.PropietarioDTO;
import com.example.taller.model.Propietario;

public interface PropietarioService {
    Propietario crearPropietario(PropietarioDTO propietario);

    Propietario obtenerPropietarioPorDni(String dni);

    void eliminarPropietario(String dni);
}

package com.example.taller.service;



import java.util.List;


import com.example.taller.dto.PropietarioDTO;
import com.example.taller.model.Propietario;

public interface PropietarioService {
    Propietario crearPropietario(PropietarioDTO propietario);

    Propietario obtenerPropietarioPorDni(String dni);

List<Propietario> listarPropietarios();

    void eliminarPropietario(String dni);
}

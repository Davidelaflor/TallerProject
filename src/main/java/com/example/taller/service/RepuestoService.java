package com.example.taller.service;


import java.util.List;


import com.example.taller.model.Propietario;
import com.example.taller.model.Repuesto;

public interface RepuestoService {
    Repuesto crearRepuesto(Repuesto repuesto);

    Repuesto obtenerRepuestoPorCodigo(Long codigo);

    List<Repuesto> listarRepuestos();


    void eliminarRepuesto(Long codigo);
}

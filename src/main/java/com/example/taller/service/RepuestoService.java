package com.example.taller.service;

import com.example.taller.model.Repuesto;

public interface RepuestoService {
    Repuesto crearRepuesto(Repuesto repuesto);

    Repuesto obtenerRepuestoPorCodigo(Long codigo);

    void eliminarRepuesto(Long codigo);
}

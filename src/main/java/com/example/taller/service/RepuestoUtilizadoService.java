package com.example.taller.service;

import com.example.taller.model.RepuestoUtilizado;

public interface RepuestoUtilizadoService {
    RepuestoUtilizado crearRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado);

    RepuestoUtilizado obtenerRepuestoUtilizadoPorId(String id);

    void eliminarRepuestoUtilizado(String id);
}

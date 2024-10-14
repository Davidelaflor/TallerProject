package com.example.taller.service;

import com.example.taller.model.RepuestoUtilizado;

public interface RepuestoUtilizadoService {
    RepuestoUtilizado crearRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado);

    RepuestoUtilizado obtenerRepuestoUtilizadoPorId(Long id);

    void eliminarRepuestoUtilizado(Long id);
}

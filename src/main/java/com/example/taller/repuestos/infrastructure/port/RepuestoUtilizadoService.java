package com.example.taller.repuestos.infrastructure.port;

import com.example.taller.repuestos.domain.model.RepuestoUtilizado;

public interface RepuestoUtilizadoService {
    RepuestoUtilizado crearRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado);

    RepuestoUtilizado obtenerRepuestoUtilizadoPorId(String id);

    void eliminarRepuestoUtilizado(String id);
}

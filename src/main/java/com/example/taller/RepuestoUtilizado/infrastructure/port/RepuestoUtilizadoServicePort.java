package com.example.taller.RepuestoUtilizado.infrastructure.port;

import com.example.taller.RepuestoUtilizado.infrastructure.adapter.RepuestoUtilizadoEntity;

public interface RepuestoUtilizadoServicePort {
    RepuestoUtilizadoEntity crearRepuestoUtilizado(RepuestoUtilizadoEntity repuestoUtilizado);

    RepuestoUtilizadoEntity obtenerRepuestoUtilizadoPorId(String id);

    void eliminarRepuestoUtilizado(String id);

    void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad);

}

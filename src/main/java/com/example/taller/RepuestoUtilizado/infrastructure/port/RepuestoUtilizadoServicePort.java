package com.example.taller.RepuestoUtilizado.infrastructure.port;

import java.util.Optional;

import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;

public interface RepuestoUtilizadoServicePort {
    void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad);

    Optional<RepuestoEntity> findById(String repuestoUtilizadoId);

}

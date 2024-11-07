package com.example.taller.RepuestoUtilizado.infrastructure.port;

import java.util.Optional;

import com.example.taller.RepuestoUtilizado.application.RepuestoUtilizadoRequestDTO;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;

public interface RepuestoUtilizadoServicePort {
    void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, RepuestoUtilizadoRequestDTO repuestoUtilizadoDTO); 

}

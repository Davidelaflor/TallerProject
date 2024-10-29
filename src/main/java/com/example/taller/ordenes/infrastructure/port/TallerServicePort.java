package com.example.taller.ordenes.infrastructure.port;

import java.util.List;

import com.example.taller.ordenes.application.dto.OrdenTrabajoDTO;
import com.example.taller.ordenes.domain.model.OrdenTrabajo;

public interface TallerServicePort {
    List<OrdenTrabajo> listarOrdenes();
    OrdenTrabajo crearOrdenTrabajo(OrdenTrabajo ordenTrabajo);
    OrdenTrabajo obtenerOrdenTrabajo(Long id);
    void eliminarOrdenTrabajo(Long id);
    double calcularCostoTotal(OrdenTrabajoDTO ordenTrabajoDTO);
}

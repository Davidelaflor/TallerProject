package com.example.taller.ordenes.infrastructure.port;

import java.util.List;

import com.example.taller.ordenes.application.OrdenTrabajoRequestDTO;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;

public interface OrdenTrabajoServicePort {
    List<OrdenTrabajoEntity> listarOrdenes();
    OrdenTrabajoEntity crearOrdenTrabajo(OrdenTrabajoRequestDTO dto);
    OrdenTrabajoEntity obtenerOrdenTrabajo(Long id);
    OrdenTrabajoEntity buscarOrdenTrabajoPorId(Long id);
    void eliminarOrdenTrabajo(Long id);
    void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad);
    double calcularCostoTotal(OrdenTrabajoRequestDTO ordenTrabajoDTO);
}

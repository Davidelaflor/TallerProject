package com.example.taller.ordenes.infrastructure.port;

import java.util.List;

import com.example.taller.ordenes.application.OrdenTrabajoRequestDTO;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

public interface OrdenTrabajoServicePort {
    List<OrdenTrabajoEntity> listarOrdenes();
    OrdenTrabajoDTO crearOrdenTrabajo(OrdenTrabajoRequestDTO ordenTrabajoRequestDTO);
    OrdenTrabajoEntity obtenerOrdenTrabajo(Long id);
    OrdenTrabajoDTO buscarOrdenTrabajoPorId(Long id);
    void eliminarOrdenTrabajo(Long id);
    void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad);
    double calcularCostoTotal(Long ordenTrabajoId);
    boolean existeOrdenTrabajoPorVehiculo(VehiculoEntity vehiculo);
    void agregarHorasAOrdenTrabajo(Long ordenTrabajoId, int horas);  // Método agregado

}

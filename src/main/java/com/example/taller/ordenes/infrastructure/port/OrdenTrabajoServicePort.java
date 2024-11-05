package com.example.taller.ordenes.infrastructure.port;

import java.util.List;
import java.util.Optional;

import com.example.taller.ordenes.application.OrdenTrabajoRequestDTO;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

public interface OrdenTrabajoServicePort {
    List<OrdenTrabajoDTO> listarOrdenes();
    OrdenTrabajoDTO crearOrdenTrabajo(OrdenTrabajoRequestDTO ordenTrabajoRequestDTO);
    OrdenTrabajoDTO obtenerOrdenTrabajo(Long id);
    void eliminarOrdenTrabajo(Long id);
    void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad);
    double calcularCostoTotal(Long ordenTrabajoId);
    boolean existeOrdenTrabajoPorVehiculo(String patente);
    void agregarHorasAOrdenTrabajo(Long ordenTrabajoId, int horas);  // Método agregado
    OrdenTrabajoEntity save(OrdenTrabajoEntity ordenTrabajo);
    Optional<OrdenTrabajoEntity> findById(Long id);
    boolean existeOrdenTrabajoPorId(Long id);

}

package com.example.taller.ordenes.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;

@Service
public class OrdenTrabajoApplicationService {
@Autowired
    private OrdenTrabajoServicePort ordenTrabajoServicePort;

    @Autowired
    private OrdenTrabajoValidationsService validationService;

    public OrdenTrabajoDTO crearOrdenTrabajo(OrdenTrabajoRequestDTO dto) {
        // Lógica de validación
        validationService.validarOrdenTrabajo(dto);
        
        // Delegación de la creación al adaptador de infraestructura
        return ordenTrabajoServicePort.crearOrdenTrabajo(dto);
    }

    public void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad) {
        validationService.validarAgregarRepuesto(ordenTrabajoId, repuestoUtilizadoId, cantidad);
        ordenTrabajoServicePort.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoId, cantidad);
    }

    public double calcularCostoTotal(Long ordenTrabajoId) {
        // Invoca el método del puerto para obtener el costo de la orden desde la capa de infraestructura
        return ordenTrabajoServicePort.calcularCostoTotal(ordenTrabajoId);
    }

    public void eliminarOrdenTrabajo(Long id) {
        ordenTrabajoServicePort.eliminarOrdenTrabajo(id);
    }

    public OrdenTrabajoDTO buscarOrdenTrabajoPorId(Long id) {
        return ordenTrabajoServicePort.buscarOrdenTrabajoPorId(id);
    }
}

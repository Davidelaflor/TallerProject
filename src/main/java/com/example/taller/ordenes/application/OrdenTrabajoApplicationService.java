package com.example.taller.ordenes.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrdenTrabajoApplicationService {

    @Autowired
    private OrdenTrabajoServicePort servicePort;

    @Autowired
    private OrdenTrabajoValidator validator;

    public OrdenTrabajoDTO crearOrdenTrabajo(OrdenTrabajoRequestDTO dto) {
        validator.validarOrdenTrabajo(dto);
        return servicePort.crearOrdenTrabajo(dto);
    }

      public OrdenTrabajoDTO finalizarOrdenTrabajo(Long id) {
        validator.validarOrdenTrabajoExistente(id);
        return servicePort.finalizarOrdenTrabajo(id);
    }

    public double calcularCostoTotal(Long ordenTrabajoId) {
        // Invoca el método del puerto para obtener el costo de la orden desde la capa
        // de infraestructura
        return servicePort.calcularCostoTotal(ordenTrabajoId);
    }

    public void eliminarOrdenTrabajo(Long id) {
        servicePort.eliminarOrdenTrabajo(id);
    }

    public OrdenTrabajoDTO obtenerOrdenTrabajo(Long id) {
        validator.validarOrdenTrabajoExistente(id);
        return servicePort.obtenerOrdenTrabajo(id);
    }

    public List<OrdenTrabajoDTO> listarOrdenes() {
        return servicePort.listarOrdenes();
    }

    public OrdenTrabajoDTO agregarHorasAOrdenTrabajo(Long ordenTrabajoId, int horas) {
        validator.validarOrdenTrabajoExistente(ordenTrabajoId); 
        return servicePort.agregarHorasAOrdenTrabajo(ordenTrabajoId, horas);
    }
}

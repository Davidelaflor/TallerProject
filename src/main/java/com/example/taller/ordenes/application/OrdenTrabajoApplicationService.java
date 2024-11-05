package com.example.taller.ordenes.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

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

    public void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad) {
        // Si tienes validaciones, asegúrate de que estén implementadas correctamente
        servicePort.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoId, cantidad);
    }

    public double calcularCostoTotal(Long ordenTrabajoId) {
        // Invoca el método del puerto para obtener el costo de la orden desde la capa
        // de infraestructura
        return servicePort.calcularCostoTotal(ordenTrabajoId);
    }

    public void eliminarOrdenTrabajo(Long id) {
        servicePort.eliminarOrdenTrabajo(id);
    }

    public OrdenTrabajoDTO buscarOrdenTrabajoPorId(Long id) {
        return servicePort.buscarOrdenTrabajoPorId(id);
    }

    public OrdenTrabajoDTO obtenerOrdenTrabajo(Long id) {
        return servicePort.obtenerOrdenTrabajo(id);
    }
}

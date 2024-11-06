package com.example.taller.RepuestoUtilizado.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.RepuestoUtilizado.infrastructure.port.RepuestoUtilizadoServicePort;

@Service
public class RepuestoUtilizadoApplicationService {

    @Autowired
    private RepuestoUtilizadoServicePort servicePort;

    public void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad) {
        // Si tienes validaciones, asegúrate de que estén implementadas correctamente
        servicePort.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoId, cantidad);
    }
}

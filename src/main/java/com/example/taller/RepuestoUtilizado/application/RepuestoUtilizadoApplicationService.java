package com.example.taller.RepuestoUtilizado.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.RepuestoUtilizado.infrastructure.port.RepuestoUtilizadoServicePort;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;
import com.example.taller.repuestos.infrastructure.port.RepuestoServicePort;

@Service
public class RepuestoUtilizadoApplicationService {

    @Autowired
    private RepuestoUtilizadoServicePort repuestoUtilizadoServicePort;
    
    public void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, RepuestoUtilizadoRequestDTO repuestoUtilizadoDTO) {
        repuestoUtilizadoServicePort.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoDTO);
    }
}

package com.example.taller.RepuestoUtilizado.infrastructure.adapter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.RepuestoUtilizado.infrastructure.port.RepuestoUtilizadoServicePort;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;
import com.example.taller.repuestos.infrastructure.port.RepuestoServicePort;

@Service
public class RepuestoUtilizadoService implements RepuestoUtilizadoServicePort {
    @Autowired
    private RepuestoUtilizadoRepository repuestoUtilizadoRepository;

    @Autowired
    private OrdenTrabajoServicePort ordenTrabajoServicePort;

    @Autowired
    private RepuestoServicePort repuestoServicePort;

    @Override
    public void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad) {
        OrdenTrabajoEntity ordenTrabajo = ordenTrabajoServicePort.findById(ordenTrabajoId)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

        RepuestoEntity repuesto = repuestoServicePort.findById(repuestoUtilizadoId)
                .orElseThrow(() -> new RuntimeException("Repuesto no encontrado"));

        RepuestoUtilizadoEntity repuestoUtilizado = new RepuestoUtilizadoEntity();
        repuestoUtilizado.setRepuesto(repuesto);
        repuestoUtilizado.setCantidad(cantidad);

        ordenTrabajo.getRepuestosUtilizados().add(repuestoUtilizado);

        ordenTrabajoServicePort.save(ordenTrabajo);
    }
    

    @Override
    public Optional<RepuestoEntity> findById(String repuestoUtilizadoId) {
        return repuestoServicePort.findById(repuestoUtilizadoId);
    }
}

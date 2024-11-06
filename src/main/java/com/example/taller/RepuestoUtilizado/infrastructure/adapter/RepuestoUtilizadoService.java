package com.example.taller.RepuestoUtilizado.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.taller.RepuestoUtilizado.infrastructure.port.RepuestoUtilizadoServicePort;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;

public class RepuestoUtilizadoService implements RepuestoUtilizadoServicePort {
     @Autowired
    private RepuestoUtilizadoRepository repuestoUtilizadoRepository;

    @Autowired
    private OrdenTrabajoServicePort ordenTrabajoServicePort;
    
        @Override
    public void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad) {
        // Obtener y actualizar la orden de trabajo para agregar repuesto
        OrdenTrabajoEntity ordenTrabajo = ordenTrabajoServicePort.findById(ordenTrabajoId)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

        // Lógica para agregar el repuesto a la orden de trabajo
        // (incluye manipulación de la entidad y persistencia)
        ordenTrabajoServicePort.save(ordenTrabajo);
    }
    @Override
    public RepuestoUtilizadoEntity crearRepuestoUtilizado(RepuestoUtilizadoEntity repuestoUtilizado) {
        // Se pueden incluir validaciones o lógica adicional aquí
        return repuestoUtilizadoRepository.save(repuestoUtilizado);
    }  

    @Override
    public RepuestoUtilizadoEntity obtenerRepuestoUtilizadoPorId(String id) {
        return repuestoUtilizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Repuesto Utilizado no encontrado"));
    }

    @Override
    public void eliminarRepuestoUtilizado(String id) {
        repuestoUtilizadoRepository.deleteById(id);
    }

}

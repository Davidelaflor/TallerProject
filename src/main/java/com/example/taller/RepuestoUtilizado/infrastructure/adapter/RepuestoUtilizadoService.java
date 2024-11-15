package com.example.taller.RepuestoUtilizado.infrastructure.adapter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.RepuestoUtilizado.application.RepuestoUtilizadoRequestDTO;
import com.example.taller.RepuestoUtilizado.infrastructure.port.RepuestoUtilizadoServicePort;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;
import com.example.taller.repuestos.infrastructure.port.RepuestoServicePort;

@Service
public class RepuestoUtilizadoService implements RepuestoUtilizadoServicePort {

   @Autowired
   private OrdenTrabajoServicePort ordenTrabajoServicePort;

   @Autowired
   private RepuestoServicePort repuestoServicePort;

   @Override
   public void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, RepuestoUtilizadoRequestDTO repuestoUtilizadoDTO) {
        // Obtener la orden de trabajo
        OrdenTrabajoEntity ordenTrabajo = ordenTrabajoServicePort.findById(ordenTrabajoId)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

        // Obtener el repuesto del inventario
        RepuestoEntity repuesto = repuestoServicePort.findByCodigoInventario(repuestoUtilizadoDTO.getCodigoInventario())
                .orElseThrow(() -> new RuntimeException("Repuesto no encontrado"));

        // Verificar si hay suficiente cantidad disponible en el inventario
        int cantidadUtilizada = repuestoUtilizadoDTO.getCantidadUtilizada();
        if (repuesto.getCantidad() < cantidadUtilizada) {
            throw new RuntimeException("Stock insuficiente para el repuesto: " + repuesto.getCodigoInventario());
        }

        // Crear la entidad RepuestoUtilizado
        RepuestoUtilizadoEntity repuestoUtilizado = new RepuestoUtilizadoEntity();
        repuestoUtilizado.setOrdenTrabajo(ordenTrabajo);
        repuestoUtilizado.setRepuesto(repuesto);
        repuestoUtilizado.setCantidad(cantidadUtilizada);

        // Restar la cantidad utilizada del inventario
        int nuevaCantidad = repuesto.getCantidad() - cantidadUtilizada;
        repuesto.setCantidad(nuevaCantidad);

        // Actualizar la entidad del repuesto en la base de datos
        repuestoServicePort.save(repuesto);

        // Añadir el repuesto utilizado a la orden de trabajo
        ordenTrabajo.getRepuestosUtilizados().add(repuestoUtilizado);

        // Guardar los cambios en la orden de trabajo
        ordenTrabajoServicePort.save(ordenTrabajo);
    }
}

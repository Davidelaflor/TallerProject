package com.example.taller.ordenes.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.taller.ordenes.application.OrdenTrabajoMapper; // Importa el mapper

import com.example.taller.ordenes.application.OrdenTrabajoRequestDTO;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

@Service
public class OrdenTrabajoService implements OrdenTrabajoServicePort {
@Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

      @Override
    public boolean existeOrdenTrabajoPorVehiculo(VehiculoEntity vehiculo) {
        // Implementa la lógica para verificar si hay una orden de trabajo para el vehículo
        return ordenTrabajoRepository.existsByVehiculo(vehiculo);
    }
    @Override
    public OrdenTrabajoDTO crearOrdenTrabajo(OrdenTrabajoRequestDTO dto) {
        OrdenTrabajoEntity ordenEntity = new OrdenTrabajoEntity();
        ordenEntity.setDetalleFalla(dto.getDetalleFalla());
        ordenEntity.setHorasTrabajadas(dto.getHorasTrabajadas());
        ordenEntity.setEstado(dto.getEstado().toString());

        // Guardar la orden en el repositorio
        OrdenTrabajoEntity savedEntity = ordenTrabajoRepository.save(ordenEntity);

        // Convertir la entidad guardada a DTO
        return new OrdenTrabajoDTO(savedEntity.getId(), savedEntity.getDetalleFalla(), 
                                   savedEntity.getHorasTrabajadas(), savedEntity.getEstado());
    }

    @Override
    public void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad) {
        // Obtener y actualizar la orden de trabajo para agregar repuesto
        OrdenTrabajoEntity ordenTrabajo = ordenTrabajoRepository.findById(ordenTrabajoId)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));
        
        // Lógica para agregar el repuesto a la orden de trabajo
        // (incluye manipulación de la entidad y persistencia)
        ordenTrabajoRepository.save(ordenTrabajo);
    }

    @Override
    public OrdenTrabajoDTO buscarOrdenTrabajoPorId(Long id) {
        return ordenTrabajoRepository.findById(id)
                .map(entity -> new OrdenTrabajoDTO(entity.getId(), entity.getDetalleFalla(),
                        entity.getHorasTrabajadas(), entity.getEstado()))
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));
    }

    @Override
    public void eliminarOrdenTrabajo(Long id) {
        ordenTrabajoRepository.deleteById(id);
    }
    @Override
    public double calcularCostoTotal(Long ordenTrabajoId) {
        // Calcular el costo de la mano de obra
        final double costoPorHora = 50.0;
        double costoManoObra = orden.getHorasTrabajadas() * costoPorHora;

        // Calcular el costo de los repuestos utilizados
        double costoRepuestos = orden.getRepuestosUtilizados().stream()
                .mapToDouble(r -> r.getRepuesto().getPrecio() * r.getCantidad())
                .sum();

        return costoManoObra + costoRepuestos;
    }
}

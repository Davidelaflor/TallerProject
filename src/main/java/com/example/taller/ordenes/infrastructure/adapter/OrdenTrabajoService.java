package com.example.taller.ordenes.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.taller.empleados.domain.EmpleadoDTO;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoRepository;
import com.example.taller.ordenes.application.OrdenTrabajoRequestDTO;
import com.example.taller.ordenes.domain.Estado;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoRepository;

@Service
public class OrdenTrabajoService implements OrdenTrabajoServicePort {
    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

     @Autowired
    private VehiculoRepository vehiculoRepository; 
    
     @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Override
    public boolean existeOrdenTrabajoPorVehiculo(VehiculoEntity vehiculo) {
        // Implementa la lógica para verificar si hay una orden de trabajo para el
        // vehículo
        return ordenTrabajoRepository.existsByVehiculo(vehiculo);
    }

    @Override
    public OrdenTrabajoDTO crearOrdenTrabajo(OrdenTrabajoRequestDTO dto) {
        VehiculoEntity vehiculo = vehiculoRepository.findByPatente(dto.getVehiculoPatente())
        .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
         EmpleadoEntity empleado = empleadoRepository.findById(dto.getEmpleadoId())
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        OrdenTrabajoEntity ordenEntity = new OrdenTrabajoEntity();
        ordenEntity.setDetalleFalla(dto.getDetalleFalla());
        ordenEntity.setHorasTrabajadas(dto.getHorasTrabajadas());
        ordenEntity.setFechaIngreso(dto.getFechaIngreso());
        ordenEntity.setEstado("ACTIVO");
        ordenEntity.setVehiculo(vehiculo);
        ordenEntity.setEmpleado(empleado);
        // Guardar la orden en el repositorio
        OrdenTrabajoEntity savedEntity = ordenTrabajoRepository.save(ordenEntity);

            EmpleadoDTO empleadoDTO = new EmpleadoDTO(empleado.getId(), empleado.getNombre(), empleado.getApellido(), empleado.getTelefono()); // Ejemplo de conversión

        // Convertir la entidad guardada a DTO y retornarla
        return new OrdenTrabajoDTO(
                savedEntity.getId(),
                savedEntity.getDetalleFalla(),
                savedEntity.getHorasTrabajadas(),
                savedEntity.getEstado(),
                savedEntity.getFechaIngreso(), // Suponiendo que este método existe
                empleadoDTO, // Reemplaza con la lógica para obtener empleado
                null, // Reemplaza con la lógica para obtener repuestos
                null // Reemplaza con la lógica para obtener propietario
        );

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
                .map(entity -> new OrdenTrabajoDTO(
                        entity.getId(),
                        entity.getDetalleFalla(),
                        entity.getHorasTrabajadas(),
                        entity.getEstado(),
                        entity.getFechaIngreso(), // Agregar esto
                        null, // Asignar null o un valor adecuado para empleado
                        null, // Asignar null o un valor adecuado para repuestos
                        null // Asignar null o un valor adecuado para propietario
                ))
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));
    }

    @Override
    public void eliminarOrdenTrabajo(Long id) {
        ordenTrabajoRepository.deleteById(id);
    }

    @Override
    public double calcularCostoTotal(Long ordenTrabajoId) {
        OrdenTrabajoEntity orden = ordenTrabajoRepository.findById(ordenTrabajoId)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));
        // Calcular el costo de la mano de obra
        final double costoPorHora = 50.0;
        double costoManoObra = orden.getHorasTrabajadas() * costoPorHora;

        // Calcular el costo de los repuestos utilizados
        double costoRepuestos = orden.getRepuestosUtilizados().stream()
                .mapToDouble(r -> r.getRepuesto().getPrecio() * r.getCantidad())
                .sum();

        return costoManoObra + costoRepuestos;
    }

    @Override
    public OrdenTrabajoEntity obtenerOrdenTrabajo(Long id) {
        // Busca la orden por ID
        return ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));
    }

    @Override
    public List<OrdenTrabajoEntity> listarOrdenes() {
        // Retorna todas las ordenes de trabajo desde el repositorio
        return ordenTrabajoRepository.findAll();
    }

    @Override
    public void agregarHorasAOrdenTrabajo(Long ordenTrabajoId, int horas) {
        OrdenTrabajoEntity ordenTrabajo = ordenTrabajoRepository.findById(ordenTrabajoId)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

        // Asumiendo que tienes un método setHorasTrabajadas en la entidad
        // OrdenTrabajoEntity
        ordenTrabajo.setHorasTrabajadas(ordenTrabajo.getHorasTrabajadas() + horas);
        ordenTrabajoRepository.save(ordenTrabajo);
    }
}

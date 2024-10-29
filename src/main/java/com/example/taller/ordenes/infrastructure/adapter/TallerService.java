package com.example.taller.ordenes.infrastructure.adapter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.example.taller.empleados.infrastructure.repository.EmpleadoRepository;
import com.example.taller.empleados.domain.model.Empleado;
import com.example.taller.model.Estado;
import com.example.taller.ordenes.domain.model.OrdenTrabajo;
import com.example.taller.ordenes.infrastructure.repository.OrdenTrabajoRepository;
import com.example.taller.propietarios.domain.model.Propietario;
import com.example.taller.propietarios.infrastructure.repository.PropietarioRepository;
import com.example.taller.repuestos.application.dto.RepuestoUtilizadoDTO;
import com.example.taller.repuestos.domain.dto.RepuestoUtilizadoResponseDTO;
import com.example.taller.repuestos.domain.model.Repuesto;
import com.example.taller.repuestos.domain.model.RepuestoUtilizado;
import com.example.taller.repuestos.infrastructure.repository.RepuestoRepository;
import com.example.taller.repuestos.infrastructure.repository.RepuestoUtilizadoRepository;
import com.example.taller.vehiculos.domain.model.Vehiculo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TallerService implements TallerServicePort {
    @Autowired
    private RepuestoRepository repuestoRepository;

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private RepuestoUtilizadoRepository repuestoUtilizadoRepository;


    @Override
    public List<OrdenTrabajo> listarOrdenes() {
        return ordenTrabajoRepository.findAll();
    }

    @Override
    public OrdenTrabajo crearOrdenTrabajo(OrdenTrabajoDTO dto) {
        // Buscar empleado por ID
        Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        
        // Buscar propietario por DNI
        Propietario propietario = propietarioRepository.findById(dto.getPropietarioDni())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));
    
        // Buscar el vehículo por patente dentro de los vehículos del propietario
        Vehiculo vehiculoSeleccionado = propietario.getVehiculos().stream()
                .filter(vehiculo -> vehiculo.getPatente().equals(dto.getVehiculoPatente()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado para el propietario"));
       // Verificar si el vehículo ya está en alguna orden de trabajo existente
       boolean vehiculoRegistrado = ordenTrabajoRepository.existsByVehiculo(vehiculoSeleccionado);
       if (vehiculoRegistrado) {
           throw new RuntimeException("El vehículo ya tiene una orden de trabajo registrada.");
       }
        // Crear la entidad OrdenTrabajo directamente
        OrdenTrabajo nuevaOrden = OrdenTrabajo.builder()
                .detalleFalla(dto.getDetalleFalla())
                .horasTrabajadas(dto.getHorasTrabajadas())  // Tomar el valor desde el DTO
                .estado(Estado.ACTIVO.toString())  // Puedes ajustar si el estado es dinámico
                .empleado(empleado)
                .propietario(propietario)
                .vehiculo(vehiculoSeleccionado)
                .fechaIngreso(dto.getFechaIngreso() != null ? dto.getFechaIngreso() : LocalDate.now())  // Fecha por defecto
                .build();
    
        // Guardar y devolver la nueva orden
        return ordenTrabajoRepository.save(nuevaOrden);
    }
    
    

   
    @Override
    public void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad) {
        // Obtener la orden de trabajo
        OrdenTrabajo ordenTrabajo = ordenTrabajoRepository.findById(ordenTrabajoId)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

        // Obtener el repuesto (suponiendo que ya tienes la entidad Repuesto)
        Repuesto repuesto = repuestoRepository.findById(repuestoUtilizadoId)
                .orElseThrow(() -> new RuntimeException("Repuesto no encontrado"));

        // Crear un nuevo RepuestoUtilizado
        RepuestoUtilizado repuestoUtilizado = new RepuestoUtilizado();
        repuestoUtilizado.setRepuesto(repuesto);
        // Si hay un atributo de cantidad en RepuestoUtilizado, puedes establecerlo aquí
        repuestoUtilizado.setCantidad(cantidad); // o la cantidad que necesites

        // Agregar el repuesto a la orden de trabajo
        ordenTrabajo.addRepuestoUtilizado(repuestoUtilizado);
        ordenTrabajoRepository.save(ordenTrabajo); // Guardar la orden con los nuevos repuestos
    }

@Override
public void agregarHorasAOrdenTrabajo(Long ordenTrabajoId, int horas) {
    // Obtener la orden de trabajo
    OrdenTrabajo ordenTrabajo = ordenTrabajoRepository.findById(ordenTrabajoId)
            .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

    // Sumar horas al campo horasTrabajadas
    ordenTrabajo.setHorasTrabajadas(ordenTrabajo.getHorasTrabajadas() + horas);

    // Guardar la orden con el nuevo total de horas
    ordenTrabajoRepository.save(ordenTrabajo);
}

    @Override
    public OrdenTrabajo buscarOrdenTrabajoPorId(Long id) {
        return ordenTrabajoRepository.findById(id).orElse(null);
    }

    @Override
    public OrdenTrabajo obtenerOrdenTrabajo(Long id) {
        return ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden de trabajo no encontrada con id: " + id));

    }

    @Override
    public void eliminarOrdenTrabajo(Long id) {
        ordenTrabajoRepository.deleteById(id);
    }

    @Override
    public RepuestoUtilizado guardarRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado) {
        return repuestoUtilizadoRepository.save(repuestoUtilizado);
    }

    public double calcularCostoTotal(OrdenTrabajoDTO ordenTrabajoDTO) {
        // Costo por hora fijo
        final double costoPorHora = 50.0;

        // Calcular costo de la mano de obra
        double costoManoObra = ordenTrabajoDTO.getHorasTrabajadas() * costoPorHora;

        // Calcular costo total de los repuestos utilizados
        double costoRepuestos = calcularCostoRepuestos(ordenTrabajoDTO.getRepuestosUtilizados());

        // Calcular costo total
        return costoManoObra + costoRepuestos;
    }

    private double calcularCostoRepuestos(List<RepuestoUtilizadoDTO> repuestosUtilizados) {
        double total = 0;
        for (RepuestoUtilizadoDTO repuestoUtilizado : repuestosUtilizados) {
            // Suponiendo que el precio se multiplica por la cantidad utilizada
            total += repuestoUtilizado.getRepuesto().getPrecio() * repuestoUtilizado.getCantidadUtilizada();
        }
        return total;
    }
}

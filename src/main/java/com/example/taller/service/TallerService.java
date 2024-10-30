package com.example.taller.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.example.taller.RepuestoUtilizado.application.RepuestoUtilizadoRequestDTO;
import com.example.taller.RepuestoUtilizado.domain.RepuestoUtilizadoDTO;
import com.example.taller.RepuestoUtilizado.infrastructure.adapter.RepuestoUtilizadoEntity;
import com.example.taller.RepuestoUtilizado.infrastructure.adapter.RepuestoUtilizadoRepository;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoRepository;
import com.example.taller.ordenes.application.OrdenTrabajoRequestDTO;
import com.example.taller.ordenes.domain.Estado;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoRepository;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioRepository;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoRepository;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TallerService implements OrdenTrabajoServicePort {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private RepuestoUtilizadoRepository repuestoUtilizadoRepository;

  

   

   

   


   


    @Override
    public List<OrdenTrabajoEntity> listarOrdenes() {
        return ordenTrabajoRepository.findAll();
    }

    @Override
    public OrdenTrabajoEntity crearOrdenTrabajo(OrdenTrabajoRequestDTO dto) {
        // Buscar empleado por ID
        EmpleadoEntity empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        
        // Buscar propietario por DNI
        PropietarioEntity propietario = propietarioRepository.findById(dto.getPropietarioDni())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));
    
        // Buscar el vehículo por patente dentro de los vehículos del propietario
        VehiculoEntity vehiculoSeleccionado = propietario.getVehiculos().stream()
                .filter(vehiculo -> vehiculo.getPatente().equals(dto.getVehiculoPatente()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado para el propietario"));
       // Verificar si el vehículo ya está en alguna orden de trabajo existente
       boolean vehiculoRegistrado = ordenTrabajoRepository.existsByVehiculo(vehiculoSeleccionado);
       if (vehiculoRegistrado) {
           throw new RuntimeException("El vehículo ya tiene una orden de trabajo registrada.");
       }
        // Crear la entidad OrdenTrabajo directamente
        OrdenTrabajoEntity nuevaOrden = OrdenTrabajoEntity.builder()
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
        OrdenTrabajoEntity ordenTrabajo = ordenTrabajoRepository.findById(ordenTrabajoId)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

        // Obtener el repuesto (suponiendo que ya tienes la entidad Repuesto)
        RepuestoEntity repuesto = repuestoRepository.findById(repuestoUtilizadoId)
                .orElseThrow(() -> new RuntimeException("Repuesto no encontrado"));

        // Crear un nuevo RepuestoUtilizado
        RepuestoUtilizadoEntity repuestoUtilizado = new RepuestoUtilizadoEntity();
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
    OrdenTrabajoEntity ordenTrabajo = ordenTrabajoRepository.findById(ordenTrabajoId)
            .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

    // Sumar horas al campo horasTrabajadas
    ordenTrabajo.setHorasTrabajadas(ordenTrabajo.getHorasTrabajadas() + horas);

    // Guardar la orden con el nuevo total de horas
    ordenTrabajoRepository.save(ordenTrabajo);
}

    @Override
    public OrdenTrabajoEntity buscarOrdenTrabajoPorId(Long id) {
        return ordenTrabajoRepository.findById(id).orElse(null);
    }

    @Override
    public OrdenTrabajoEntity obtenerOrdenTrabajo(Long id) {
        return ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden de trabajo no encontrada con id: " + id));

    }

    @Override
    public void eliminarOrdenTrabajo(Long id) {
        ordenTrabajoRepository.deleteById(id);
    }

    @Override
    public RepuestoUtilizadoEntity guardarRepuestoUtilizado(RepuestoUtilizadoEntity repuestoUtilizado) {
        return repuestoUtilizadoRepository.save(repuestoUtilizado);
    }

    public double calcularCostoTotal(OrdenTrabajoRequestDTO ordenTrabajoDTO) {
        // Costo por hora fijo
        final double costoPorHora = 50.0;

        // Calcular costo de la mano de obra
        double costoManoObra = ordenTrabajoDTO.getHorasTrabajadas() * costoPorHora;

        // Calcular costo total de los repuestos utilizados
        double costoRepuestos = calcularCostoRepuestos(ordenTrabajoDTO.getRepuestosUtilizados());

        // Calcular costo total
        return costoManoObra + costoRepuestos;
    }

    private double calcularCostoRepuestos(List<RepuestoUtilizadoRequestDTO> repuestosUtilizados) {
        double total = 0;
        for (RepuestoUtilizadoRequestDTO repuestoUtilizado : repuestosUtilizados) {
            // Suponiendo que el precio se multiplica por la cantidad utilizada
            total += repuestoUtilizado.getRepuesto().getPrecio() * repuestoUtilizado.getCantidadUtilizada();
        }
        return total;
    }
}

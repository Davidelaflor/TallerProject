package com.example.taller.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import com.example.taller.dto.OrdenTrabajoDTO;
import com.example.taller.dto.OrdenTrabajoResponseDTO;
import com.example.taller.dto.RepuestoUtilizadoDTO;
import com.example.taller.dto.RepuestoUtilizadoResponseDTO;
import com.example.taller.model.Empleado;
import com.example.taller.model.Estado;
import com.example.taller.model.OrdenTrabajo;
import com.example.taller.model.Propietario;
import com.example.taller.model.Repuesto;
import com.example.taller.model.RepuestoUtilizado;
import com.example.taller.model.Vehiculo;
import com.example.taller.repository.EmpleadoRepository;
import com.example.taller.repository.OrdenTrabajoRepository;
import com.example.taller.repository.PropietarioRepository;
import com.example.taller.repository.RepuestoRepository;
import com.example.taller.repository.RepuestoUtilizadoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TallerService implements TallerServiceInterface {
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

    // Métodos para manejar Empleados
    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado buscarEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    // Métodos para manejar Propietarios
    @Override
    public List<Propietario> listarPropietarios() {
        return propietarioRepository.findAll();
    }

    @Override
    public Propietario guardarPropietario(Propietario propietario) {
        return propietarioRepository.save(propietario);
    }

    @Override
    public Propietario buscarPropietarioPorDni(String dni) {
        return propietarioRepository.findById(dni).orElse(null);
    }

    @Override
    public void eliminarPropietario(String dni) {
        propietarioRepository.deleteById(dni);
    }

    @Override
    public List<Repuesto> listarRepuestos() {
        return repuestoRepository.findAll();
    }

    @Override
    public Repuesto guardarRepuesto(Repuesto repuesto) {
        return repuestoRepository.save(repuesto);
    }

    @Override
    public Repuesto buscarRepuestoPorCodigo(String codigo) {
        return repuestoRepository.findById(codigo).orElse(null);
    }

    @Override
    public void eliminarRepuesto(String codigo) {
        repuestoRepository.deleteById(codigo);
    }

    @Override
    public void actualizarCantidad(String codigo, int cantidad) {
        Repuesto repuesto = buscarRepuestoPorCodigo(codigo);
        if (repuesto != null) {
            repuesto.setCantidad(repuesto.getCantidad() - cantidad);
            repuestoRepository.save(repuesto);
        }
    }

    @Override
    public List<OrdenTrabajo> listarOrdenes() {
        return ordenTrabajoRepository.findAll();
    }

    @Override
    public OrdenTrabajo crearOrdenTrabajo(OrdenTrabajoDTO dto) {
        Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Propietario propietario = propietarioRepository.findById(dto.getPropietarioDni())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

        // Suponiendo que se ha seleccionado un vehículo de los que tiene el propietario
        List<Vehiculo> vehiculos = propietario.getVehiculos();
        if (vehiculos.isEmpty()) {
            throw new RuntimeException("El propietario no tiene vehículos registrados.");
        }

        // Aquí seleccionas el vehículo de alguna forma, por ejemplo, el primero:
        Vehiculo vehiculoSeleccionado = vehiculos.get(0); // Selecciona el vehículo correspondiente

        OrdenTrabajo entity = generateOrdenTrabajoEntity(dto, empleado, propietario, vehiculoSeleccionado);

        return ordenTrabajoRepository.save(entity);

    }

    private OrdenTrabajo generateOrdenTrabajoEntity(OrdenTrabajoDTO dto, Empleado empleado, Propietario propietario,
            Vehiculo vehiculo) {
        return OrdenTrabajo.builder()
                .detalleFalla(dto.getDetalleFalla())
                .horasTrabajadas(dto.getHorasTrabajadas())  // Tomar el valor desde el DTO
                .estado(Estado.ACTIVO.toString())
                .empleado(empleado)
                .propietario(propietario)
                .vehiculo(vehiculo)
                .fechaIngreso(dto.getFechaIngreso() != null ? dto.getFechaIngreso() : LocalDate.now())
                .build();
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

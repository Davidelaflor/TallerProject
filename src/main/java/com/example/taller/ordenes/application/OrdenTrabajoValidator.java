package com.example.taller.ordenes.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.taller.empleados.infrastructure.port.EmpleadoServicePort;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.vehiculos.infrastructure.port.VehiculoServicePort;

@Component
public class OrdenTrabajoValidator {
    @Autowired
    private EmpleadoServicePort empleadoServicePort;

    @Autowired
    private PropietarioServicePort propietarioServicePort;


    @Autowired
    private OrdenTrabajoServicePort ordenTrabajoServicePort;

    @Autowired
    private VehiculoServicePort vehiculoServicePort;

    public void validarEmpleadoExistente(Long empleadoId) {
        if (empleadoId == null) {
            throw new IllegalArgumentException("El ID del empleado es requerido.");
        }
        if (!empleadoServicePort.existsById(empleadoId)) {
            throw new RuntimeException("Empleado no encontrado.");
        }
    }

    public void validarPropietarioExistente(String propietarioDni) {
        if (propietarioDni == null) {
            throw new IllegalArgumentException("El DNI del propietario es requerido.");
        }
        if (!propietarioServicePort.existsByDni(propietarioDni)) {
            throw new RuntimeException("Propietario no encontrado.");
        }
    }

    public void validarVehiculoExistentePropietario(String patente, String dni) {
        if (patente == null) {
            throw new IllegalArgumentException("La patente del vehículo es requerida.");
        }
        if (dni == null) {
            throw new IllegalArgumentException("El propietario es requerido.");
        }
    
       // Verifica si el vehículo con la patente pertenece al propietario con el DNI dado
    if (!vehiculoServicePort.existsByPatenteAndPropietarioDni(patente, dni)) {
        throw new RuntimeException("Vehículo no encontrado para el propietario con DNI: " + dni);
    }
    }

   public void validarVehiculoNoRegistrado(String patente) {
        // Buscar si ya existe una orden de trabajo para el vehículo
        Optional<OrdenTrabajoEntity> ordenExistente = ordenTrabajoServicePort.findByVehiculoPatente(patente);

        if (ordenExistente.isPresent()) {
            // Si ya existe una orden de trabajo
            OrdenTrabajoEntity orden = ordenExistente.get();

            // Si la orden está activa, no permitir la creación de una nueva
            if ("ACTIVO".equals(orden.getEstado())) {
                throw new RuntimeException("El vehículo ya tiene una orden de trabajo activa.");
            }
            // Si la orden está finalizada, permitir la creación de una nueva
            if ("FINALIZADO".equals(orden.getEstado())) {
                // No se lanza excepción, permitiendo la creación de la nueva orden
            }
        }
    }
    public void validarOrdenTrabajoExistente(Long id) {
        if (!ordenTrabajoServicePort.existeOrdenTrabajoPorId(id)) {
            throw new RuntimeException("Orden de trabajo no encontrada");
        }
    }
    public void validarOrdenTrabajo(OrdenTrabajoRequestDTO dto) {
        validarEmpleadoExistente(dto.getEmpleadoId());
        validarPropietarioExistente(dto.getPropietarioDni());
        validarVehiculoExistentePropietario(dto.getVehiculoPatente(), dto.getPropietarioDni());
        validarVehiculoNoRegistrado(dto.getVehiculoPatente());
    }
}

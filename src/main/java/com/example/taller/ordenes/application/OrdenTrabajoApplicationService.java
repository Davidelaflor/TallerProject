package com.example.taller.ordenes.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoRepository;  // Asegúrate de importar el repositorio
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioRepository;  // Asegúrate de importar el repositorio
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

@Service
public class OrdenTrabajoApplicationService {
    
    @Autowired
    private OrdenTrabajoServicePort ordenTrabajoServicePort;

    @Autowired
    private EmpleadoRepository empleadoRepository;  // Inyección del repositorio de empleados

    @Autowired
    private PropietarioRepository propietarioRepository;  // Inyección del repositorio de propietarios

    public OrdenTrabajoDTO crearOrdenTrabajo(OrdenTrabajoRequestDTO dto) {
        // Validaciones
        if (dto.getEmpleadoId() == null) {
            throw new IllegalArgumentException("El ID del empleado es requerido.");
        }

        EmpleadoEntity empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado."));

        if (dto.getPropietarioDni() == null) {
            throw new IllegalArgumentException("El DNI del propietario es requerido.");
        }

        PropietarioEntity propietario = propietarioRepository.findById(dto.getPropietarioDni())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado."));

        if (dto.getVehiculoPatente() == null) {
            throw new IllegalArgumentException("La patente del vehículo es requerida.");
        }

        // Buscar el vehículo por patente dentro de los vehículos del propietario
        VehiculoEntity vehiculoSeleccionado = propietario.getVehiculos().stream()
                .filter(vehiculo -> vehiculo.getPatente().equals(dto.getVehiculoPatente()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado para el propietario."));

        // Verificar si el vehículo ya está en alguna orden de trabajo existente
        boolean vehiculoRegistrado = ordenTrabajoServicePort.existeOrdenTrabajoPorVehiculo(vehiculoSeleccionado);
        if (vehiculoRegistrado) {
            throw new RuntimeException("El vehículo ya tiene una orden de trabajo registrada.");
        }

        // Crear la nueva orden de trabajo
        return ordenTrabajoServicePort.crearOrdenTrabajo(dto);
    }

    public void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad) {
        // Si tienes validaciones, asegúrate de que estén implementadas correctamente
        ordenTrabajoServicePort.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoId, cantidad);
    }

    public double calcularCostoTotal(Long ordenTrabajoId) {
        // Invoca el método del puerto para obtener el costo de la orden desde la capa de infraestructura
        return ordenTrabajoServicePort.calcularCostoTotal(ordenTrabajoId);
    }

    public void eliminarOrdenTrabajo(Long id) {
        ordenTrabajoServicePort.eliminarOrdenTrabajo(id);
    }

    public OrdenTrabajoDTO buscarOrdenTrabajoPorId(Long id) {
        return ordenTrabajoServicePort.buscarOrdenTrabajoPorId(id);
    }
    public OrdenTrabajoDTO obtenerOrdenTrabajo(Long id) {
        return ordenTrabajoServicePort.obtenerOrdenTrabajo(id);
    }
}

package com.example.taller.ordenes.application;

import org.springframework.stereotype.Component;
import java.util.List;
import com.example.taller.RepuestoUtilizado.domain.RepuestoUtilizadoDTO;
import com.example.taller.empleados.domain.EmpleadoDTO;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;
import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.vehiculos.domain.VehiculoDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

@Component
public class OrdenTrabajoMapper {
    public OrdenTrabajoDTO toDTO(OrdenTrabajoEntity ordenTrabajo) {
        // Mapeo de repuestos utilizados a DTO
        List<RepuestoUtilizadoDTO> repuestosUtilizados = ordenTrabajo
                .getRepuestosUtilizados()
                .stream()
                .map(repuestoUtilizado -> new RepuestoUtilizadoDTO(
                        repuestoUtilizado.getRepuesto()
                                .getCodigoInventario(),
                        repuestoUtilizado.getRepuesto().getNombre(),
                        repuestoUtilizado.getRepuesto().getPrecio(),
                        repuestoUtilizado.getCantidad()))
                .toList();

        // Mapeo del vehículo **asociado** a la orden de trabajo
        VehiculoEntity vehiculo = ordenTrabajo.getVehiculo();
        VehiculoDTO vehiculoDTO = null;
        if (vehiculo != null) {
            vehiculoDTO = new VehiculoDTO(
                    vehiculo.getPatente(),
                    vehiculo.getMarca(),
                    vehiculo.getModelo());
        }

        // Mapeo del propietario a DTO (incluyendo solo el vehículo específico)
        PropietarioEntity propietario = ordenTrabajo.getPropietario();
        List<VehiculoDTO> vehiculosDTO = vehiculo != null ? List.of(vehiculoDTO)
                : List.of(); // Solo el vehículo de la orden
        PropietarioDTO propietarioDTO = new PropietarioDTO(
                propietario.getDni(),
                propietario.getNombre(),
                propietario.getTelefono(),
                vehiculosDTO); // Incluye solo el vehículo asociado a la orden

        // Mapeo del empleado a DTO (con apellido y teléfono)
        EmpleadoEntity empleado = ordenTrabajo.getEmpleado();
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getTelefono());
        // Crear DTO de respuesta de la orden de trabajo
        return new OrdenTrabajoDTO(
                ordenTrabajo.getId(),
                ordenTrabajo.getDetalleFalla(),
                ordenTrabajo.getHorasTrabajadas(),
                ordenTrabajo.getEstado(),
                ordenTrabajo.getFechaIngreso(), // Mapeo de la fecha de ingreso
                empleadoDTO, // Mapeo del empleado
                repuestosUtilizados,
                propietarioDTO);
    }
    public OrdenTrabajoEntity toEntity(OrdenTrabajoRequestDTO requestDTO) {
        OrdenTrabajoEntity ordenTrabajo = new OrdenTrabajoEntity();
        ordenTrabajo.setDetalleFalla(requestDTO.getDetalleFalla());
        ordenTrabajo.setHorasTrabajadas(requestDTO.getHorasTrabajadas());
        // Completa aquí con el resto de las propiedades que necesites
        return ordenTrabajo;
    }
}

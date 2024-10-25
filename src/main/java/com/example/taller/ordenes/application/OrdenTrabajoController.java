package com.example.taller.ordenes.application;

import java.util.List;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.dto.HorasTrabajadasDTO;
import com.example.taller.dto.PropietarioResponseDTO;
import com.example.taller.dto.RepuestoUtilizadoDTO;
import com.example.taller.dto.RepuestoUtilizadoResponseDTO;
import com.example.taller.dto.VehiculoResponseDTO;
import com.example.taller.empleados.domain.EmpleadoResponseDTO;
import com.example.taller.empleados.infrastructure.adapter.Empleado;
import com.example.taller.model.OrdenTrabajo;
import com.example.taller.model.Propietario;
import com.example.taller.model.RepuestoUtilizado;
import com.example.taller.model.Vehiculo;
import com.example.taller.ordenes.domain.OrdenTrabajoResponseDTO;
import com.example.taller.ordenes.infrastructure.adapter.TallerServiceInterface;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenTrabajoController {
        @Autowired
        private TallerServiceInterface tallerService;

        @GetMapping
        public ResponseEntity<List<OrdenTrabajoResponseDTO>> listarOrdenes() {
                List<OrdenTrabajo> ordenesTrabajo = tallerService.listarOrdenes();

                // Mapeo de la lista de órdenes de trabajo a una lista de
                // OrdenTrabajoResponseDTO
                List<OrdenTrabajoResponseDTO> ordenesTrabajoDTO = ordenesTrabajo.stream()
                                .map(ordenTrabajo -> {

                                        // Mapeo de repuestos utilizados a DTO
                                        List<RepuestoUtilizadoResponseDTO> repuestosUtilizados = ordenTrabajo
                                                        .getRepuestoUtilizado()
                                                        .stream()
                                                        .map(repuestoUtilizado -> new RepuestoUtilizadoResponseDTO(
                                                                        repuestoUtilizado.getRepuesto()
                                                                                        .getCodigoInventario(),
                                                                        repuestoUtilizado.getRepuesto().getNombre(),
                                                                        repuestoUtilizado.getRepuesto().getPrecio(),
                                                                        repuestoUtilizado.getCantidad()))
                                                        .toList();

                                        // Mapeo del vehículo **asociado** a la orden de trabajo
                                        Vehiculo vehiculo = ordenTrabajo.getVehiculo();
                                        VehiculoResponseDTO vehiculoDTO = null;
                                        if (vehiculo != null) {
                                                vehiculoDTO = new VehiculoResponseDTO(
                                                                vehiculo.getPatente(),
                                                                vehiculo.getMarca(),
                                                                vehiculo.getModelo());
                                        }

                                        // Mapeo del propietario a DTO (incluyendo solo el vehículo específico)
                                        Propietario propietario = ordenTrabajo.getPropietario();
                                        List<VehiculoResponseDTO> vehiculosDTO = vehiculo != null ? List.of(vehiculoDTO)
                                                        : List.of(); // Solo el vehículo de la orden
                                        PropietarioResponseDTO propietarioDTO = new PropietarioResponseDTO(
                                                        propietario.getDni(),
                                                        propietario.getNombre(),
                                                        propietario.getTelefono(),
                                                        vehiculosDTO); // Incluye solo el vehículo asociado a la orden

                                        // Mapeo del empleado a DTO (con apellido y teléfono)
                                        Empleado empleado = ordenTrabajo.getEmpleado();
                                        EmpleadoResponseDTO empleadoDTO = new EmpleadoResponseDTO(
                                                        empleado.getId(),
                                                        empleado.getNombre(),
                                                        empleado.getApellido(),
                                                        empleado.getTelefono());
                                        // Crear DTO de respuesta de la orden de trabajo
                                        return new OrdenTrabajoResponseDTO(
                                                        ordenTrabajo.getId(),
                                                        ordenTrabajo.getDetalleFalla(),
                                                        ordenTrabajo.getHorasTrabajadas(),
                                                        ordenTrabajo.getEstado(),
                                                        ordenTrabajo.getFechaIngreso(), // Mapeo de la fecha de ingreso
                                                        empleadoDTO, // Mapeo del empleado
                                                        repuestosUtilizados,
                                                        propietarioDTO);

                                }).toList();

                return ResponseEntity.ok(ordenesTrabajoDTO);
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrdenTrabajoResponseDTO> obtenerOrden(@PathVariable Long id) {
                OrdenTrabajo ordenTrabajo = tallerService.obtenerOrdenTrabajo(id);
                if (ordenTrabajo == null) {
                        return ResponseEntity.notFound().build();
                }

                // Mapeo de repuestos utilizados a DTO
                List<RepuestoUtilizadoResponseDTO> repuestosUtilizados = ordenTrabajo.getRepuestoUtilizado().stream()
                                .map(repuestoUtilizado -> new RepuestoUtilizadoResponseDTO(
                                                repuestoUtilizado.getRepuesto().getCodigoInventario(),
                                                repuestoUtilizado.getRepuesto().getNombre(),
                                                repuestoUtilizado.getRepuesto().getPrecio(),
                                                repuestoUtilizado.getCantidad()))
                                .toList();

                // Mapeo de los vehículos del propietario a DTO
                List<VehiculoResponseDTO> vehiculos = ordenTrabajo.getPropietario().getVehiculos().stream()
                                .map(vehiculo -> new VehiculoResponseDTO(
                                                vehiculo.getPatente(),
                                                vehiculo.getMarca(),
                                                vehiculo.getModelo()))
                                .toList();

                // Mapeo del propietario a DTO
                PropietarioResponseDTO propietarioDTO = new PropietarioResponseDTO(
                                ordenTrabajo.getPropietario().getDni(),
                                ordenTrabajo.getPropietario().getNombre(),
                                ordenTrabajo.getPropietario().getTelefono(),
                                vehiculos);

                EmpleadoResponseDTO empleadoDTO = new EmpleadoResponseDTO(
                                ordenTrabajo.getEmpleado().getId(),
                                ordenTrabajo.getEmpleado().getNombre(),
                                ordenTrabajo.getEmpleado().getApellido(), // Añadir apellido
                                ordenTrabajo.getEmpleado().getTelefono());

                // Crear DTO de respuesta de la orden de trabajo
                OrdenTrabajoResponseDTO responseDTO = new OrdenTrabajoResponseDTO(
                                ordenTrabajo.getId(),
                                ordenTrabajo.getDetalleFalla(),
                                ordenTrabajo.getHorasTrabajadas(),
                                ordenTrabajo.getEstado(),
                                ordenTrabajo.getFechaIngreso(), // Mapeo de la fecha de ingreso
                                empleadoDTO, // Mapeo del empleado
                                repuestosUtilizados,
                                propietarioDTO);
                return ResponseEntity.ok(responseDTO);
                // return ordenTrabajo != null ? ResponseEntity.ok(ordenTrabajo) :
                // ResponseEntity.notFound().build();
        }

        @PostMapping
        public ResponseEntity<OrdenTrabajo> crearOrdenTrabajo(@RequestBody OrdenTrabajoDTO ordenTrabajo) {
                OrdenTrabajo nuevaOrden = tallerService.crearOrdenTrabajo(ordenTrabajo);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarOrdenTrabajo(@PathVariable Long id) {
                tallerService.eliminarOrdenTrabajo(id);
                return ResponseEntity.noContent().build();
        }

        @PostMapping("/{ordenTrabajoId}/repuestos/{repuestoUtilizadoId}")
        public ResponseEntity<Void> agregarRepuestoAOrden(@PathVariable Long ordenTrabajoId,
                        @PathVariable String repuestoUtilizadoId,
                        @RequestBody RepuestoUtilizadoDTO repuestoUtilizadoDTO) {
                int cantidad = repuestoUtilizadoDTO.getCantidadUtilizada();
                tallerService.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoId, cantidad);
                return ResponseEntity.ok().build();
        }

        @PostMapping("/{ordenTrabajoId}/horas")
        public ResponseEntity<Void> agregarHorasAOrden(@PathVariable Long ordenTrabajoId,
                        @RequestBody HorasTrabajadasDTO horasTrabajadasDTO) {
                int horas = horasTrabajadasDTO.getHorasTrabajadas();
                tallerService.agregarHorasAOrdenTrabajo(ordenTrabajoId, horas);
                return ResponseEntity.ok().build();
        }

        @PostMapping("/calcular-costo")
        public double calcularCosto(@RequestBody OrdenTrabajoDTO ordenTrabajoDTO) {
                // Calcular el costo total utilizando el servicio
                return tallerService.calcularCostoTotal(ordenTrabajoDTO);
        }

}
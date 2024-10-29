package com.example.taller.ordenes.application.controller;

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

import com.example.taller.empleados.application.EmpleadoApplicationService;
import com.example.taller.empleados.domain.dto.EmpleadoResponseDTO;
import com.example.taller.empleados.domain.model.Empleado;
import com.example.taller.horas.application.dto.HorasTrabajadasDTO;
import com.example.taller.ordenes.application.dto.OrdenTrabajoDTO;
import com.example.taller.ordenes.domain.dto.OrdenTrabajoResponseDTO;
import com.example.taller.ordenes.domain.model.OrdenTrabajo;
import com.example.taller.ordenes.infrastructure.port.TallerServicePort;
import com.example.taller.propietarios.application.dto.PropietarioVehiculoDTO;
import com.example.taller.propietarios.domain.dto.PropietarioResponseDTO;
import com.example.taller.propietarios.domain.model.Propietario;
import com.example.taller.repuestos.application.dto.RepuestoUtilizadoDTO;
import com.example.taller.repuestos.domain.dto.RepuestoUtilizadoResponseDTO;
import com.example.taller.repuestos.domain.model.RepuestoUtilizado;
import com.example.taller.vehiculos.domain.dto.VehiculoResponseDTO;
import com.example.taller.vehiculos.domain.model.Vehiculo;
import com.example.taller.ordenes.application.TallerApplicationService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ordenes")
@Tag(name = "Ordenes de trabajo", description = "Controlador para las ordenes de trabajo.")

public class OrdenTrabajoController {
        private final TallerApplicationService tallerApplicationService;

        public OrdenTrabajoController(TallerApplicationService tallerApplicationService){
                this.tallerApplicationService = tallerApplicationService;
        }

        @GetMapping
        @Operation(summary = "Listar ordenes de trabajo", description = "Obtiene una lista de todas las ordenes de trabajo registradas en el sistema")

        public ResponseEntity<List<OrdenTrabajoResponseDTO>> listarOrdenes() {
                List<OrdenTrabajo> ordenesTrabajo = tallerApplicationService.listarOrdenes();

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
        @Operation(summary = "Obtener orden de trabajo concreta", description = "Obtener una orden de trabajo a traves de su id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenTrabajoResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Orden de trabajo no encontrada", content = @Content)
        })
        public ResponseEntity<OrdenTrabajoResponseDTO> obtenerOrden( @Parameter(description = "Id de la orden de trabajo", required = true) @PathVariable Long id) {
                OrdenTrabajo ordenTrabajo = tallerApplicationService.obtenerOrdenTrabajo(id);
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
        @Operation(summary = "Crear ordenes de trabajo", description = "Crea una nueva orden de trabajo")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenTrabajoResponseDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Vehiculo no encontrado para el propietario, propietario no encontrado, el vehiculo ya tiene una orden de trabajo registrada, empleado no encontrado", content = @Content)
        })
        public ResponseEntity<OrdenTrabajo> crearOrdenTrabajo(@RequestBody OrdenTrabajoDTO ordenTrabajo) {
                OrdenTrabajo nuevaOrden = tallerApplicationService.crearOrdenTrabajo(ordenTrabajo);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarOrdenTrabajo(@PathVariable Long id) {
                tallerApplicationService.eliminarOrdenTrabajo(id);
                return ResponseEntity.noContent().build();
        }

        @PostMapping("/{ordenTrabajoId}/repuestos/{repuestoUtilizadoId}")
        @Operation(summary = "Añadir repuestos", description = "Añadir repuestos a una orden de trabajo")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                        @ApiResponse(responseCode = "500", description = "Repuesto no encontrado, orden de trabajo no encontrada", content = @Content)
        })
        public ResponseEntity<Void> agregarRepuestoAOrden(
                        @Parameter(description = "Id de la orden de trabajo", required = true) @PathVariable Long ordenTrabajoId,
                        @Parameter(description = "Codigo del repuesto en el inventario", required = true) @PathVariable String repuestoUtilizadoId,
                        @Parameter(description = "Datos del repuesto que se va a añadir", required = true) @RequestBody RepuestoUtilizadoDTO repuestoUtilizadoDTO) {
                int cantidad = repuestoUtilizadoDTO.getCantidadUtilizada();
                tallerApplicationService.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoId, cantidad);
                return ResponseEntity.ok().build();
        }

        @PostMapping("/{ordenTrabajoId}/horas")
        @Operation(summary = "Añadir horas", description = "Añadir horas trabajadas a una orden de trabajo")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                @ApiResponse(responseCode = "500", description = "Orden de trabajo no encontrada", content = @Content)
})
        public ResponseEntity<Void> agregarHorasAOrden(
                @Parameter(description = "Id de la orden de trabajo", required = true) @PathVariable Long ordenTrabajoId,
                @Parameter(description = "Numero de horas que se quieren añadir", required = true) @RequestBody HorasTrabajadasDTO horasTrabajadasDTO) {
                int horas = horasTrabajadasDTO.getHorasTrabajadas();
                tallerApplicationService.agregarHorasAOrdenTrabajo(ordenTrabajoId, horas);
                return ResponseEntity.ok().build();
        }

        @PostMapping("/calcular-costo")
        @Operation(summary = "Calcular coste", description = "Calcular el precio de la orden de trabajo sumando horas de trabajo y repuestos")

        public double calcularCosto(@RequestBody OrdenTrabajoDTO ordenTrabajoDTO) {
                // Calcular el costo total utilizando el servicio
                return tallerApplicationService.calcularCostoTotal(ordenTrabajoDTO);
        }

}

package com.example.taller.ordenes.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.RepuestoUtilizado.application.RepuestoUtilizadoRequestDTO;
import com.example.taller.RepuestoUtilizado.domain.RepuestoUtilizadoDTO;
import com.example.taller.empleados.domain.EmpleadoDTO;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.vehiculos.domain.VehiculoDTO;
import com.example.taller.ordenes.application.OrdenTrabajoMapper; // Asegúrate de que esto esté presente

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
        @Autowired
        private OrdenTrabajoServicePort tallerService;
        @Autowired
        private OrdenTrabajoMapper ordenTrabajoMapper;
        @GetMapping
        @Operation(summary = "Listar ordenes de trabajo", description = "Obtiene una lista de todas las ordenes de trabajo registradas en el sistema")

        public ResponseEntity<List<OrdenTrabajoDTO>> listarOrdenes() {
                List<OrdenTrabajoEntity> ordenesTrabajo = tallerService.listarOrdenes();
                List<OrdenTrabajoDTO> ordenesTrabajoDTO = ordenesTrabajo.stream()
                                .map(ordenTrabajoMapper::toDTO)
                                .toList();

                return ResponseEntity.ok(ordenesTrabajoDTO);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener orden de trabajo concreta", description = "Obtener una orden de trabajo a traves de su id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenTrabajoDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Orden de trabajo no encontrada", content = @Content)
        })
        public ResponseEntity<OrdenTrabajoDTO> obtenerOrden(
                        @Parameter(description = "Id de la orden de trabajo", required = true) @PathVariable Long id) {
                OrdenTrabajoEntity ordenTrabajo = tallerService.obtenerOrdenTrabajo(id);
                if (ordenTrabajo == null) {
                        return ResponseEntity.notFound().build();
                }

                // Mapeo de repuestos utilizados a DTO
                List<RepuestoUtilizadoDTO> repuestosUtilizados = ordenTrabajo.getRepuestoUtilizado().stream()
                                .map(repuestoUtilizado -> new RepuestoUtilizadoDTO(
                                                repuestoUtilizado.getRepuesto().getCodigoInventario(),
                                                repuestoUtilizado.getRepuesto().getNombre(),
                                                repuestoUtilizado.getRepuesto().getPrecio(),
                                                repuestoUtilizado.getCantidad()))
                                .toList();

                // Mapeo de los vehículos del propietario a DTO
                List<VehiculoDTO> vehiculos = ordenTrabajo.getPropietario().getVehiculos().stream()
                                .map(vehiculo -> new VehiculoDTO(
                                                vehiculo.getPatente(),
                                                vehiculo.getMarca(),
                                                vehiculo.getModelo()))
                                .toList();

                // Mapeo del propietario a DTO
                PropietarioDTO propietarioDTO = new PropietarioDTO(
                                ordenTrabajo.getPropietario().getDni(),
                                ordenTrabajo.getPropietario().getNombre(),
                                ordenTrabajo.getPropietario().getTelefono(),
                                vehiculos);

                EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                                ordenTrabajo.getEmpleado().getId(),
                                ordenTrabajo.getEmpleado().getNombre(),
                                ordenTrabajo.getEmpleado().getApellido(), // Añadir apellido
                                ordenTrabajo.getEmpleado().getTelefono());

                // Crear DTO de respuesta de la orden de trabajo
                OrdenTrabajoDTO responseDTO = new OrdenTrabajoDTO(
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
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenTrabajoDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Vehiculo no encontrado para el propietario, propietario no encontrado, el vehiculo ya tiene una orden de trabajo registrada, empleado no encontrado", content = @Content)
        })
        public ResponseEntity<OrdenTrabajoEntity> crearOrdenTrabajo(@RequestBody OrdenTrabajoRequestDTO ordenTrabajo) {
                OrdenTrabajoEntity nuevaOrden = ordenTrabajoService.crearOrdenTrabajo(ordenTrabajo);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarOrdenTrabajo(@PathVariable Long id) {
                tallerService.eliminarOrdenTrabajo(id);
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
                        @Parameter(description = "Datos del repuesto que se va a añadir", required = true) @RequestBody RepuestoUtilizadoRequestDTO repuestoUtilizadoDTO) {
                int cantidad = repuestoUtilizadoDTO.getCantidadUtilizada();
                tallerService.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoId, cantidad);
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
                        @Parameter(description = "Numero de horas que se quieren añadir", required = true) @RequestBody HorasTrabajadasRequestDTO horasTrabajadasDTO) {
                int horas = horasTrabajadasDTO.getHorasTrabajadas();
                tallerService.agregarHorasAOrdenTrabajo(ordenTrabajoId, horas);
                return ResponseEntity.ok().build();
        }

        @PostMapping("/calcular-costo")
        @Operation(summary = "Calcular coste", description = "Calcular el precio de la orden de trabajo sumando horas de trabajo y repuestos")

        public double calcularCosto(@RequestBody OrdenTrabajoRequestDTO ordenTrabajoDTO) {
                // Calcular el costo total utilizando el servicio
                return tallerService.calcularCostoTotal(ordenTrabajoDTO);
        }

}

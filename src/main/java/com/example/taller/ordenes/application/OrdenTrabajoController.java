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
import com.example.taller.ordenes.utilities.OrdenTrabajoMapper;
import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.vehiculos.domain.VehiculoDTO;

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
        private OrdenTrabajoApplicationService ordenTrabajoApplicationService;

        @GetMapping
        @Operation(summary = "Listar ordenes de trabajo", description = "Obtiene una lista de todas las ordenes de trabajo registradas en el sistema")
        public ResponseEntity<List<OrdenTrabajoDTO>> listarOrdenes() {
                List<OrdenTrabajoDTO> ordenesTrabajoDTO = ordenTrabajoApplicationService.listarOrdenes();
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
                OrdenTrabajoDTO ordenTrabajoDTO = ordenTrabajoApplicationService.obtenerOrdenTrabajo(id);
                if (ordenTrabajoDTO != null) {
                        return ResponseEntity.ok(ordenTrabajoDTO); // Retorna 200 OK si existe
                } else {
                        return ResponseEntity.notFound().build(); // Retorna 404 Not Found si no
                }
        }

        @PostMapping
        @Operation(summary = "Crear ordenes de trabajo", description = "Crea una nueva orden de trabajo")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenTrabajoDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Vehiculo no encontrado para el propietario, propietario no encontrado, el vehiculo ya tiene una orden de trabajo registrada, empleado no encontrado", content = @Content)
        })
        public ResponseEntity<OrdenTrabajoDTO> crearOrdenTrabajo(
                        @RequestBody OrdenTrabajoRequestDTO ordenTrabajoRequestDTO) {
                OrdenTrabajoDTO ordenCreada = ordenTrabajoApplicationService.crearOrdenTrabajo(ordenTrabajoRequestDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(ordenCreada);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarOrdenTrabajo(@PathVariable Long id) {
                ordenTrabajoApplicationService.eliminarOrdenTrabajo(id);
                return ResponseEntity.noContent().build();
        }

        @PostMapping("/{ordenTrabajoId}/horas")
        @Operation(summary = "Añadir horas", description = "Añadir horas trabajadas a una orden de trabajo")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                        @ApiResponse(responseCode = "500", description = "Orden de trabajo no encontrada", content = @Content)
        })
        public ResponseEntity<OrdenTrabajoDTO> agregarHorasAOrden(
                        @Parameter(description = "Id de la orden de trabajo", required = true) @PathVariable Long ordenTrabajoId,
                        @Parameter(description = "Numero de horas que se quieren añadir", required = true) @RequestBody HorasTrabajadasRequestDTO horasTrabajadasDTO) {
                int horas = horasTrabajadasDTO.getHorasTrabajadas();
                try {
                        OrdenTrabajoDTO ordenActualizada = ordenTrabajoApplicationService.agregarHorasAOrdenTrabajo(ordenTrabajoId, horas);
                        return ResponseEntity.ok(ordenActualizada);
                } catch (RuntimeException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
        }

        @PostMapping("/calcular-costo")
        @Operation(summary = "Calcular coste", description = "Calcular el precio de la orden de trabajo sumando horas de trabajo y repuestos")

        public double calcularCosto(@RequestBody Long ordenTrabajoDTO) {
                // Calcular el costo total utilizando el servicio
                return ordenTrabajoApplicationService.calcularCostoTotal(ordenTrabajoDTO);
        }

}

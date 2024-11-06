package com.example.taller.RepuestoUtilizado.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.RepuestoUtilizado.infrastructure.adapter.RepuestoUtilizadoEntity;
import com.example.taller.RepuestoUtilizado.infrastructure.port.RepuestoUtilizadoServicePort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/repuestos-utilizados")
public class RepuestoUtilizadoController {
@Autowired
    private RepuestoUtilizadoServicePort repuestoUtilizadoService;


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
                ordenTrabajoApplicationService.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoId,
                                cantidad);
                return ResponseEntity.ok().build();
        }
        
    @PostMapping
    public ResponseEntity<RepuestoUtilizadoEntity> crearRepuestoUtilizado(@RequestBody RepuestoUtilizadoEntity repuestoUtilizado) {
        RepuestoUtilizadoEntity nuevoRepuestoUtilizado = repuestoUtilizadoService.crearRepuestoUtilizado(repuestoUtilizado);
        return ResponseEntity.ok(nuevoRepuestoUtilizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepuestoUtilizadoEntity> obtenerRepuestoUtilizadoPorId(@PathVariable String id) {
        RepuestoUtilizadoEntity repuestoUtilizado = repuestoUtilizadoService.obtenerRepuestoUtilizadoPorId(id);
        return ResponseEntity.ok(repuestoUtilizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRepuestoUtilizado(@PathVariable String id) {
        repuestoUtilizadoService.eliminarRepuestoUtilizado(id);
        return ResponseEntity.noContent().build();
    }
}

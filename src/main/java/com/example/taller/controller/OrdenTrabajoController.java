package com.example.taller.controller;

import java.util.List;

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

import com.example.taller.dto.OrdenTrabajoDTO;
import com.example.taller.model.OrdenTrabajo;
import com.example.taller.service.TallerServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenTrabajoController {
@Autowired
    private TallerServiceInterface tallerService;

     @Operation(summary = "Listar todas las órdenes de trabajo", 
               description = "Obtiene una lista de todas las órdenes de trabajo existentes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")
    })
    @GetMapping
    public List<OrdenTrabajo> listarOrdenes() {
        return tallerService.listarOrdenes();
    }

    @Operation(summary = "Obtener una orden de trabajo por ID", 
    description = "Devuelve la orden de trabajo correspondiente al ID proporcionado.")
@ApiResponses(value = {
@ApiResponse(responseCode = "200"),
@ApiResponse(responseCode = "404")
})
    @GetMapping("/{id}")
    public ResponseEntity<OrdenTrabajo> obtenerOrden(@PathVariable Long id) {
        OrdenTrabajo ordenTrabajo = tallerService.buscarOrdenTrabajoPorId(id);
        return ordenTrabajo != null ? ResponseEntity.ok(ordenTrabajo) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear una nueva orden de trabajo", 
    description = "Crea una nueva orden de trabajo con los detalles proporcionados.")
@ApiResponses(value = {
@ApiResponse(responseCode = "201"),
@ApiResponse(responseCode = "400")
})
    @PostMapping
    public ResponseEntity<OrdenTrabajo> crearOrdenTrabajo(@RequestBody OrdenTrabajoDTO ordenTrabajo) {
        OrdenTrabajo nuevaOrden = tallerService.crearOrdenTrabajo(ordenTrabajo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
    }

    @Operation(summary = "Modificar una orden de trabajo existente", 
    description = "Actualiza la orden de trabajo correspondiente al ID proporcionado.")
@ApiResponses(value = {
@ApiResponse(responseCode = "200"),
@ApiResponse(responseCode = "404"),
@ApiResponse(responseCode = "400")
})
      @PutMapping("/{id}")
    public ResponseEntity<OrdenTrabajo> modificarOrdenTrabajo(@PathVariable Long id, @RequestBody OrdenTrabajoDTO dto) {
        OrdenTrabajo ordenModificada = tallerService.modificarOrdenTrabajo(id, dto);
        return ResponseEntity.ok(ordenModificada);
    }

    @Operation(summary = "Eliminar una orden de trabajo", 
    description = "Elimina la orden de trabajo correspondiente al ID proporcionado.")
@ApiResponses(value = {
@ApiResponse(responseCode = "204"),
@ApiResponse(responseCode = "404")
})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrdenTrabajo(@PathVariable Long id) {
        tallerService.eliminarOrdenTrabajo(id);
        return ResponseEntity.noContent().build();
    }
}

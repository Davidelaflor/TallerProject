package com.example.taller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.dto.NuevoPropietarioDTO;
import com.example.taller.dto.PropietarioDTO;
import com.example.taller.dto.PropietarioResponseDTO;
import com.example.taller.dto.PropietarioVehiculoDTO;
import com.example.taller.dto.VehiculoDTO;
import com.example.taller.model.Propietario;
import com.example.taller.service.PropietarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/propietarios")
@Tag(name = "Propietarios", description = "API para la gestión de propietarios y vehículos")

public class PropietarioController {
    @Autowired
    private PropietarioService propietarioService;

    @GetMapping
    @Operation(summary = "Listar propietarios", description = "Obtiene una lista de todos los propietarios registrados en el sistema")

    public List<Propietario> listarPropietarios() {
        return propietarioService.listarPropietarios();
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo propietario con su vehículo", description = "Este endpoint es para crear un nuevo propietario junto con su vehículo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietario y vehículo creados con exito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PropietarioVehiculoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Vehiculo o propietario ya registrado", content = @Content)
    })

    public ResponseEntity<?> crearPropietarioConVehiculo(        @Parameter(description = "Datos del propietario y vehículo a crear", required = true)
    @RequestBody PropietarioVehiculoDTO dto
    ) {
        try {
            Propietario nuevoPropietario = propietarioService.crearPropietarioConVehiculo(dto.getPropietario(),
                    dto.getVehiculo());
            return ResponseEntity.ok(nuevoPropietario);
        } catch (IllegalArgumentException e) {
            // Enviar un error 400 si ya existe el propietario o el vehículo
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{dni}")
    @Operation(summary = "Obtener propietario por DNI", description = "Recupera los detalles de un propietario utilizando su DNI, incluyendo sus vehículos asociados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Propietario.class))),
            @ApiResponse(responseCode = "404", description = "Propietario no encontrado"),
    })
    public ResponseEntity<Propietario> obtenerPropietarioPorDni(
            @Parameter(description = "DNI del propietario que se quiere buscar", required = true) @PathVariable String dni) {
        Propietario propietario = propietarioService.obtenerPropietarioPorDni(dni);
        return ResponseEntity.ok(propietario);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> eliminarPropietario(@PathVariable String dni) {
        propietarioService.eliminarPropietario(dni);
        return ResponseEntity.noContent().build();
    }
}

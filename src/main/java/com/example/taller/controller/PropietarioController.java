package com.example.taller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.dto.PropietarioDTO;
import com.example.taller.dto.PropietarioVehiculoDTO;
import com.example.taller.dto.VehiculoDTO;
import com.example.taller.model.Propietario;
import com.example.taller.service.PropietarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {
    @Autowired
    private PropietarioService propietarioService;

      @Operation(summary = "Listar todos los propietarios", description = "Obtiene una lista de todos los propietarios.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "500")
    })
    @GetMapping
    public List<Propietario> listarPropietarios() {
        return propietarioService.listarPropietarios();
    }

    @Operation(summary = "Crear propietario con vehículo", description = "Crea un nuevo propietario junto con su vehículo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "500")
    })
    @PostMapping
    public ResponseEntity<Propietario> crearPropietarioConVehiculo(@RequestBody PropietarioVehiculoDTO dto) {
        Propietario nuevoPropietario = propietarioService.crearPropietarioConVehiculo(dto.getPropietario(),
                dto.getVehiculo());
        return ResponseEntity.ok(nuevoPropietario);
    }

    @Operation(summary = "Obtener propietario por DNI", description = "Obtiene un propietario específico utilizando su DNI.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "500")
    })
    @GetMapping("/{dni}")
    public ResponseEntity<Propietario> obtenerPropietarioPorDni(@PathVariable String dni) {
        Propietario propietario = propietarioService.obtenerPropietarioPorDni(dni);
        return ResponseEntity.ok(propietario);
    }

    @Operation(summary = "Eliminar propietario por DNI", description = "Elimina un propietario específico utilizando su DNI.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "500")
    })
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> eliminarPropietario(@PathVariable String dni) {
        propietarioService.eliminarPropietario(dni);
        return ResponseEntity.noContent().build();
    }
}

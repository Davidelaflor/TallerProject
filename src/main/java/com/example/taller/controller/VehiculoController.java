package com.example.taller.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.dto.CrearVehiculoParaPropietarioDTO;
import com.example.taller.dto.PropietarioVehiculoDTO;
import com.example.taller.dto.VehiculoDTO;
import com.example.taller.model.Vehiculo;
import com.example.taller.service.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/vehiculos")
@Tag(name = "Vehiculos", description = "Controlador para la gestión de vehiculos.")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<Vehiculo> crearVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo nuevoVehiculo = vehiculoService.crearVehiculo(vehiculo);
        return ResponseEntity.ok(nuevoVehiculo);
    }

    @GetMapping // http://localhost:8080/api/vehiculos?dni=12345678i
    @Operation(summary = "Listado de vehiculos", description = "Obtiene una lista de todos los vehiculos registrados en el sistema para un propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content (mediaType = "application/json", schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "400", description = "No se encontraron vehiculos para el propietario con ese dni", content = @Content)
    })
    public ResponseEntity<List<Vehiculo>> obtenerVehiculosPorDni(@Parameter(description = "Dni del propietario", required = true) @RequestParam String dni) {
        if (dni != null) {
            List<Vehiculo> vehiculos = vehiculoService.obtenerVehiculosPorDni(dni);
            return ResponseEntity.ok(vehiculos);
        } else {
            // Si no se proporciona un DNI, devuelve todos los vehículos
            List<Vehiculo> vehiculos = vehiculoService.obtenerTodosLosVehiculos();
            return ResponseEntity.ok(vehiculos);
        }
    }

    @GetMapping("/{patente}")
    @Operation(summary = "Buscar vehiculo", description = "Buscar un vehiculo registrado en el sistema a traves de sus patente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content (mediaType = "application/json", schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Vehiculo no encontrado", content = @Content)
    })
    public ResponseEntity<Vehiculo> obtenerVehiculoPorPatente(@Parameter(description = "Patente del vehiculo", required = true) @PathVariable String patente) {
        Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorPatente(patente);
        return ResponseEntity.ok(vehiculo);
    }

    /*
     * @GetMapping
     * public ResponseEntity<List<Vehiculo>> obtenerTodosLosVehiculos() {
     * List<Vehiculo> vehiculos = vehiculoService.obtenerTodosLosVehiculos();
     * return ResponseEntity.ok(vehiculos);
     * }
     */

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable String patente) {
        vehiculoService.eliminarVehiculo(patente);
        return ResponseEntity.noContent().build();
    }

    private static final Logger logger = LoggerFactory.getLogger(VehiculoController.class);

    @PostMapping("/propietario/{dni}")
    @Operation(summary = "Agregar vehiculos", description = "Agregar vehiculos nuevos a un propietario ya registrado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content (mediaType = "application/json", schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Propietario no encontrado. Vehiculo ya registrado para este propietario. Vehiculo ya registrado para otro propietario", content = @Content)
    })
    public ResponseEntity<Vehiculo> agregarVehiculoAPropietario(@Parameter(description = "Dni del propietario", required = true) @PathVariable String dni,
    @Parameter(description = "Datos del vehiculo que se va a registrar", required = true) @RequestBody CrearVehiculoParaPropietarioDTO vehiculoDTO) {
        try {
            Vehiculo nuevoVehiculo = vehiculoService.agregarVehiculoAPropietario(dni, vehiculoDTO);
            return ResponseEntity.ok(nuevoVehiculo);
        } catch (RuntimeException e) {
            logger.error("Error al agregar vehículo al propietario con DNI {}: {}", dni, e.getMessage());
            return ResponseEntity.badRequest().body(null); // Devolver 400 si ocurre un error
        }
    }
}

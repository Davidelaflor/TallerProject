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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.model.Vehiculo;
import com.example.taller.service.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    @Operation(summary = "Crear un nuevo vehículo", description = "Este endpoint permite crear un nuevo vehículo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @PostMapping
    public ResponseEntity<Vehiculo> crearVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo nuevoVehiculo = vehiculoService.crearVehiculo(vehiculo);
        return ResponseEntity.ok(nuevoVehiculo);
    }

    @Operation(summary = "Obtener vehículos por DNI", description = "Este endpoint permite obtener una lista de vehículos asociados a un propietario por su DNI. Para acceder, use: http://localhost:8080/api/vehiculos?dni=dni concreto\"")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })

    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerVehiculosPorDni(@RequestParam String dni) {
        if (dni != null) {
            List<Vehiculo> vehiculos = vehiculoService.obtenerVehiculosPorDni(dni);
            return ResponseEntity.ok(vehiculos);
        } else {
            // Si no se proporciona un DNI, devuelve todos los vehículos
            List<Vehiculo> vehiculos = vehiculoService.obtenerTodosLosVehiculos();
            return ResponseEntity.ok(vehiculos);
        }
    }

    @Operation(summary = "Obtener un vehículo por su patente", description = "Este endpoint permite obtener un vehículo específico utilizando su patente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })
    @GetMapping("/{patente}")
    public ResponseEntity<Vehiculo> obtenerVehiculoPorPatente(@PathVariable String patente) {
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

    @Operation(summary = "Eliminar un vehículo", description = "Este endpoint permite eliminar un vehículo específico utilizando su patente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404")
    })
    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable String patente) {
        vehiculoService.eliminarVehiculo(patente);
        return ResponseEntity.noContent().build();
    }
}

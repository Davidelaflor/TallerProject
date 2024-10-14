package com.example.taller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.taller.model.Vehiculo;
import com.example.taller.service.VehiculoService;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
  @Autowired
    private VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<Vehiculo> crearVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo nuevoVehiculo = vehiculoService.crearVehiculo(vehiculo);
        return ResponseEntity.ok(nuevoVehiculo);
    }

    @GetMapping("/{patente}")
    public ResponseEntity<Vehiculo> obtenerVehiculoPorPatente(@PathVariable String patente) {
        Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorPatente(patente);
        return ResponseEntity.ok(vehiculo);
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerTodosLosVehiculos() {
        List<Vehiculo> vehiculos = vehiculoService.obtenerTodosLosVehiculos();
        return ResponseEntity.ok(vehiculos);
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable String patente) {
        vehiculoService.eliminarVehiculo(patente);
        return ResponseEntity.noContent().build();
    }
}

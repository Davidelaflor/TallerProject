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

import com.example.taller.model.RepuestoUtilizado;
import com.example.taller.service.RepuestoUtilizadoService;

@RestController
@RequestMapping("/api/repuestos-utilizados")
public class RepuestoUtilizadoController {
@Autowired
    private RepuestoUtilizadoService repuestoUtilizadoService;

    @PostMapping
    public ResponseEntity<RepuestoUtilizado> crearRepuestoUtilizado(@RequestBody RepuestoUtilizado repuestoUtilizado) {
        RepuestoUtilizado nuevoRepuestoUtilizado = repuestoUtilizadoService.crearRepuestoUtilizado(repuestoUtilizado);
        return ResponseEntity.ok(nuevoRepuestoUtilizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepuestoUtilizado> obtenerRepuestoUtilizadoPorId(@PathVariable String id) {
        RepuestoUtilizado repuestoUtilizado = repuestoUtilizadoService.obtenerRepuestoUtilizadoPorId(id);
        return ResponseEntity.ok(repuestoUtilizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRepuestoUtilizado(@PathVariable String id) {
        repuestoUtilizadoService.eliminarRepuestoUtilizado(id);
        return ResponseEntity.noContent().build();
    }
}

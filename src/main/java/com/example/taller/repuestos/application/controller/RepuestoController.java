package com.example.taller.repuestos.application.controller;
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

import com.example.taller.propietarios.domain.model.Propietario;
import com.example.taller.repuestos.domain.model.Repuesto;
import com.example.taller.repuestos.infrastructure.port.RepuestoServicePort;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {
@Autowired
    private RepuestoServicePort repuestoService;

@GetMapping
    public List<Repuesto> listarReouestos() {
        return repuestoService.listarRepuestos();
    }
    @PostMapping
    public ResponseEntity<Repuesto> crearRepuesto(@RequestBody Repuesto repuesto) {
        Repuesto nuevoRepuesto = repuestoService.crearRepuesto(repuesto);
        return ResponseEntity.ok(nuevoRepuesto);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Repuesto> obtenerRepuestoPorCodigo(@PathVariable Long codigo) {
        Repuesto repuesto = repuestoService.obtenerRepuestoPorCodigo(codigo);
        return ResponseEntity.ok(repuesto);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminarRepuesto(@PathVariable Long codigo) {
        repuestoService.eliminarRepuesto(codigo);
        return ResponseEntity.noContent().build();
    }
}

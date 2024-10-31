package com.example.taller.repuestos.application;
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

import com.example.taller.repuestos.domain.RepuestoDTO;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {
@Autowired
    private RepuestoApplicationService repuestoApplicationService;

@GetMapping
    public List<RepuestoDTO> listarReouestos() {
        return repuestoApplicationService.listarRepuestos();
    }
    @PostMapping
    public ResponseEntity<RepuestoDTO> crearRepuesto(@RequestBody RepuestoDTO repuesto) {
        RepuestoDTO nuevoRepuesto = repuestoApplicationService.crearRepuesto(repuesto);
        return ResponseEntity.ok(nuevoRepuesto);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<RepuestoDTO> obtenerRepuestoPorCodigo(@PathVariable Long codigo) {
        RepuestoDTO repuesto = repuestoApplicationService.obtenerRepuestoPorCodigo(codigo);
        return ResponseEntity.ok(repuesto);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminarRepuesto(@PathVariable Long codigo) {
        repuestoApplicationService.eliminarRepuesto(codigo);
        return ResponseEntity.noContent().build();
    }
}

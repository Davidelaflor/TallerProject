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

import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;
import com.example.taller.repuestos.infrastructure.port.RepuestoServicePort;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {
@Autowired
    private RepuestoServicePort repuestoService;

@GetMapping
    public List<RepuestoEntity> listarReouestos() {
        return repuestoService.listarRepuestos();
    }
    @PostMapping
    public ResponseEntity<RepuestoEntity> crearRepuesto(@RequestBody RepuestoEntity repuesto) {
        RepuestoEntity nuevoRepuesto = repuestoService.crearRepuesto(repuesto);
        return ResponseEntity.ok(nuevoRepuesto);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<RepuestoEntity> obtenerRepuestoPorCodigo(@PathVariable Long codigo) {
        RepuestoEntity repuesto = repuestoService.obtenerRepuestoPorCodigo(codigo);
        return ResponseEntity.ok(repuesto);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminarRepuesto(@PathVariable Long codigo) {
        repuestoService.eliminarRepuesto(codigo);
        return ResponseEntity.noContent().build();
    }
}

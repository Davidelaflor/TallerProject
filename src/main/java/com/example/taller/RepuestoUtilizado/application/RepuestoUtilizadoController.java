package com.example.taller.RepuestoUtilizado.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.RepuestoUtilizado.infrastructure.adapter.RepuestoUtilizadoEntity;
import com.example.taller.RepuestoUtilizado.infrastructure.port.RepuestoUtilizadoServicePort;

@RestController
@RequestMapping("/api/repuestos-utilizados")
public class RepuestoUtilizadoController {
@Autowired
    private RepuestoUtilizadoServicePort repuestoUtilizadoService;

    @PostMapping
    public ResponseEntity<RepuestoUtilizadoEntity> crearRepuestoUtilizado(@RequestBody RepuestoUtilizadoEntity repuestoUtilizado) {
        RepuestoUtilizadoEntity nuevoRepuestoUtilizado = repuestoUtilizadoService.crearRepuestoUtilizado(repuestoUtilizado);
        return ResponseEntity.ok(nuevoRepuestoUtilizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepuestoUtilizadoEntity> obtenerRepuestoUtilizadoPorId(@PathVariable String id) {
        RepuestoUtilizadoEntity repuestoUtilizado = repuestoUtilizadoService.obtenerRepuestoUtilizadoPorId(id);
        return ResponseEntity.ok(repuestoUtilizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRepuestoUtilizado(@PathVariable String id) {
        repuestoUtilizadoService.eliminarRepuestoUtilizado(id);
        return ResponseEntity.noContent().build();
    }
}

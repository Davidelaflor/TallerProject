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

import com.example.taller.dto.PropietarioDTO;
import com.example.taller.model.Propietario;
import com.example.taller.service.PropietarioService;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {
    @Autowired
    private PropietarioService propietarioService;

    @PostMapping
    public ResponseEntity<Propietario> crearPropietario(@RequestBody PropietarioDTO propietario) {
        Propietario nuevoPropietario = propietarioService.crearPropietario(propietario);
        return ResponseEntity.ok(nuevoPropietario);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Propietario> obtenerPropietarioPorDni(@PathVariable String dni) {
        Propietario propietario = propietarioService.obtenerPropietarioPorDni(dni);
        return ResponseEntity.ok(propietario);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> eliminarPropietario(@PathVariable String dni) {
        propietarioService.eliminarPropietario(dni);
        return ResponseEntity.noContent().build();
    }
}

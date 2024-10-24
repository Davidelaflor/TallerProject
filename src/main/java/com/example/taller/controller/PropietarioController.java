package com.example.taller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.dto.NuevoPropietarioDTO;
import com.example.taller.dto.PropietarioDTO;
import com.example.taller.dto.PropietarioVehiculoDTO;
import com.example.taller.dto.VehiculoDTO;
import com.example.taller.model.Propietario;
import com.example.taller.service.PropietarioService;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {
    @Autowired
    private PropietarioService propietarioService;

    @GetMapping
    public List<Propietario> listarPropietarios() {
        return propietarioService.listarPropietarios();
    }

    @PostMapping
    public ResponseEntity<?> crearPropietarioConVehiculo(@RequestBody PropietarioVehiculoDTO dto) {
        try {
            Propietario nuevoPropietario = propietarioService.crearPropietarioConVehiculo(dto.getPropietario(), dto.getVehiculo());
            return ResponseEntity.ok(nuevoPropietario);
        } catch (IllegalArgumentException e) {
            // Enviar un error 400 si ya existe el propietario o el vehículo
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
 // Nuevo método para crear propietario sin vehículo
    @PostMapping("/sin-vehiculo")
    public ResponseEntity<Propietario> crearPropietario(@RequestBody NuevoPropietarioDTO propietarioDTO) {
        Propietario nuevoPropietario = propietarioService.crearPropietario(propietarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPropietario);
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

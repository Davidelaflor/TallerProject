package com.example.taller.empleados.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.empleados.application.EmpleadoApplicationService;
import com.example.taller.empleados.domain.dto.EmpleadoDTO;
import com.example.taller.empleados.domain.dto.EmpleadoResponseDTO;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
private final EmpleadoApplicationService empleadoApplicationService;

    public EmpleadoController(EmpleadoApplicationService empleadoApplicationService) {
        this.empleadoApplicationService = empleadoApplicationService;
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crearEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoResponseDTO nuevoEmpleado = empleadoApplicationService.crearEmpleado(empleadoDTO);
        return ResponseEntity.ok(nuevoEmpleado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerEmpleadoPorId(@PathVariable Long id) {
        EmpleadoResponseDTO empleado = empleadoApplicationService.obtenerEmpleadoPorId(id);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoApplicationService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}

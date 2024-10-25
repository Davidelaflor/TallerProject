package com.example.taller.empleados.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.taller.empleados.infrastructure.adapter.Empleado;
import com.example.taller.empleados.infrastructure.port.EmpleadoServicePort;

public class EmpleadoApplicationService {
	// private EmpleadoService EmpleadoService;
	// private EmpleadoValidationService validationService;

	 public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
				// sección de validaciones
				// validaciones propias de empleado
				// validaciones de negocio y contra componentes
				// PropietarioService.exitsById(id)
        Empleado nuevoEmpleado = empleadoService.crearEmpleado(empleado);
        return ResponseEntity.ok(nuevoEmpleado);
    }
}

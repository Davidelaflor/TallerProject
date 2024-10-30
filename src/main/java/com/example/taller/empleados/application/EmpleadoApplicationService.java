package com.example.taller.empleados.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.empleados.domain.EmpleadoDTO;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.empleados.infrastructure.port.EmpleadoServicePort;

@Service
public class EmpleadoApplicationService {
 @Autowired
    private EmpleadoServicePort empleadoServicePort;

    public EmpleadoDTO crearEmpleado(EmpleadoRequestDTO empleadoDTO) {
        // Lógica de validación
        return empleadoServicePort.crearEmpleado(empleadoDTO);
    }

    public EmpleadoEntity obtenerEmpleadoPorId(Long id) {
        return empleadoServicePort.obtenerEmpleadoPorId(id);
    }

    public void eliminarEmpleado(Long id) {
        empleadoServicePort.eliminarEmpleado(id);
    }
}

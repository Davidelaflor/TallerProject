 package com.example.taller.empleados.application;

import com.example.taller.empleados.application.dto.EmpleadoDTO;
import com.example.taller.empleados.domain.dto.EmpleadoResponseDTO;
import com.example.taller.empleados.domain.model.Empleado;
import com.example.taller.empleados.infrastructure.port.EmpleadoServicePort;


public class EmpleadoApplicationService {
private final EmpleadoServicePort empleadoServicePort;

    public EmpleadoApplicationService(EmpleadoServicePort empleadoServicePort) {
        this.empleadoServicePort = empleadoServicePort;
    }

    public EmpleadoResponseDTO crearEmpleado(EmpleadoDTO empleadoDTO) {
        Empleado empleado = new Empleado(null, empleadoDTO.getNombre(), empleadoDTO.getApellido(), empleadoDTO.getTelefono());
        Empleado nuevoEmpleado = empleadoServicePort.crearEmpleado(empleado);
        return new EmpleadoResponseDTO(nuevoEmpleado.getId(), nuevoEmpleado.getNombre(), nuevoEmpleado.getApellido(), nuevoEmpleado.getTelefono());
    }

    public EmpleadoResponseDTO obtenerEmpleadoPorId(Long id) {
        Empleado empleado = empleadoServicePort.obtenerEmpleadoPorId(id);
        return new EmpleadoResponseDTO(empleado.getId(), empleado.getNombre(), empleado.getApellido(), empleado.getTelefono());
    }

    public void eliminarEmpleado(Long id) {
        empleadoServicePort.eliminarEmpleado(id);
    }
}

package com.example.taller.empleados.infrastructure.port;

public interface EmpleadoServicePort {
  EmpleadoResponseDTO crearEmpleado(EmpleadoDTO empleado);

    Empleado obtenerEmpleadoPorId(Long id);

    void eliminarEmpleado(Long id);
}

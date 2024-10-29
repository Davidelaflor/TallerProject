package com.example.taller.empleados.infrastructure.port;

import com.example.taller.empleados.application.EmpleadoRequestDTO;
import com.example.taller.empleados.domain.EmpleadoResponseDTO;
import com.example.taller.empleados.infrastructure.adapter.Empleado;

public interface EmpleadoServicePort {
  EmpleadoResponseDTO crearEmpleado(EmpleadoRequestDTO empleadoDTO);
  Empleado obtenerEmpleadoPorId(Long id);
  void eliminarEmpleado(Long id);
}

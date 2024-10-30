package com.example.taller.empleados.infrastructure.port;

import com.example.taller.empleados.application.EmpleadoRequestDTO;
import com.example.taller.empleados.domain.EmpleadoDTO;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;

public interface EmpleadoServicePort {
  EmpleadoDTO crearEmpleado(EmpleadoRequestDTO empleadoDTO);
  EmpleadoEntity obtenerEmpleadoPorId(Long id);
  void eliminarEmpleado(Long id);
}

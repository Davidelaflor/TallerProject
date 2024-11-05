package com.example.taller.empleados.infrastructure.port;

import java.util.Optional;

import com.example.taller.empleados.application.EmpleadoRequestDTO;
import com.example.taller.empleados.domain.EmpleadoDTO;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;

public interface EmpleadoServicePort {
  EmpleadoDTO crearEmpleado(EmpleadoRequestDTO empleadoDTO);

  EmpleadoEntity obtenerEmpleadoPorId(Long id);

  void eliminarEmpleado(Long id);

  Optional<EmpleadoEntity> findById(Long id);

  boolean existsById(Long empleadoId);

}

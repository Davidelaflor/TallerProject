package com.example.taller.service;

import com.example.taller.model.Empleado;

public interface EmpleadoService {
  Empleado crearEmpleado(Empleado empleado);

    Empleado obtenerEmpleadoPorId(Long id);

    void eliminarEmpleado(Long id);
}

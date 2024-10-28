package com.example.taller.empleados.domain.port;

import com.example.taller.empleados.domain.model.Empleado;

public interface EmpleadoServicePort {
    Empleado crearEmpleado(Empleado empleado);
    Empleado obtenerEmpleadoPorId(Long id);
    void eliminarEmpleado(Long id);
}

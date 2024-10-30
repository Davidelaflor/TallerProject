package com.example.taller.empleados.infrastructure.adapter;
import com.example.taller.empleados.application.EmpleadoRequestDTO;

public class EmpleadoMapper {
    public static EmpleadoEntity toEntity(EmpleadoRequestDTO empleadoDTO) {
        return EmpleadoEntity.builder()
                .nombre(empleadoDTO.getNombre())
                .apellido(empleadoDTO.getApellido())
                .telefono(empleadoDTO.getTelefono())
                .build();
    }
}

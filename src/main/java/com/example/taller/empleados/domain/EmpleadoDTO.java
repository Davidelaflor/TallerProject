package com.example.taller.empleados.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;

}

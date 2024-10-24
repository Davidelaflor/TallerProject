package com.example.taller.dto;

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

public class PropietarioDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
}

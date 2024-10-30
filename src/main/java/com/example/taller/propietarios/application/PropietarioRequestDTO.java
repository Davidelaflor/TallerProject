package com.example.taller.propietarios.application;

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

public class PropietarioRequestDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
}

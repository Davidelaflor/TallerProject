package com.example.taller.repuestos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RepuestoDTO {
    private String codigoInventario;
    private String nombre;
    private double precio;
    private Integer cantidad;
}

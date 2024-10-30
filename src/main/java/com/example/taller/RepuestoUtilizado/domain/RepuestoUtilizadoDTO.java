package com.example.taller.RepuestoUtilizado.domain;

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
public class RepuestoUtilizadoDTO {
    private String codigoInventario; // Código del repuesto
    private String nombre;           // Nombre del repuesto
    private double precio;          // Precio del repuesto
    private int cantidad;           // Cantidad utilizada
}

package com.example.taller.vehiculos.application.dto;

import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CrearVehiculoParaPropietarioDTO {
    private String patente;
    private String marca;
    private String modelo;
}

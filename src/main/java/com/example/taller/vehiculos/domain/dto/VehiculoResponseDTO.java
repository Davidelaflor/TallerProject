package com.example.taller.vehiculos.domain.dto;

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
public class VehiculoResponseDTO {
    private String patente;
    private String marca;
    private String modelo;
}

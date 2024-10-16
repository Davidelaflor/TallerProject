package com.example.taller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {
    private String patente;
    private String marca;
    private String modelo;
}

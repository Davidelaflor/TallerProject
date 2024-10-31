package com.example.taller.vehiculos.domain;

import com.example.taller.propietarios.domain.PropietarioDTO;

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
public class VehiculoDTO {
    private String patente;
    private String marca;
    private String modelo;
    private PropietarioDTO propietario;
}

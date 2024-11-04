package com.example.taller.vehiculos.domain;

import com.example.taller.propietarios.domain.PropietarioDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private PropietarioDTO propietario;
    
    public VehiculoDTO(String patente, String marca, String modelo) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
    }
}

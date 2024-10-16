package com.example.taller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PropietarioVehiculoDTO {
    private PropietarioDTO propietario;
    private VehiculoDTO vehiculo;
}

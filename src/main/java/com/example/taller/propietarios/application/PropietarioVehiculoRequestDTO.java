package com.example.taller.propietarios.application;

import com.example.taller.vehiculos.application.VehiculoRequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO que contiene información de un propietario y su vehículo")

public class PropietarioVehiculoRequestDTO {
    @Schema(description = "Datos del propietario", required = true)

    private PropietarioRequestDTO propietario;
    @Schema(description = "Datos del vehículo", required = true)

    private VehiculoRequestDTO vehiculo;
}

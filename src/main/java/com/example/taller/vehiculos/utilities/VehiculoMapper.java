package com.example.taller.vehiculos.utilities;

import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import com.example.taller.vehiculos.domain.VehiculoDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

public class VehiculoMapper {

    private VehiculoMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static VehiculoDTO toDTO(VehiculoEntity entity) {
    if (entity == null) {
        return null; // Evitar NullPointerException si la entidad es nula
    }
    return new VehiculoDTO(entity.getPatente(), entity.getMarca(), entity.getModelo());
}

 // Método para mapear el DTO de Vehiculo a la Entidad Vehiculo
    public static VehiculoEntity toEntity(VehiculoRequestDTO vehiculoDTO, PropietarioEntity propietario) {
        return VehiculoEntity.builder()
                .patente(vehiculoDTO.getPatente())
                .marca(vehiculoDTO.getMarca())
                .modelo(vehiculoDTO.getModelo())
                .propietario(propietario)
                .build();
    }
}

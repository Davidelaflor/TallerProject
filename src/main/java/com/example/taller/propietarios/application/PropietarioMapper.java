package com.example.taller.propietarios.application;

import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;

public class PropietarioMapper {
  public static PropietarioEntity convertToEntity(PropietarioDTO dto) {
        if (dto == null) {
            return null; // Manejo de nulos
        }
        PropietarioEntity entity = new PropietarioEntity();
        entity.setDni(dto.getDni());
        entity.setNombre(dto.getNombre());
        // Rellena otros campos...
        return entity;
    }

    public static PropietarioDTO convertToDTO(PropietarioEntity entity) {
        if (entity == null) {
            return null; // Manejo de nulos
        }
        PropietarioDTO dto = new PropietarioDTO();
        dto.setDni(entity.getDni());
        dto.setNombre(entity.getNombre());
        // Rellena otros campos...
        return dto;
    }
}

package com.example.taller.propietarios.utilities;

import java.util.List;
import java.util.stream.Collectors;

import com.example.taller.propietarios.application.PropietarioRequestDTO;
import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import com.example.taller.vehiculos.domain.VehiculoDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class PropietarioMapper {
    // Convertir de PropietarioRequestDTO a PropietarioEntity
    public static PropietarioEntity toEntity(PropietarioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        PropietarioEntity entity = new PropietarioEntity();
        entity.setDni(dto.getDni());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setDireccion(dto.getDireccion());
        entity.setTelefono(dto.getTelefono());
        return entity;
    }

    // Convertir de VehiculoRequestDTO a VehiculoEntity
    public static VehiculoEntity toEntity(VehiculoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        VehiculoEntity entity = new VehiculoEntity();
        entity.setPatente(dto.getPatente());
        entity.setMarca(dto.getMarca());
        entity.setModelo(dto.getModelo());
        return entity;
    }

    // Convertir de PropietarioEntity a PropietarioDTO
    public static PropietarioDTO toDTO(PropietarioEntity entity) {
        if (entity == null) {
            return null;
        }
        PropietarioDTO dto = new PropietarioDTO();
        dto.setDni(entity.getDni());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setDireccion(entity.getDireccion());
        dto.setTelefono(entity.getTelefono());

        // Si el propietario tiene vehículos asociados, los convertimos a DTO
        List<VehiculoDTO> vehiculosDTO = entity.getVehiculos().stream()
                .map(vehiculo -> new VehiculoDTO(vehiculo.getPatente(), vehiculo.getMarca(), vehiculo.getModelo()))
                .collect(Collectors.toList());

        dto.setVehiculos(vehiculosDTO);
        return dto;
    }

    // Convertir de VehiculoEntity a VehiculoDTO
    public static VehiculoDTO toDTO(VehiculoEntity entity) {
        if (entity == null) {
            return null;
        }
        VehiculoDTO dto = new VehiculoDTO();
        dto.setPatente(entity.getPatente());
        dto.setMarca(entity.getMarca());
        dto.setModelo(entity.getModelo());
        return dto;
    }
}

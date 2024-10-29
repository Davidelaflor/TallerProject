package com.example.taller.propietarios.domain.dto;

import java.util.List;

import com.example.taller.vehiculos.domain.dto.VehiculoResponseDTO;

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
public class PropietarioResponseDTO {
  private String dni;
    private String nombre;
    private String telefono;
    private List<VehiculoResponseDTO> vehiculos;
}

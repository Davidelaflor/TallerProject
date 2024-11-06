package com.example.taller.propietarios.domain;

import java.util.List;

import com.example.taller.vehiculos.domain.VehiculoDTO;

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
public class PropietarioDTO {
  private String dni;
  private String nombre;
  private String apellido;
  private String direccion;
  private String telefono;
  private List<VehiculoDTO> vehiculos;
}

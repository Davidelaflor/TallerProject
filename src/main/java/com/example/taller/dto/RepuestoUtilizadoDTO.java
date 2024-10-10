package com.example.taller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RepuestoUtilizadoDTO {
    private Long id;
    private RepuestoDTO repuesto;
    private int cantidadUtilizada;
}

package com.example.taller.RepuestoUtilizado.application;

import com.example.taller.repuestos.application.RepuestoRequestDTO;

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
public class RepuestoUtilizadoRequestDTO {
    private Long id;
    private RepuestoRequestDTO repuesto;
    private int cantidadUtilizada;
}

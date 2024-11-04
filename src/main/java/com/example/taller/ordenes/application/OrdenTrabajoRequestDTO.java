package com.example.taller.ordenes.application;

import java.time.LocalDate;
import java.util.List;

import com.example.taller.RepuestoUtilizado.application.RepuestoUtilizadoRequestDTO;
import com.example.taller.ordenes.domain.Estado;
import com.example.taller.propietarios.application.PropietarioRequestDTO;

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

public class OrdenTrabajoRequestDTO {

    private Long id;

    private LocalDate fechaIngreso;
    private String propietarioDni;  
    private String detalleFalla;
    private int horasTrabajadas;
    private Long empleadoId;
    private String vehiculoPatente;
}
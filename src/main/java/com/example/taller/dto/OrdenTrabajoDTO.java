package com.example.taller.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.taller.model.Estado;

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

public class OrdenTrabajoDTO {

    private Long id;

    private String patente;
    private LocalDate fechaIngreso;
    private String propietarioDni;  
    private String marca;
    private String modelo;
    private String detalleFalla;
    private Estado estado; // Activo/Finalizado
    private List<RepuestoDTO> repuestosUtilizados;
    private int horasTrabajadas;
    private PropietarioDTO propietario;
    private Long empleadoId;
}

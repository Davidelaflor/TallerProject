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

    private LocalDate fechaIngreso;
    private String propietarioDni;  
    private String detalleFalla;
    private Estado estado; // Activo/Finalizado
    private List<RepuestoUtilizadoDTO> repuestosUtilizados;
    private int horasTrabajadas;
    private Long empleadoId;
    private String vehiculoPatente;
}

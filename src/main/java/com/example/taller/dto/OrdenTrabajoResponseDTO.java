package com.example.taller.dto;

import java.time.LocalDate;
import java.util.List;
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
public class OrdenTrabajoResponseDTO {
    private Long id;
    private String detalleFalla;
    private int horasTrabajadas;
    private String estado;
    private LocalDate fechaIngreso;
    private EmpleadoResponseDTO empleado;
    private List<RepuestoUtilizadoResponseDTO> repuestosUtilizados;
    private PropietarioResponseDTO propietario;
}

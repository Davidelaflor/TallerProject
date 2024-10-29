package com.example.taller.ordenes.domain.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.taller.empleados.domain.dto.EmpleadoResponseDTO;
import com.example.taller.propietarios.domain.dto.PropietarioResponseDTO;
import com.example.taller.repuestos.domain.dto.RepuestoUtilizadoResponseDTO;

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

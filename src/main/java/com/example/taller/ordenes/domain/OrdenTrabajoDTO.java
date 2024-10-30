package com.example.taller.ordenes.domain;

import java.time.LocalDate;
import java.util.List;

import com.example.taller.RepuestoUtilizado.domain.RepuestoUtilizadoDTO;
import com.example.taller.empleados.domain.EmpleadoDTO;
import com.example.taller.propietarios.domain.PropietarioDTO;

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
public class OrdenTrabajoDTO {
    private Long id;
    private String detalleFalla;
    private int horasTrabajadas;
    private String estado;
    private LocalDate fechaIngreso;
    private EmpleadoDTO empleado;
    private List<RepuestoUtilizadoDTO> repuestosUtilizados;
    private PropietarioDTO propietario;
}

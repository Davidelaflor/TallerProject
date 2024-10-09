package com.example.taller.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.cglib.core.Local;

import com.example.taller.model.Estado;

public class OrdenTrabajoDTO {

    private Long id;
    private String vehiculoPatente;
    private LocalDate fechaIngreso;
    private String propietarioDni;  // Relación Propietario y DNI
    private String detalleFalla;
    private Estado estado; // Activo/Finalizado
    private List<RepuestoDTO> repuestosUtilizados;
    private int horasTrabajadas;
    private PropietarioDTO propietario;
    private EmpleadoDTO empleadoAsignado;

     // Constructor vacío
     public OrdenTrabajoDTO() {}

     // Constructor
     public OrdenTrabajoDTO(Long id, String vehiculoPatente, String detalleFalla, int horasTrabajadas, Estado estado, 
                            LocalDate fechaIngreso, EmpleadoDTO empleadoAsignado, String propietarioDni, List<RepuestoDTO> repuestosUtilizados) {
         this.id = id;
         this.vehiculoPatente = vehiculoPatente;
         this.detalleFalla = detalleFalla;
         this.horasTrabajadas = horasTrabajadas;
         this.estado = estado;
         this.fechaIngreso = fechaIngreso;
         this.empleadoAsignado = empleadoAsignado;
         this.propietarioDni = propietarioDni;
         this.repuestosUtilizados = repuestosUtilizados;
     }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getPropietarioDni() {
        return propietarioDni;
    }

    public void setPropietarioDni(String propietarioDni) {
        this.propietarioDni = propietarioDni;
    }


    public String getDetalleFalla() {
        return detalleFalla;
    }

    public void setDetalleFalla(String detalleFalla) {
        this.detalleFalla = detalleFalla;
    }

    public EmpleadoDTO getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    public void setEmpleadoAsignado(EmpleadoDTO empleadoAsignado) {
        this.empleadoAsignado = empleadoAsignado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<RepuestoDTO> getRepuestosUtilizados() {
        return repuestosUtilizados;
    }

    public void setRepuestosUtilizados(List<RepuestoDTO> repuestosUtilizados) {
        this.repuestosUtilizados = repuestosUtilizados;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public PropietarioDTO getPropietario() {
        return propietario;
    }

    public void setPropietario(PropietarioDTO propietario) {
        this.propietario = propietario;
    }

    public String getVehiculoPatente() { return vehiculoPatente; }
    public void setVehiculoPatente(String vehiculoPatente) { this.vehiculoPatente = vehiculoPatente; }


    @Override
public String toString() {
    return "OrdenTrabajoDTO{" +
            "id=" + id +
            ", fechaIngreso=" + fechaIngreso +
            ", propietarioDni='" + propietarioDni + '\'' +
            ", detalleFalla='" + detalleFalla + '\'' +
            ", estado=" + estado +
            ", horasTrabajadas=" + horasTrabajadas +
            ", propietario=" + propietario +
            ", empleado=" + empleadoAsignado +
            ", repuestosUtilizados=" + repuestosUtilizados + 
            '}';
}
}

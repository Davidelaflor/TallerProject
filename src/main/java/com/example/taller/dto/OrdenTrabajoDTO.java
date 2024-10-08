package com.example.taller.dto;

import java.util.Date;
import java.util.List;

import com.example.taller.model.Estado;

public class OrdenTrabajoDTO {

    private Long id;
    private String patente;
    private Date fechaIngreso;
    private String propietarioDni;  // Relación Propietario y DNI
    private String marca;
    private String modelo;
    private String detalleFalla;
    private Estado estado; // Activo/Finalizado
    private List<RepuestoDTO> repuestosUtilizados;
    private int horasTrabajadas;
    private PropietarioDTO propietario;
    private EmpleadoDTO empleado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getPropietarioDni() {
        return propietarioDni;
    }

    public void setPropietarioDni(String propietarioDni) {
        this.propietarioDni = propietarioDni;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDetalleFalla() {
        return detalleFalla;
    }

    public void setDetalleFalla(String detalleFalla) {
        this.detalleFalla = detalleFalla;
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
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

    @Override
public String toString() {
    return "OrdenTrabajoDTO{" +
            "id=" + id +
            ", patente='" + patente + '\'' +
            ", fechaIngreso=" + fechaIngreso +
            ", propietarioDni='" + propietarioDni + '\'' +
            ", marca='" + marca + '\'' +
            ", modelo='" + modelo + '\'' +
            ", detalleFalla='" + detalleFalla + '\'' +
            ", estado=" + estado +
            ", repuestosUtilizados=" + repuestosUtilizados +
            ", horasTrabajadas=" + horasTrabajadas +
            ", propietario=" + propietario +
            ", empleado=" + empleado +
            '}';
}
}

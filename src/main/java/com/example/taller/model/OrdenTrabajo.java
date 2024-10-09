package com.example.taller.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.core.Local;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;




@Entity
@Table(name = "ordenes_trabajo")
public class OrdenTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_patente")
    private Vehiculo vehiculo;

    /*private String patente;
    private String marca;
    private String modelo;*/
    private String detalleFalla;

    @ManyToOne
    @JoinColumn(name = "empleado_id") // FK que hace referencia a la tabla de empleados
    private Empleado empleadoAsignado;

    @Enumerated(EnumType.STRING)
    private Estado estado;


    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

     @ManyToOne // Muchas órdenes de trabajo pueden estar asociadas a un mismo propietario
    @JoinColumn(name = "propietario_dni") // Define la FK en la tabla 'orden_trabajo'
    private Propietario propietario;
    
    @OneToMany(mappedBy = "ordenTrabajo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RepuestoUtilizado> repuestosUtilizados = new ArrayList<>();
    
    private int horasTrabajadas;

    public OrdenTrabajo() {}

    public OrdenTrabajo(Vehiculo vehiculo, String detalleFalla, int horasTrabajadas, Estado estado, LocalDate fechaIngreso, Empleado empleadoAsignado, Propietario propietario, List<RepuestoUtilizado> repuestosUtilizados) {
        this.vehiculo = vehiculo;
        this.detalleFalla = detalleFalla;
        this.horasTrabajadas = horasTrabajadas;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.empleadoAsignado = empleadoAsignado;
        this.propietario = propietario;
        this.repuestosUtilizados = repuestosUtilizados;
    }

     // Métodos para manejar la lista de repuestos utilizados
     public void addRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado) {
        repuestosUtilizados.add(repuestoUtilizado);
        repuestoUtilizado.setOrdenTrabajo(this); // Establece la relación bidireccional
    }


    public void removeRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado) {
        repuestosUtilizados.remove(repuestoUtilizado);
        repuestoUtilizado.setOrdenTrabajo(null); // Elimina la relación bidireccional
    }
    
    public Empleado getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    public void setEmpleadoAsignado(Empleado empleadoAsignado) {
        this.empleadoAsignado = empleadoAsignado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   /*  public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }*/

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    /* public String getMarca() {
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
    }*/
    
    public String getDetalleFalla() {
        return detalleFalla;
    }

    public void setDetalleFalla(String detalleFalla) {
        this.detalleFalla = detalleFalla;
    }


    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) { 
        this.estado = estado;
    }

    public List<RepuestoUtilizado> getRepuestosUtilizados() {
        return repuestosUtilizados;
    }

    public void setRepuestosUtilizados(List<RepuestoUtilizado> repuestosUtilizados) {
        this.repuestosUtilizados = repuestosUtilizados;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}

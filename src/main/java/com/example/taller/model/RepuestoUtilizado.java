package com.example.taller.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "repuestos_utilizados")
public class RepuestoUtilizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_trabajo_id", nullable = false)
    private OrdenTrabajo ordenTrabajo;


   @ManyToOne
    @JoinColumn(name = "repuesto_codigo", nullable = false)
    private Repuesto repuesto; // Referencia a Repuesto

    private Integer cantidad; // Cantidad del repuesto utilizado

        // Constructor por defecto
        public RepuestoUtilizado() {
        }
    
         // Constructor
    public RepuestoUtilizado(Long id, OrdenTrabajo ordenTrabajo, Repuesto repuesto, Integer cantidad) {
        this.id = id;
        this.ordenTrabajo = ordenTrabajo;
        this.repuesto = repuesto;
        this.cantidad = cantidad;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Repuesto getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(Repuesto repuesto) {
        this.repuesto = repuesto;
    }

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}

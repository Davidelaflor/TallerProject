package com.example.taller.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "repuestos_utilizados")
public class RepuestoUtilizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "orden_trabajo_id", nullable = false)
    private OrdenTrabajo ordenTrabajo;


   @ManyToOne
    @JoinColumn(name = "repuesto_codigo", nullable = false)
    private Repuesto repuesto; // Referencia a Repuesto

    @NotNull(message = "La cantidad utilizada no puede ser nula")
    @Builder.Default
    private int cantidad = 1; // Cantidad del repuesto utilizado

        // Constructor por defecto
        public RepuestoUtilizado() {
        }
    
    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}

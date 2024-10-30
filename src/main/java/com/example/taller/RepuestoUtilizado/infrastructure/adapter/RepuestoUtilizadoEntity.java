package com.example.taller.RepuestoUtilizado.infrastructure.adapter;


import com.example.taller.ordenes.infrastructure.adapter.OrdenTrabajoEntity;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class RepuestoUtilizadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orden_trabajo_id", nullable = false)
    private OrdenTrabajoEntity ordenTrabajo;


    @JsonIgnore
   @ManyToOne
    @JoinColumn(name = "repuesto_codigo", nullable = false)
    private RepuestoEntity repuesto; // Referencia a Repuesto

    @NotNull(message = "La cantidad utilizada no puede ser nula")
    @Builder.Default
    private int cantidad = 1; // Cantidad del repuesto utilizado

        // Constructor por defecto
        public RepuestoUtilizadoEntity() {
        }
    
    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RepuestoEntity getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(RepuestoEntity repuesto) {
        this.repuesto = repuesto;
    }

    public OrdenTrabajoEntity getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajoEntity ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}

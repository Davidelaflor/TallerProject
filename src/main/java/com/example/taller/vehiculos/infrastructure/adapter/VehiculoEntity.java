package com.example.taller.vehiculos.infrastructure.adapter;

import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "vehiculos")
public class VehiculoEntity {

    @Id
    @Column(name = "patente")

    private String patente; // La patente como clave primaria
    @Column(name = "marca")

    private String marca;
    @Column(name = "modelo")

    private String modelo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "propietario_dni", nullable = false) // Clave foránea
    private PropietarioEntity propietario; // Relación inversa
    public VehiculoEntity(String patente, String marca, String modelo) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
    }
}

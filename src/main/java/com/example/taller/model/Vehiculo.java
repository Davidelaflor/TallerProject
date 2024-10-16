package com.example.taller.model;

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

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "vehiculos")
public class Vehiculo {

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
    private Propietario propietario; // Relación inversa
}

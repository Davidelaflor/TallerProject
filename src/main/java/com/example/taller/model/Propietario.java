package com.example.taller.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "propietarios")
public class Propietario {
    @Id
    @Column(name = "dni")
    private String dni;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(name = "apellido")
    private String apellido;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Column(name = "direccion")
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Column(name = "telefono")
    private String telefono;

    @Builder.Default
    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Vehiculo> vehiculos = new ArrayList<>(); 
    
    public void addVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
        vehiculo.setPropietario(this); // Establece la relación bidireccional
    }
    
}

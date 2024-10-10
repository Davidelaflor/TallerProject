package com.example.taller.model;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "empleados")
@NoArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
}

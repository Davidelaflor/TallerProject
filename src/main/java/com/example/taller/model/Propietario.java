package com.example.taller.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Entity
@Builder
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

  // Constructor por defecto
  public Propietario() {
    // Inicializaciones si son necesarias
}

public Propietario(String dni, String nombre, String apellido, String telefono, String direccion) {
    this.dni = dni;
    this.nombre = nombre;
    this.apellido = apellido;
    this.telefono = telefono;
    this.direccion = direccion;
}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getApellido() { // Getter para 'apellido'
        return apellido;
    }

    public void setApellido(String apellido) { // Setter para 'apellido'
        this.apellido = apellido;
    }

}

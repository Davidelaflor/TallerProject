package com.example.taller.model;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

import java.util.List;


@Entity
@Table(name = "propietarios") 
public class Propietario {
    @Id
    @Column(name = "dni")
    private String dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

@OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos = new ArrayList<>();


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

public Propietario(String dni) {
    this.dni = dni;
}


/*String dni = "12345678A"; // El DNI que deseas verificar
Propietario propietario = new Propietario(dni);

Aquí puedes consultar en la base de datos usando el DNI para encontrar al propietario */

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

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

}

package com.example.taller.dto;

import java.util.List;

public class PropietarioDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private List<VehiculoDTO> vehiculos;

    // Constructor por defecto
    public PropietarioDTO() {}

    // Constructor parametrizado
    public PropietarioDTO(String dni, String nombre, String apellido, String direccion, String telefono, List<VehiculoDTO> vehiculos) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido; // Inicializa el apellido
        this.direccion = direccion;
        this.telefono = telefono;
        this.vehiculos = vehiculos;

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public List<VehiculoDTO> getVehiculos() { return vehiculos; }
    public void setVehiculos(List<VehiculoDTO> vehiculos) { this.vehiculos = vehiculos; }

    @Override
public String toString() {
    return "PropietarioDTO{" +
            "dni='" + dni + '\'' +
            ", nombre='" + nombre + '\'' +
            ", apellido='" + apellido + '\'' +
            ", direccion='" + direccion + '\'' +
            ", telefono='" + telefono + '\'' +
            '}';
}
}

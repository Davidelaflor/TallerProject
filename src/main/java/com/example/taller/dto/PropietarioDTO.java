package com.example.taller.dto;
public class PropietarioDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;


    public PropietarioDTO() {}

    public PropietarioDTO(String dni, String nombre, String apellido, String direccion, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido; // Inicializa el apellido
        this.direccion = direccion;
        this.telefono = telefono;
      

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

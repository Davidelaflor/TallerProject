package com.example.taller.empleados.application;

public class EmpleadoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
  public EmpleadoDTO() {}
  public EmpleadoDTO(Long id, String nombre, String apellido, String telefono) {
      this.id = id; // Inicializa el id
      this.nombre = nombre;
      this.apellido = apellido;
      this.telefono = telefono;
  }
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "EmpleadoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}


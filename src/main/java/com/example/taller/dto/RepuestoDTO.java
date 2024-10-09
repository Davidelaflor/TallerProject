package com.example.taller.dto;

public class RepuestoDTO {
    
    private String codigoInventario;
    private String nombre;
    private double precio;
    private Integer cantidad;

  // Constructor vacío
  public RepuestoDTO() {

  }

      // Constructor
      public RepuestoDTO(String codigoInventario, String nombre, int cantidad, double precio) {
        this.codigoInventario = codigoInventario;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public String getCodigoInventario() {
        return codigoInventario;
    }

    public void setCodigoInventario(String codigoInventario) {
        this.codigoInventario = codigoInventario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    @Override
    public String toString() {
        return "RepuestoDTO{" +
                "codigoInventario='" + codigoInventario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}

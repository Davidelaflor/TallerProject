package com.example.taller.model;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "repuestos") 
public class Repuesto {

    @Id
    @Column(name = "codigo_inventario")
    private String codigoInventario;
    private String nombre;
    private double precio;
    private Integer cantidad;

 
    public Repuesto() {
    }

 // Constructor
 public Repuesto(String codigoInventario, String nombre, Integer cantidad, Double precio) {
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

}

package com.example.taller.model;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
@AllArgsConstructor
@Entity
@Table(name = "repuestos") 
public class Repuesto {

    @Id
    @NotBlank(message = "El código de inventario no puede estar vacío")
    @Column(name = "codigo_inventario")
    private String codigoInventario;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El precio no puede ser nulo")
    private double precio;

    @NotNull(message = "La cantidad no puede ser nula")
    private int cantidad;

 
    public Repuesto() {
    }


public int getCantidad() {
    return cantidad;
}

public void setCantidad(int cantidad) {
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

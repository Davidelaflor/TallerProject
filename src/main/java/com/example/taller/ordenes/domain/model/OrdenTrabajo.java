package com.example.taller.ordenes.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordenes_trabajo")
public class OrdenTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String detalleFalla;
    private int horasTrabajadas;
    private String estado;
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;
    @ManyToOne
    @JoinColumn(name = "empleado_id") 
    private Empleado empleado;
    @ManyToOne 
    @JoinColumn(name = "propietario_dni")
    private Propietario propietario;
    @OneToMany(mappedBy = "ordenTrabajo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RepuestoUtilizado> repuestoUtilizado = new ArrayList<>();
    
     // Métodos para agregar y eliminar repuestos utilizados
     public void addRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado) {
        this.repuestoUtilizado.add(repuestoUtilizado); // Añade el repuesto a la lista
        repuestoUtilizado.setOrdenTrabajo(this); // Configura la relación bidireccional
    }

    public void removeRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado) {
        this.repuestoUtilizado.remove(repuestoUtilizado); // Elimina el repuesto de la lista
        repuestoUtilizado.setOrdenTrabajo(null); // Limpia la relación bidireccional
    }
    @ManyToOne
    @JoinColumn(name = "vehiculo_patente")
    private Vehiculo vehiculo;
}

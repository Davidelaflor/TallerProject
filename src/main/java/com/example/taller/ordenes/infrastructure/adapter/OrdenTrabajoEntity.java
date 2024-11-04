package com.example.taller.ordenes.infrastructure.adapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.taller.RepuestoUtilizado.infrastructure.adapter.RepuestoUtilizadoEntity;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

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
public class OrdenTrabajoEntity {

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
    private EmpleadoEntity empleado;
    @ManyToOne 
    @JoinColumn(name = "propietario_dni")
    private PropietarioEntity propietario;
    @OneToMany(mappedBy = "ordenTrabajo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RepuestoUtilizadoEntity> repuestosUtilizados = new ArrayList<>();
    
     // Métodos para agregar y eliminar repuestos utilizados
     public void addRepuestoUtilizado(RepuestoUtilizadoEntity repuestoUtilizado) {
        this.repuestosUtilizados.add(repuestoUtilizado); // Añade el repuesto a la lista
        repuestoUtilizado.setOrdenTrabajo(this); // Configura la relación bidireccional
    }

    public void removeRepuestoUtilizado(RepuestoUtilizadoEntity repuestoUtilizado) {
        this.repuestosUtilizados.remove(repuestoUtilizado); // Elimina el repuesto de la lista
        repuestoUtilizado.setOrdenTrabajo(null); // Limpia la relación bidireccional
    }
    @ManyToOne
    @JoinColumn(name = "vehiculo_patente", nullable = false)
    private VehiculoEntity vehiculo;
}

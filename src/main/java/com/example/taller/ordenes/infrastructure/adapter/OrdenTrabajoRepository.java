package com.example.taller.ordenes.infrastructure.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajoEntity, Long> { 
    boolean existsByVehiculo_Patente(String patente); // Este método verifica si el vehículo ya está en una orden de trabajo

}

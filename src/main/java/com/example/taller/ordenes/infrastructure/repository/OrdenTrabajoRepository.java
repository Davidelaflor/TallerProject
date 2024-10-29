package com.example.taller.ordenes.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taller.ordenes.domain.model.OrdenTrabajo;
import com.example.taller.vehiculos.domain.model.Vehiculo;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo, Long> { 
    boolean existsByVehiculo(Vehiculo vehiculo); // Este método verifica si el vehículo ya está en una orden de trabajo

}

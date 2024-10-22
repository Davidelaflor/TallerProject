package com.example.taller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.taller.model.OrdenTrabajo;
import com.example.taller.model.Vehiculo;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo, Long> { 
    boolean existsByVehiculo(Vehiculo vehiculo); // Este método verifica si el vehículo ya está en una orden de trabajo

}

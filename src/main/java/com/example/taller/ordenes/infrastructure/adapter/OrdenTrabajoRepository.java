package com.example.taller.ordenes.infrastructure.adapter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajoEntity, Long> { 
    boolean existsByVehiculo_Patente(String patente); 
    List<OrdenTrabajoEntity> findByPropietarioDniAndEstado(String dni, String estado);
    List<OrdenTrabajoEntity> findByEstado(String estado); 
}

package com.example.taller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.taller.model.OrdenTrabajo;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo, Long> { 
    boolean existsByPatente(String patente);

}

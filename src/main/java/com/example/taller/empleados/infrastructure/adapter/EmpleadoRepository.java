package com.example.taller.empleados.infrastructure.adapter;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
        //Optional<EmpleadoEntity> findById(Long id); no es necesario porque jparepo ua hace esto por defecto al parecer

}



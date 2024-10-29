package com.example.taller.repuestos.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taller.repuestos.domain.model.Repuesto;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, String> {
}

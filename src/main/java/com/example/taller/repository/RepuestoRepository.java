package com.example.taller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taller.model.Repuesto;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, String> {
}

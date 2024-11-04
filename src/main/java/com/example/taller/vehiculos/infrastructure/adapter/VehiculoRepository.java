package com.example.taller.vehiculos.infrastructure.adapter;

import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, String> {
    List<VehiculoEntity> findByPropietarioDni(String dni);
    boolean existsByPatente(String patente); // Verificar si el vehículo ya existe por patente
    Optional<VehiculoEntity> findByPatente(String patente);

}

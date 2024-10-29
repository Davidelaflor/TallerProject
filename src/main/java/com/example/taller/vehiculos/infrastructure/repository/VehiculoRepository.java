package com.example.taller.vehiculos.infrastructure.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taller.vehiculos.domain.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    List<Vehiculo> findByPropietarioDni(String dni);
    boolean existsByPatente(String patente); // Verificar si el vehículo ya existe por patente

}

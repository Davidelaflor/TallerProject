package com.example.taller.propietarios.infrastructure.adapter;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepository extends JpaRepository<PropietarioEntity, String> {
    Optional<PropietarioEntity> findByDni(String dni);
    boolean existsByDni(String dni); // Verificar si el propietario ya existe por DNI

}

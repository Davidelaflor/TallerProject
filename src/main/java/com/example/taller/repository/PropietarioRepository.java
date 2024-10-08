package com.example.taller.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taller.model.Propietario;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, String> {
    Optional<Propietario> findByDni(String dni);

}

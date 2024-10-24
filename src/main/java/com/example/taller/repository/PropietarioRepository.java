package com.example.taller.repository;




import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taller.model.Propietario;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, String> {
    Propietario findByDni(String dni);
    boolean existsByDni(String dni); // Verificar si el propietario ya existe por DNI

}

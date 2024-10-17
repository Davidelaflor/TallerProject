package com.example.taller.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taller.model.Propietario;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, String> {
    Propietario findByDni(String dni);

}

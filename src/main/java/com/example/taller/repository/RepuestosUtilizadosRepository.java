package com.example.taller.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.taller.model.RepuestoUtilizado; // Asegúrate de que esta importación esté presente


@Repository
@ComponentScan
public interface RepuestosUtilizadosRepository extends JpaRepository<RepuestoUtilizado, Long> {

}

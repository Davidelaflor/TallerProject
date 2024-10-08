package com.example.taller.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.taller.model.Estado;


import com.example.taller.model.OrdenTrabajo;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo, Long> { //aqui tenia que poner el nombre de la tabla de la base de datos??
    List<OrdenTrabajo> findByEstado(Estado estado); // Ejemplo de consulta personalizada
    Optional<OrdenTrabajo> findByPatente(String patente);

 }

package com.example.taller.repuestos.infrastructure.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taller.repuestos.domain.model.RepuestoUtilizado;


@Repository
@ComponentScan
public interface RepuestoUtilizadoRepository extends JpaRepository<RepuestoUtilizado, String> {

}

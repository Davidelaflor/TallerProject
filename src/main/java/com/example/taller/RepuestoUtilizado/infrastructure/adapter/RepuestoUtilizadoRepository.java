package com.example.taller.RepuestoUtilizado.infrastructure.adapter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@ComponentScan
public interface RepuestoUtilizadoRepository extends JpaRepository<RepuestoUtilizadoEntity, String> {

}

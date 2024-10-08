package com.example.taller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.taller.model.Repuesto;

@Repository
public interface RepuestoRepository  extends JpaRepository<Repuesto, String> {
    Repuesto findByCodigoInventario(String codigoInventario); // Método para buscar un repuesto por código
      @Query("SELECT r FROM Repuesto r WHERE r.codigoInventario IN :codigos")
    List<Repuesto> findByCodigoInventarioIn(@Param("codigos") List<String> codigos);
}

package com.example.taller.repuestos.infrastructure.port;

import java.util.List;
import java.util.Optional;

import com.example.taller.repuestos.domain.RepuestoDTO;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;

public interface RepuestoServicePort {

    List<RepuestoDTO> listarRepuestos();

    Optional<RepuestoEntity> findByCodigoInventario(String codigoInventario);

    void save(RepuestoEntity repuesto);

}

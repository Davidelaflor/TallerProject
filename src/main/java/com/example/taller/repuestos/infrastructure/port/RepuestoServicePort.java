package com.example.taller.repuestos.infrastructure.port;


import java.util.List;

import com.example.taller.repuestos.domain.RepuestoDTO;

public interface RepuestoServicePort {
    RepuestoDTO crearRepuesto(RepuestoDTO repuestoDTO);

    RepuestoDTO obtenerRepuestoPorCodigo(String codigo);

    List<RepuestoDTO> listarRepuestos();

    void eliminarRepuesto(String codigo);
}

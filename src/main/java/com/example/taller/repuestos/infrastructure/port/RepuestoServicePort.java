package com.example.taller.repuestos.infrastructure.port;


import java.util.List;

import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;

public interface RepuestoServicePort {
    RepuestoEntity crearRepuesto(RepuestoEntity repuesto);

    RepuestoEntity obtenerRepuestoPorCodigo(Long codigo);

    List<RepuestoEntity> listarRepuestos();

    RepuestoEntity buscarRepuestoPorCodigo(String codigoInventario);

    void eliminarRepuesto(Long codigo);
}

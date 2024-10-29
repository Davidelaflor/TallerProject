package com.example.taller.repuestos.infrastructure.port;


import java.util.List;

import com.example.taller.propietarios.domain.model.Propietario;
import com.example.taller.repuestos.domain.model.Repuesto;

public interface RepuestoServicePort {
    Repuesto crearRepuesto(Repuesto repuesto);

    Repuesto obtenerRepuestoPorCodigo(Long codigo);

    void agregarRepuestoAOrden(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad);

    List<Repuesto> listarRepuestos();

    Repuesto buscarRepuestoPorCodigo(String codigoInventario);

    void eliminarRepuesto(Long codigo);
}

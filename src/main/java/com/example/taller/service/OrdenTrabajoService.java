package com.example.taller.service;

import java.util.List;
import com.example.taller.model.OrdenTrabajo;

import com.example.taller.dto.OrdenTrabajoDTO;

public interface OrdenTrabajoService {
  OrdenTrabajo crearOrdenTrabajo(OrdenTrabajoDTO ordenTrabajoDto);//, Long empleadoCodigo, String propietarioDni, List<String> codigosRepuestos, List<Integer> cantidades); 
  OrdenTrabajo modificarOrdenTrabajo(Long id, OrdenTrabajoDTO ordenTrabajoDto);
  List<OrdenTrabajo> listarOrdenes();
    OrdenTrabajo obtenerOrdenPorId(Long id);
    void eliminarOrdenTrabajo(Long id);
    boolean existeOrdenPorPatente(String patente);
    boolean existeOrdenPorId(Long id);

}

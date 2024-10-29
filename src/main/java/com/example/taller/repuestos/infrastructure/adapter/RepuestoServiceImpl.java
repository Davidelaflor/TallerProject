package com.example.taller.repuestos.infrastructure.adapter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.ordenes.infrastructure.repository.OrdenTrabajoRepository;
import com.example.taller.repuestos.domain.model.Repuesto;
import com.example.taller.repuestos.infrastructure.port.RepuestoServicePort;
import com.example.taller.repuestos.infrastructure.repository.RepuestoRepository;

@Service
public class RepuestoServiceImpl implements RepuestoServicePort {
    @Autowired
    private RepuestoRepository repuestoRepository;
  @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;
@Override
    public List<Repuesto> listarRepuestos() {
        return repuestoRepository.findAll();
    }

    @Override
    public Repuesto crearRepuesto(Repuesto repuesto) {
        return repuestoRepository.save(repuesto);
    }

    @Override
    public Repuesto obtenerRepuestoPorCodigo(Long codigo) {
        return null;//repuestoRepository.findById(codigo)
                //.orElseThrow(() -> new RuntimeException("Repuesto no encontrado"));
    }


    @Override
    public Repuesto buscarRepuestoPorCodigo(String codigoInventario) {
        return repuestoRepository.findById(codigoInventario)
                .orElse(null);
    }

    @Override
    public void eliminarRepuesto(Long codigo) {
       // repuestoRepository.deleteById(codigo);
    }
    @Override
    public void agregarRepuestoAOrden(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad) {
        // Obtener la orden de trabajo
        OrdenTrabajo ordenTrabajo = ordenTrabajoRepository.findById(ordenTrabajoId)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

        // Obtener el repuesto
        Repuesto repuesto = repuestoRepository.findById(repuestoUtilizadoId)
                .orElseThrow(() -> new RuntimeException("Repuesto no encontrado"));

        // Crear el objeto RepuestoUtilizado y establecer sus propiedades
        RepuestoUtilizado repuestoUtilizado = new RepuestoUtilizado(repuesto, cantidad);

        // Agregar el repuesto a la orden de trabajo y guardar
        ordenTrabajo.addRepuestoUtilizado(repuestoUtilizado);
        ordenTrabajoRepository.save(ordenTrabajo);
    }
}

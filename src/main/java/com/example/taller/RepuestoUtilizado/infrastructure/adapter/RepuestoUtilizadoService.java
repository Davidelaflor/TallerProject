package com.example.taller.RepuestoUtilizado.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.taller.RepuestoUtilizado.infrastructure.port.RepuestoUtilizadoServicePort;

public class RepuestoUtilizadoService implements RepuestoUtilizadoServicePort {
     @Autowired
    private RepuestoUtilizadoRepository repuestoUtilizadoRepository;

    @Override
    public RepuestoUtilizadoEntity crearRepuestoUtilizado(RepuestoUtilizadoEntity repuestoUtilizado) {
        // Se pueden incluir validaciones o lógica adicional aquí
        return repuestoUtilizadoRepository.save(repuestoUtilizado);
    }  

    @Override
    public RepuestoUtilizadoEntity obtenerRepuestoUtilizadoPorId(String id) {
        return repuestoUtilizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Repuesto Utilizado no encontrado"));
    }

    @Override
    public void eliminarRepuestoUtilizado(String id) {
        repuestoUtilizadoRepository.deleteById(id);
    }

}

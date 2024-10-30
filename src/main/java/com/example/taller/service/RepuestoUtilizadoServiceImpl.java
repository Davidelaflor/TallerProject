package com.example.taller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.RepuestoUtilizado.infrastructure.adapter.RepuestoUtilizadoEntity;
import com.example.taller.RepuestoUtilizado.infrastructure.adapter.RepuestoUtilizadoRepository;
import com.example.taller.RepuestoUtilizado.infrastructure.port.RepuestoUtilizadoServicePort;

@Service
public class RepuestoUtilizadoServiceImpl implements RepuestoUtilizadoServicePort {
  @Autowired
    private RepuestoUtilizadoRepository repuestoUtilizadoRepository;

    @Override
    public RepuestoUtilizadoEntity crearRepuestoUtilizado(RepuestoUtilizadoEntity repuestoUtilizado) {
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

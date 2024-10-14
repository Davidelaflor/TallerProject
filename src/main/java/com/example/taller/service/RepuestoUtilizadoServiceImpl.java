package com.example.taller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.model.RepuestoUtilizado;
import com.example.taller.repository.RepuestoUtilizadoRepository;

@Service
public class RepuestoUtilizadoServiceImpl implements RepuestoUtilizadoService {
  @Autowired
    private RepuestoUtilizadoRepository repuestoUtilizadoRepository;

    @Override
    public RepuestoUtilizado crearRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado) {
        return repuestoUtilizadoRepository.save(repuestoUtilizado);
    }

    @Override
    public RepuestoUtilizado obtenerRepuestoUtilizadoPorId(Long id) {
        return repuestoUtilizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Repuesto Utilizado no encontrado"));
    }

    @Override
    public void eliminarRepuestoUtilizado(Long id) {
        repuestoUtilizadoRepository.deleteById(id);
    }
}

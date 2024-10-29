package com.example.taller.repuestos.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.repuestos.domain.model.RepuestoUtilizado;
import com.example.taller.repuestos.infrastructure.port.RepuestoUtilizadoService;
import com.example.taller.repuestos.infrastructure.repository.RepuestoUtilizadoRepository;

@Service
public class RepuestoUtilizadoServiceImpl implements RepuestoUtilizadoService {
  @Autowired
    private RepuestoUtilizadoRepository repuestoUtilizadoRepository;

    @Override
    public RepuestoUtilizado crearRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado) {
        return repuestoUtilizadoRepository.save(repuestoUtilizado);
    }

    @Override
    public RepuestoUtilizado obtenerRepuestoUtilizadoPorId(String id) {
        return repuestoUtilizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Repuesto Utilizado no encontrado"));
    }

    @Override
    public void eliminarRepuestoUtilizado(String id) {
        repuestoUtilizadoRepository.deleteById(id);
    }
}

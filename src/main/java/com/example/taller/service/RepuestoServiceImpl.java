package com.example.taller.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoRepository;
import com.example.taller.repuestos.infrastructure.port.RepuestoServicePort;

@Service
public class RepuestoServiceImpl implements RepuestoServicePort {
    @Autowired
    private RepuestoRepository repuestoRepository;

@Override
    public List<RepuestoEntity> listarRepuestos() {
        return repuestoRepository.findAll();
    }

    @Override
    public RepuestoEntity crearRepuesto(RepuestoEntity repuesto) {
        return repuestoRepository.save(repuesto);
    }

    @Override
    public RepuestoEntity obtenerRepuestoPorCodigo(Long codigo) {
        return null;//repuestoRepository.findById(codigo)
                //.orElseThrow(() -> new RuntimeException("Repuesto no encontrado"));
    }


    @Override
    public RepuestoEntity buscarRepuestoPorCodigo(String codigoInventario) {
        return repuestoRepository.findById(codigoInventario)
                .orElse(null);
    }

    @Override
    public void eliminarRepuesto(Long codigo) {
       // repuestoRepository.deleteById(codigo);
    }
}

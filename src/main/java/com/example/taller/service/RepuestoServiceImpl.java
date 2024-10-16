package com.example.taller.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.model.Repuesto;
import com.example.taller.repository.RepuestoRepository;

@Service
public class RepuestoServiceImpl implements RepuestoService {
    @Autowired
    private RepuestoRepository repuestoRepository;

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
}
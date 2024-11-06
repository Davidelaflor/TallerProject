package com.example.taller.repuestos.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.repuestos.domain.RepuestoDTO;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;
import com.example.taller.repuestos.infrastructure.port.RepuestoServicePort;

@Service
public class RepuestoApplicationService {
 @Autowired
    private RepuestoServicePort repuestoService;

    // Método para listar todos los repuestos
    public List<RepuestoDTO> listarRepuestos() {
        return repuestoService.listarRepuestos();
    }
}

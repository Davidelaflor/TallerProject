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

    // Método para crear un repuesto con validación
    public RepuestoDTO crearRepuesto(RepuestoDTO repuesto) {
        if (repuesto.getCodigoInventario() == null || repuesto.getCodigoInventario().isEmpty()) {
            throw new IllegalArgumentException("El código de inventario no puede ser nulo o vacío");
        }
        return repuestoService.crearRepuesto(repuesto);
    }

    // Método para obtener un repuesto por código con validación de existencia
    public RepuestoDTO obtenerRepuestoPorCodigo(String codigo) {
        RepuestoDTO repuesto = repuestoService.obtenerRepuestoPorCodigo(codigo);
        if (repuesto == null) {
            throw new RuntimeException("Repuesto no encontrado");
        }
        return repuesto;
    }

   
    // Método para eliminar un repuesto con validación de existencia
    public void eliminarRepuesto(String codigo) {
        RepuestoDTO repuesto = obtenerRepuestoPorCodigo(codigo); // Llama al método de validación
        repuestoService.eliminarRepuesto(codigo);
    }
}

package com.example.taller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.model.Empleado;
import com.example.taller.repository.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado crearEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    @Override
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
}

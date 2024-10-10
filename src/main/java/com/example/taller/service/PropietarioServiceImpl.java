package com.example.taller.service;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.dto.PropietarioDTO;
import com.example.taller.model.Propietario;
import com.example.taller.repository.PropietarioRepository;


@Service
public class PropietarioServiceImpl implements PropietarioService {
    @Autowired
    private PropietarioRepository propietarioRepository;

      @Override
    public List<Propietario> listarPropietarios() {
        return propietarioRepository.findAll();
    }
  
    @Override
    public Propietario crearPropietario(PropietarioDTO dto) {
        Propietario entity = generatePropietarioEntity(dto);
        return propietarioRepository.save(entity);
    }

    private Propietario generatePropietarioEntity(PropietarioDTO dto) {
        return Propietario.builder()
                .dni(dto.getDni())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .build();
    }

    @Override
    public Propietario obtenerPropietarioPorDni(String dni) {
        return propietarioRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));
    }

    @Override
    public void eliminarPropietario(String dni) {
        propietarioRepository.deleteById(dni);
    }

}

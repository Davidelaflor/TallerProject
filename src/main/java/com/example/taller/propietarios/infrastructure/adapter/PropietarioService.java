package com.example.taller.propietarios.infrastructure.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.propietarios.application.PropietarioRequestDTO;
import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.propietarios.utilities.PropietarioMapper;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoRepository;
import com.example.taller.vehiculos.infrastructure.port.VehiculoServicePort;

@Service
public class PropietarioService implements PropietarioServicePort{

    @Autowired
    private PropietarioRepository propietarioRepository;

        @Override
    public List<PropietarioDTO> listarPropietarios() {
        return propietarioRepository.findAll()
                .stream()
                .map(PropietarioMapper::toDTO)
                .collect(Collectors.toList());
    }
     @Override
    public PropietarioDTO crearPropietarioConVehiculo(PropietarioRequestDTO propietarioDTO, VehiculoRequestDTO vehiculoDTO) {
        PropietarioEntity entity = PropietarioMapper.toEntity(propietarioDTO);

        VehiculoEntity vehiculoEntity = PropietarioMapper.toEntity(vehiculoDTO);

        entity.addVehiculo(vehiculoEntity);

        PropietarioEntity savedEntity = propietarioRepository.save(entity);

        return PropietarioMapper.toDTO(savedEntity);
    }

    @Override
    public PropietarioDTO obtenerPropietarioPorDni(String dni) {
        PropietarioEntity entity = propietarioRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));
        return PropietarioMapper.toDTO(entity); // Usando la clase de utilidad
    }
    @Override
    public void eliminarPropietario(String dni) {
        propietarioRepository.deleteById(dni);
    }

  
    /*  @Override
   public PropietarioDTO guardarPropietario(PropietarioDTO propietarioDTO) {
        PropietarioEntity entity = PropietarioMapper.toEntity(propietarioDTO); // Convierte a Entity
        PropietarioEntity savedEntity = propietarioRepository.save(entity);
        return PropietarioMapper.toDTO(savedEntity); // Usando la clase de utilidad
    }*/

        @Override
    public Optional<PropietarioEntity> findByDni(String dni) {
        return propietarioRepository.findByDni(dni);
    }
    @Override
    public boolean existsByDni(String dni) {
        return propietarioRepository.existsByDni(dni);
    }
    @Override
    public PropietarioEntity save(PropietarioEntity propietario) {
        return propietarioRepository.save(propietario);
    }
    @Override
    public Optional<PropietarioEntity> findById(String dni) {
        return propietarioRepository.findById(dni);
    }
}

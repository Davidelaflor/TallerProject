package com.example.taller.propietarios.infrastructure.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.propietarios.application.PropietarioMapper;
import com.example.taller.propietarios.application.PropietarioRequestDTO;
import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoRepository;

@Service
public class PropietarioService implements PropietarioServicePort{

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;
        @Override
    public List<PropietarioDTO> listarPropietarios() {
        return propietarioRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
     @Override
    public PropietarioDTO crearPropietarioConVehiculo(PropietarioRequestDTO propietarioDTO, VehiculoRequestDTO vehiculoDTO) {
        PropietarioEntity entity = generatePropietarioEntity(propietarioDTO);
        VehiculoEntity vehiculoEntity = generateVehiculoEntity(vehiculoDTO);

        entity.addVehiculo(vehiculoEntity);

        PropietarioEntity savedEntity = propietarioRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public PropietarioDTO obtenerPropietarioPorDni(String dni) {
        PropietarioEntity entity = propietarioRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));
        return PropietarioMapper.convertToDTO(entity); // Usando la clase de utilidad
    }
    @Override
    public void eliminarPropietario(String dni) {
        propietarioRepository.deleteById(dni);
    }

    private VehiculoEntity generateVehiculoEntity(VehiculoRequestDTO dto) {
        return new VehiculoEntity(dto.getPatente(), dto.getMarca(), dto.getModelo());
    }

    private PropietarioEntity generatePropietarioEntity(PropietarioRequestDTO dto) {
        return new PropietarioEntity(dto.getDni(), dto.getNombre(), dto.getApellido(), dto.getDireccion(), dto.getTelefono());
    }

    private PropietarioDTO convertToDTO(PropietarioEntity entity) {
        return new PropietarioDTO(entity.getDni(), entity.getNombre(), entity.getTelefono(), null);
    }
    @Override
    public PropietarioDTO guardarPropietario(PropietarioDTO propietarioDTO) {
        PropietarioEntity entity = convertToEntity(propietarioDTO); // Convierte a Entity
        PropietarioEntity savedEntity = propietarioRepository.save(entity);
        return PropietarioMapper.convertToDTO(savedEntity); // Usando la clase de utilidad
    }
    private PropietarioEntity convertToEntity(PropietarioDTO dto) {
        if (dto == null) {
            return null; // Manejo de nulos
        }
        PropietarioEntity entity = new PropietarioEntity();
        entity.setDni(dto.getDni());
        entity.setNombre(dto.getNombre());
        // Rellena otros campos...
        return entity;
    }
}

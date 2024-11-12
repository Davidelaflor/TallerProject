package com.example.taller.vehiculos.infrastructure.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import com.example.taller.vehiculos.domain.VehiculoDTO;
import com.example.taller.vehiculos.infrastructure.port.VehiculoServicePort;
import com.example.taller.vehiculos.utilities.VehiculoMapper;

@Service
public class VehiculoService implements VehiculoServicePort {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PropietarioServicePort propietarioServicePort;

    @Override
    public List<VehiculoDTO> obtenerVehiculosPorDni(String dni) {
        List<VehiculoEntity> vehiculosEntities = vehiculoRepository.findByPropietarioDni(dni);
        return vehiculosEntities.stream()
                .map(this::convertToDTO) // Convierte cada VehiculoEntity a VehiculoDTO
                .collect(Collectors.toList()); // Recoge el resultado en una lista
    }

    private VehiculoDTO convertToDTO(VehiculoEntity entity) {
        return new VehiculoDTO(entity.getPatente(), entity.getMarca(), entity.getModelo(), null);
    }

    @Override
    public VehiculoDTO crearVehiculo(VehiculoRequestDTO vehiculoDTO) {
        // Convertir VehiculoRequestDTO a VehiculoEntity
        VehiculoEntity vehiculoEntity = VehiculoEntity.builder()
                .patente(vehiculoDTO.getPatente())
                .marca(vehiculoDTO.getMarca())
                .modelo(vehiculoDTO.getModelo())
                .build();

        // Guardar la entidad en el repositorio
        VehiculoEntity savedEntity = vehiculoRepository.save(vehiculoEntity);

        // Convertir la entidad guardada en VehiculoDTO y devolverlo
        return new VehiculoDTO(savedEntity.getPatente(), savedEntity.getMarca(), savedEntity.getModelo(), null);
    }

    @Override
    public Optional<VehiculoDTO> obtenerVehiculoPorPatente(String patente) {
        Optional<VehiculoEntity> vehiculoEntityOptional = vehiculoRepository.findById(patente);

        if (vehiculoEntityOptional.isPresent()) {
            return Optional.of(convertToDTO(vehiculoEntityOptional.get())); // Convertir a DTO
        }
        return Optional.empty();
    }

    @Override
    public List<VehiculoDTO> obtenerTodosLosVehiculos() {
        // Obtener la lista de VehiculoEntity desde el repositorio
        List<VehiculoEntity> vehiculosEntities = vehiculoRepository.findAll();

        // Convertir la lista de VehiculoEntity a VehiculoDTO
        return vehiculosEntities.stream()
                .map(this::convertToDTO) // Convierte cada VehiculoEntity a VehiculoDTO
                .collect(Collectors.toList()); // Recoge el resultado en una lista
    }

    @Override
    public void eliminarVehiculo(String patente) {
        vehiculoRepository.deleteById(patente);
    }

    @Override
    public VehiculoDTO agregarVehiculoAPropietario(String dni, VehiculoRequestDTO vehiculoDTO) {
        // Encontrar al propietario en la base de datos
        PropietarioEntity propietario = propietarioServicePort.findById(dni).get();

        VehiculoEntity nuevoVehiculo = VehiculoMapper.toEntity(vehiculoDTO, propietario);

        propietario.addVehiculo(nuevoVehiculo);

        vehiculoRepository.save(nuevoVehiculo);

        propietarioServicePort.save(propietario);

        return VehiculoMapper.toDTO(nuevoVehiculo);
    }

    @Override
    public Optional<VehiculoEntity> findByPatente(String patente) {
        return vehiculoRepository.findById(patente);
    }

    @Override
    public boolean existsByPatenteAndPropietarioDni(String patente, String dni) {
        return vehiculoRepository.existsByPatenteAndPropietarioDni(patente, dni);
    }
}

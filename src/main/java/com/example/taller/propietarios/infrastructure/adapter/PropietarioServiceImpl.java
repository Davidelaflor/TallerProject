package com.example.taller.propietarios.infrastructure.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.propietarios.application.dto.PropietarioDTO;
import com.example.taller.propietarios.domain.model.Propietario;
import com.example.taller.propietarios.infrastructure.port.PropietarioService;
import com.example.taller.propietarios.infrastructure.repository.PropietarioRepository;
import com.example.taller.vehiculos.application.dto.VehiculoDTO;
import com.example.taller.vehiculos.domain.model.Vehiculo;
import com.example.taller.vehiculos.infrastructure.repository.VehiculoRepository;

@Service
public class PropietarioServiceImpl implements PropietarioService {
    @Autowired
    private PropietarioRepository propietarioRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public List<Propietario> listarPropietarios() {
        return propietarioRepository.findAll();
    }

    @Override
    public Propietario crearPropietarioConVehiculo(PropietarioDTO propietarioDTO, VehiculoDTO vehiculoDTO) {
          // Verificar si el propietario ya existe
          if (propietarioRepository.existsByDni(propietarioDTO.getDni())) {
            throw new IllegalArgumentException("El propietario con DNI " + propietarioDTO.getDni() + " ya está registrado.");
        }

        // Verificar si el vehículo ya existe
        if (vehiculoRepository.existsByPatente(vehiculoDTO.getPatente())) {
            throw new IllegalArgumentException("El vehículo con patente " + vehiculoDTO.getPatente() + " ya está registrado.");
        }
        
        Propietario entity = generatePropietarioEntity(propietarioDTO);
        Vehiculo vehiculoEntity = generateVehiculoEntity(vehiculoDTO);
        
        entity.addVehiculo(vehiculoEntity); 

        return propietarioRepository.save(entity);
        //return entity;
    }

    private Vehiculo generateVehiculoEntity(VehiculoDTO dto) {
        return Vehiculo.builder()
                .patente(dto.getPatente())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .build();
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

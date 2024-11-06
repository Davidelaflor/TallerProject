package com.example.taller.vehiculos.infrastructure.port;

import java.util.List;
import java.util.Optional;

import com.example.taller.vehiculos.application.CrearVehiculoParaPropietarioRequestDTO;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import com.example.taller.vehiculos.domain.VehiculoDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

public interface VehiculoServicePort {

    List<VehiculoDTO> obtenerVehiculosPorDni(String dni);

    VehiculoDTO crearVehiculo(VehiculoRequestDTO vehiculoDTO);

    Optional<VehiculoDTO> obtenerVehiculoPorPatente(String patente);

    List<VehiculoDTO> obtenerTodosLosVehiculos();

    VehiculoDTO agregarVehiculoAPropietario(String dni, VehiculoRequestDTO vehiculoDTO);

    void eliminarVehiculo(String patente);

    Optional<VehiculoEntity> findByPatente(String patente);

    boolean existsByPatenteAndPropietarioDni(String patente, String dni);

}

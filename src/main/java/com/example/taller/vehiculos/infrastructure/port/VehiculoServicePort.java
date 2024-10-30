package com.example.taller.vehiculos.infrastructure.port;

import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.vehiculos.application.CrearVehiculoParaPropietarioRequestDTO;
import com.example.taller.vehiculos.application.NuevoPropietarioRequestDTO;
import com.example.taller.vehiculos.application.VehiculoRequestDTO;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;

import java.util.List;

public interface VehiculoServicePort {

    List<VehiculoEntity> obtenerVehiculosPorDni(String dni);

    VehiculoEntity crearVehiculo(VehiculoEntity vehiculo);

    VehiculoEntity obtenerVehiculoPorPatente(String patente);

    List<VehiculoEntity> obtenerTodosLosVehiculos();

    VehiculoEntity agregarVehiculoAPropietario(String dni, CrearVehiculoParaPropietarioRequestDTO vehiculoDTO);

    PropietarioEntity crearPropietario(NuevoPropietarioRequestDTO propietarioDTO); // Cambiar a usar el nuevo DTO


    void eliminarVehiculo(String patente);
}

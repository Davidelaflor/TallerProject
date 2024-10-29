package com.example.taller.vehiculos.infrastructure.port;

import com.example.taller.propietarios.application.dto.NuevoPropietarioDTO;
import com.example.taller.propietarios.domain.model.Propietario;
import com.example.taller.vehiculos.application.dto.CrearVehiculoParaPropietarioDTO;
import com.example.taller.vehiculos.application.dto.VehiculoDTO;
import com.example.taller.vehiculos.domain.model.Vehiculo;

import java.util.List;

public interface VehiculoService {

    List<Vehiculo> obtenerVehiculosPorDni(String dni);

    Vehiculo crearVehiculo(Vehiculo vehiculo);

    Vehiculo obtenerVehiculoPorPatente(String patente);

    List<Vehiculo> obtenerTodosLosVehiculos();

    Vehiculo agregarVehiculoAPropietario(String dni, CrearVehiculoParaPropietarioDTO vehiculoDTO);

    Propietario crearPropietario(NuevoPropietarioDTO propietarioDTO); // Cambiar a usar el nuevo DTO


    void eliminarVehiculo(String patente);
}

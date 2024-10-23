package com.example.taller.service;

import com.example.taller.dto.CrearVehiculoParaPropietarioDTO;
import com.example.taller.dto.NuevoPropietarioDTO;
import com.example.taller.dto.VehiculoDTO;
import com.example.taller.model.Propietario;
import com.example.taller.model.Vehiculo;
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

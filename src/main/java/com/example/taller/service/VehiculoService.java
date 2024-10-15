package com.example.taller.service;

import com.example.taller.model.Vehiculo;
import java.util.List;

public interface VehiculoService {

    List<Vehiculo> obtenerVehiculosPorDni(String dni);

    Vehiculo crearVehiculo(Vehiculo vehiculo);

    Vehiculo obtenerVehiculoPorPatente(String patente);

    List<Vehiculo> obtenerTodosLosVehiculos();

    void eliminarVehiculo(String patente);
}

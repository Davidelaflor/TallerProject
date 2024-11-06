package com.example.taller.vehiculos.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.vehiculos.domain.VehiculoDTO;
import com.example.taller.vehiculos.infrastructure.port.VehiculoServicePort;

import jakarta.transaction.Transactional;

@Service
public class VehiculoApplicationService {
    @Autowired
    private VehiculoServicePort servicePort;

    public List<VehiculoDTO> obtenerVehiculosPorDni(String dni) {
        List<VehiculoDTO> vehiculos = servicePort.obtenerVehiculosPorDni(dni);
        if (vehiculos.isEmpty()) {
            throw new RuntimeException("No se encontraron vehículos para el propietario con DNI: " + dni);
        }
        return vehiculos;
    }
    public List<VehiculoDTO> obtenerTodosLosVehiculos() {
        return servicePort.obtenerTodosLosVehiculos(); // Asegúrate de que este método existe en el puerto
    }
    @Transactional
    public void eliminarVehiculo(String patente) {
        // Llama al servicio para eliminar el vehículo por su patente
        servicePort.eliminarVehiculo(patente);
    }
    public VehiculoDTO crearVehiculo(VehiculoRequestDTO vehiculoDTO) {
        return servicePort.crearVehiculo(vehiculoDTO);
    }

    public Optional<VehiculoDTO> obtenerVehiculoPorPatente(String patente) {
        return servicePort.obtenerVehiculoPorPatente(patente);
    }

    @Transactional
    public VehiculoDTO agregarVehiculoAPropietario(String dni, VehiculoRequestDTO vehiculoDTO) {
        return servicePort.agregarVehiculoAPropietario(dni, vehiculoDTO);
    }
    

}

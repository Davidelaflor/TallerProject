package com.example.taller.service;

import java.util.List;

import com.example.taller.dto.OrdenTrabajoDTO;
import com.example.taller.model.Empleado;
import com.example.taller.model.OrdenTrabajo;
import com.example.taller.model.Propietario;
import com.example.taller.model.Repuesto;
import com.example.taller.model.RepuestoUtilizado;

public interface TallerServiceInterface {
    // Métodos para manejar Empleados
    List<Empleado> listarEmpleados();
    Empleado guardarEmpleado(Empleado empleado);
    Empleado buscarEmpleadoPorId(Long id);
    void eliminarEmpleado(Long id);
    
    // Métodos para manejar Propietarios
    List<Propietario> listarPropietarios();
    Propietario guardarPropietario(Propietario propietario);
    Propietario buscarPropietarioPorDni(String dni);
    void eliminarPropietario(String dni);
    
    // Métodos para manejar Repuestos
    List<Repuesto> listarRepuestos();
    Repuesto guardarRepuesto(Repuesto repuesto);
    Repuesto buscarRepuestoPorCodigo(String codigo);
    void eliminarRepuesto(String codigo);
    void actualizarCantidad(String codigo, int cantidad);
    
    // Métodos para manejar Ordenes de Trabajo
    List<OrdenTrabajo> listarOrdenes();
    OrdenTrabajo crearOrdenTrabajo(OrdenTrabajoDTO ordenTrabajo);
    OrdenTrabajo obtenerOrdenTrabajo(Long id);
    OrdenTrabajo buscarOrdenTrabajoPorId(Long id);
    void eliminarOrdenTrabajo(Long id);
    
    // Métodos para manejar Repuestos Utilizados
    RepuestoUtilizado guardarRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado);
    void agregarRepuestoAOrdenTrabajo(Long ordenTrabajoId, String repuestoUtilizadoId, int cantidad);
    void agregarHorasAOrdenTrabajo(Long ordenTrabajoId, int horas); // Añadir este método
}

package com.example.taller.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.dto.OrdenTrabajoDTO;
import com.example.taller.model.Empleado;
import com.example.taller.model.OrdenTrabajo;
import com.example.taller.model.Propietario;
import com.example.taller.model.Repuesto;
import com.example.taller.model.RepuestoUtilizado;
import com.example.taller.repository.EmpleadoRepository;
import com.example.taller.repository.OrdenTrabajoRepository;
import com.example.taller.repository.PropietarioRepository;
import com.example.taller.repository.RepuestoRepository;
import com.example.taller.repository.RepuestoUtilizadoRepository;

@Service
public class TallerService implements TallerServiceInterface {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private RepuestoUtilizadoRepository repuestoUtilizadoRepository;

    // Métodos para manejar Empleados
    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado buscarEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    // Métodos para manejar Propietarios
    @Override
    public List<Propietario> listarPropietarios() {
        return propietarioRepository.findAll();
    }

    @Override
    public Propietario guardarPropietario(Propietario propietario) {
        return propietarioRepository.save(propietario);
    }

    @Override
    public Propietario buscarPropietarioPorDni(String dni) {
        return propietarioRepository.findById(dni).orElse(null);
    }

    @Override
    public void eliminarPropietario(String dni) {
        propietarioRepository.deleteById(dni);
    }

    @Override
    public List<Repuesto> listarRepuestos() {
        return repuestoRepository.findAll();
    }

    @Override
    public Repuesto guardarRepuesto(Repuesto repuesto) {
        return repuestoRepository.save(repuesto);
    }

    @Override
    public Repuesto buscarRepuestoPorCodigo(String codigo) {
        return repuestoRepository.findById(codigo).orElse(null);
    }

    @Override
    public void eliminarRepuesto(String codigo) {
        repuestoRepository.deleteById(codigo);
    }

    @Override
    public void actualizarCantidad(String codigo, int cantidad) {
        Repuesto repuesto = buscarRepuestoPorCodigo(codigo);
        if (repuesto != null) {
            repuesto.setCantidad(repuesto.getCantidad() - cantidad);
            repuestoRepository.save(repuesto);
        }
    }

    @Override
    public List<OrdenTrabajo> listarOrdenes() {
        return ordenTrabajoRepository.findAll();
    }

    @Override
    public OrdenTrabajo crearOrdenTrabajo(OrdenTrabajoDTO dto) {
        Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Propietario propietario = propietarioRepository.findById(dto.getPropietarioDni())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));
        OrdenTrabajo entity = generateOrdenTrabajoEntity(dto, empleado, propietario);

        return ordenTrabajoRepository.save(entity);
        
    }

    private OrdenTrabajo generateOrdenTrabajoEntity(OrdenTrabajoDTO dto, Empleado empleado, Propietario propietario) {
        return OrdenTrabajo.builder()
                .patente(dto.getPatente())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .detalleFalla(dto.getDetalleFalla())
                .horasTrabajadas(dto.getHorasTrabajadas())
                .estado(dto.getEstado())
                .empleado(empleado)
                .propietario(propietario)
                .fechaIngreso(LocalDate.now())
                .build();
    }

    @Override
    public OrdenTrabajo buscarOrdenTrabajoPorId(Long id) {
        return ordenTrabajoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarOrdenTrabajo(Long id) {
        ordenTrabajoRepository.deleteById(id);
    }

    @Override
    public RepuestoUtilizado guardarRepuestoUtilizado(RepuestoUtilizado repuestoUtilizado) {
        return repuestoUtilizadoRepository.save(repuestoUtilizado);
    }
}

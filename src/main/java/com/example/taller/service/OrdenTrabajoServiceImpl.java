package com.example.taller.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.dto.OrdenTrabajoDTO;
import com.example.taller.dto.RepuestoDTO;
import com.example.taller.exception.CustomException;
import com.example.taller.model.Empleado;
import com.example.taller.model.OrdenTrabajo;
import com.example.taller.model.Propietario;
import com.example.taller.model.Repuesto;
import com.example.taller.model.RepuestoUtilizado;
import com.example.taller.repository.EmpleadoRepository;
import com.example.taller.repository.OrdenTrabajoRepository;
import com.example.taller.repository.PropietarioRepository;
import com.example.taller.repository.RepuestoRepository;
import com.example.taller.repository.RepuestosUtilizadosRepository;

import jakarta.persistence.EntityNotFoundException;



@Service
public class OrdenTrabajoServiceImpl implements OrdenTrabajoService {

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private RepuestoRepository repuestoRepository; // Asegúrate de que esto esté definido correctamente

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

     @Autowired
    private RepuestosUtilizadosRepository repuestosUtilizadosRepository; // Asegúrate de que el nombre coincida


    

    @Override
    public boolean existeOrdenPorId(Long id) {
        return ordenTrabajoRepository.existsById(id);
    }

    @Override
    public boolean existeOrdenPorPatente(String patente) {
        return ordenTrabajoRepository.findByPatente(patente).isPresent();
    }
    

    @Override
    public OrdenTrabajo crearOrdenTrabajo(OrdenTrabajoDTO ordenTrabajoDto){//, Long empleadoCodigo, String propietarioDni, List<String> codigosRepuestos, List<Integer> cantidades) {
       
       

       System.out.println("Repuestos utilizados: " + ordenTrabajoDto.getRepuestosUtilizados()); //esto es para ver los logs en la consola y comprobar porque me sale null []

        // Verifica si el propietario ya existe
    Optional<Propietario> propietarioOpt = propietarioRepository.findByDni(ordenTrabajoDto.getPropietario().getDni());

    Propietario propietario;
    if (propietarioOpt.isPresent()) {
        propietario = propietarioOpt.get();
    }else{
        propietario = new Propietario();
        propietario.setDni(ordenTrabajoDto.getPropietario().getDni());
        propietario.setNombre(ordenTrabajoDto.getPropietario().getNombre());
        propietario.setApellido(ordenTrabajoDto.getPropietario().getApellido());
        propietario.setTelefono(ordenTrabajoDto.getPropietario().getTelefono());
        propietario.setDireccion(ordenTrabajoDto.getPropietario().getDireccion());
        
        // Guarda el nuevo propietario
        propietarioRepository.save(propietario);
    }

    // Ahora crea la nueva orden de trabajo
    OrdenTrabajo ordenTrabajo = new OrdenTrabajo();
    ordenTrabajo.setPatente(ordenTrabajoDto.getPatente());
    ordenTrabajo.setMarca(ordenTrabajoDto.getMarca());
    ordenTrabajo.setModelo(ordenTrabajoDto.getModelo());
    ordenTrabajo.setDetalleFalla(ordenTrabajoDto.getDetalleFalla());
    ordenTrabajo.setHorasTrabajadas(ordenTrabajoDto.getHorasTrabajadas());
    ordenTrabajo.setEstado(ordenTrabajoDto.getEstado());
    ordenTrabajo.setFechaIngreso(LocalDate.now()); // Establecer la fecha de ingreso, por ejemplo

    // Asigna el empleado usando el ID del DTO
    Empleado empleado = empleadoRepository.findById(ordenTrabajoDto.getEmpleado().getId())
            .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

    ordenTrabajo.setEmpleadoAsignado(empleado);
    ordenTrabajo.setPropietario(propietario); // Asigna el propietario (ya sea existente o nuevo)

    // Guarda la nueva orden de trabajo
    OrdenTrabajo ordenGuardada = ordenTrabajoRepository.save(ordenTrabajo);

    // Aquí podrías agregar la lógica para agregar los repuestos utilizados, si es necesario
    if (ordenTrabajoDto.getRepuestosUtilizados() != null) {
        for (RepuestoDTO repuestoDto : ordenTrabajoDto.getRepuestosUtilizados()) {
            Repuesto repuesto = repuestoRepository.findByCodigoInventario(repuestoDto.getCodigoInventario());
            if (repuesto != null) {
                RepuestoUtilizado repuestoUtilizado = new RepuestoUtilizado();
                repuestoUtilizado.setRepuesto(repuesto);
                repuestoUtilizado.setCantidad(repuestoDto.getCantidad());
                repuestoUtilizado.setOrdenTrabajo(ordenGuardada); // Establecer la relación con la orden

                // Guarda el repuesto utilizado en la base de datos
               //repuestosUtilizadosRepository.save(repuestoUtilizado);
            } else {
                throw new CustomException("Repuesto no encontrado para el código: " + repuestoDto.getCodigoInventario());
            }
        }
    }

    return ordenGuardada; // Retorna la orden de trabajo guardada
}

    @Override
    public OrdenTrabajo obtenerOrdenPorId(Long id) {
        return ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new CustomException("Orden no encontrada con ID: " + id));
    }

    @Override
    public void eliminarOrdenTrabajo(Long id) {
        if (!ordenTrabajoRepository.existsById(id)) {
            throw new CustomException("Orden no encontrada con ID: " + id);
        }
        ordenTrabajoRepository.deleteById(id);
    }

    @Override
    public OrdenTrabajo modificarOrdenTrabajo(Long id, OrdenTrabajoDTO ordenTrabajoDto) {
        OrdenTrabajo ordenExistente = ordenTrabajoRepository.findById(id)
        .orElseThrow(() -> new CustomException("Orden de trabajo no encontrada con ID: " + id));

// Actualiza los campos de la orden de trabajo
ordenExistente.setPatente(ordenTrabajoDto.getPatente());
ordenExistente.setMarca(ordenTrabajoDto.getMarca());
ordenExistente.setModelo(ordenTrabajoDto.getModelo());
ordenExistente.setDetalleFalla(ordenTrabajoDto.getDetalleFalla());
ordenExistente.setHorasTrabajadas(ordenTrabajoDto.getHorasTrabajadas());
ordenExistente.setEstado(ordenTrabajoDto.getEstado());

// Asigna el empleado si está presente en el DTO
Empleado empleado = empleadoRepository.findById(ordenTrabajoDto.getEmpleado().getId())
        .orElseThrow(() -> new CustomException("Empleado no encontrado con ID: " + ordenTrabajoDto.getEmpleado().getId()));
ordenExistente.setEmpleadoAsignado(empleado);




// Guardar los cambios en la base de datos
return ordenTrabajoRepository.save(ordenExistente); }

    @Override
    public List<OrdenTrabajo> listarOrdenes() {
        return ordenTrabajoRepository.findAll();
    }

 
    

}

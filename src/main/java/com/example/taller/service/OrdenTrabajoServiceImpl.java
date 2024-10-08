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
                repuestosUtilizadosRepository.save(repuestoUtilizado);
            } else {
                throw new CustomException("Repuesto no encontrado para el código: " + repuestoDto.getCodigoInventario());
            }
        }
    }

    return ordenGuardada; // Retorna la orden de trabajo guardada
}
       /*  OrdenTrabajoDTO nuevaOrden = new OrdenTrabajoDTO();

        nuevaOrden.setPatente(ordenTrabajoDto.getPatente());
        nuevaOrden.setMarca(ordenTrabajoDto.getMarca());
        nuevaOrden.setModelo(ordenTrabajoDto.getModelo());
        nuevaOrden.setDetalleFalla(ordenTrabajoDto.getDetalleFalla());
        nuevaOrden.setHorasTrabajadas(ordenTrabajoDto.getHorasTrabajadas());
        nuevaOrden.setEstado(ordenTrabajoDto.getEstado());
        nuevaOrden.setFechaIngreso(ordenTrabajoDto.getFechaIngreso());
    
        // Asignar el ID del empleado
        Empleado empleado = empleadoRepository.findById(empleadoCodigo)
        .orElseThrow(() -> new CustomException("Empleado no encontrado con ID: " + empleadoCodigo));
    nuevaOrden.setEmpleadoAsignado(empleado);


    Propietario propietario = propietarioRepository.findByDni(propietarioDni)
    .orElseThrow(() -> new CustomException("Propietario no encontrado con DNI: " + propietarioDni));
    nuevaOrden.setPropietario(propietario);

    OrdenTrabajoDTO ordenGuardada = ordenTrabajoRepository.save(nuevaOrden);

    return ordenGuardada; // Retorna la orden de trabajo guardada
}*/

   /*  @Override
    public OrdenTrabajoDTO modificarOrdenTrabajo(OrdenTrabajoDTO ordenTrabajo, List<String> codigosRepuestos) {
        Optional<OrdenTrabajoDTO> ordenExistenteOpt = ordenTrabajoRepository.findById(ordenTrabajo.getId());
        if (ordenExistenteOpt.isPresent()) {
            OrdenTrabajoDTO ordenExistente = ordenExistenteOpt.get();

            // Actualiza los campos necesarios
            ordenExistente.setPatente(ordenTrabajo.getPatente());
            ordenExistente.setMarca(ordenTrabajo.getMarca());
            ordenExistente.setModelo(ordenTrabajo.getModelo());
            ordenExistente.setDetalleFalla(ordenTrabajo.getDetalleFalla());
            ordenExistente.setEstado(ordenTrabajo.getEstado());
            ordenExistente.setHorasTrabajadas(ordenTrabajo.getHorasTrabajadas());
            ordenExistente.setFechaIngreso(ordenTrabajo.getFechaIngreso());
                    
            // Asigna el empleado usando el ID
            if (ordenTrabajo.getEmpleadoAsignado() != null) {
                Empleado empleado = empleadoRepository.findById(ordenTrabajo.getEmpleadoAsignado().getId())
                        .orElseThrow(() -> new CustomException("El empleado no existe."));
                ordenExistente.setEmpleadoAsignado(empleado); // Almacena el nombre o ID según tu implementación
            }

            // Actualiza la lista de repuestos utilizados
            if (codigosRepuestos != null) {
                List<RepuestoUtilizado> repuestosUtilizados =  new ArrayList<>();
                for (String codigo : codigosRepuestos) {
                    Repuesto repuesto = repuestoRepository.findByCodigoInventario(codigo);
                    if (repuesto != null) {
                        RepuestoUtilizado repuestoUtilizado = new RepuestoUtilizado();
                        repuestoUtilizado.setRepuesto(repuesto);
                        repuestosUtilizados.add(repuestoUtilizado);
                    } else {
                        throw new CustomException("Repuesto no encontrado para el código: " + codigo);
                    }
                }
    
                // Asigna la lista de repuestos utilizados a la orden existente
                ordenExistente.setRepuestosUtilizados(repuestosUtilizados);
            }
    
            // Guarda la orden de trabajo actualizada
            return ordenTrabajoRepository.save(ordenExistente);
        } else {
            throw new CustomException("La orden de trabajo con ID " + ordenTrabajo.getId() + " no existe.");
        }
    }


    @Override
    public List<OrdenTrabajoDTO> listarOrdenes() {
        return ordenTrabajoRepository.findAll();
    }

    @Override
    public OrdenTrabajoDTO obtenerOrdenPorId(Long id) {
        Optional<OrdenTrabajoDTO> orden = ordenTrabajoRepository.findById(id);
        return orden.orElseThrow(() -> new CustomException("Orden no encontrada"));
    }

    @Override
    public void eliminarOrdenTrabajo(Long id) {
        // Manejo de la lógica para eliminar una orden
        if (!ordenTrabajoRepository.existsById(id)) {
            throw new CustomException("Orden no encontrada");
        }
        ordenTrabajoRepository.deleteById(id);
    }*/
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
    public OrdenTrabajo modificarOrdenTrabajo(OrdenTrabajoDTO ordenTrabajoDto, List<String> codigosRepuestos) {
        // Implementación para modificar la orden de trabajo
        // ...
        return null; // Cambia esto por el objeto OrdenTrabajo actualizado
    }

    @Override
    public List<OrdenTrabajo> listarOrdenes() {
        return ordenTrabajoRepository.findAll();
    }


}

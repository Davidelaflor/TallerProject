package com.example.taller.ordenes.infrastructure.adapter;

import static com.example.taller.ordenes.utilities.OrdenTrabajoMapper.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoRepository;
import com.example.taller.ordenes.application.OrdenTrabajoRequestDTO;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.infrastructure.port.OrdenTrabajoServicePort;
import com.example.taller.ordenes.utilities.OrdenTrabajoMapper;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;
import com.example.taller.vehiculos.infrastructure.port.VehiculoServicePort;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrdenTrabajoService implements OrdenTrabajoServicePort {
    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private VehiculoServicePort vehiculoServicePort;

    @Autowired
    private EmpleadoRepository empleadoServicePort;

    @Autowired
    private PropietarioServicePort propietarioServicePort;

    @Override
    public OrdenTrabajoDTO crearOrdenTrabajo(OrdenTrabajoRequestDTO dto) {

        VehiculoEntity vehiculo = vehiculoServicePort.findByPatente(dto.getVehiculoPatente()).get();
        EmpleadoEntity empleado = empleadoServicePort.findById(dto.getEmpleadoId()).get();
        PropietarioEntity propietario = propietarioServicePort.findByDni(dto.getPropietarioDni()).get();

        OrdenTrabajoEntity ordenEntity = new OrdenTrabajoEntity();
        ordenEntity.setDetalleFalla(dto.getDetalleFalla());
        ordenEntity.setHorasTrabajadas(dto.getHorasTrabajadas());
        ordenEntity.setFechaIngreso(dto.getFechaIngreso());
        ordenEntity.setEstado("ACTIVO");
        ordenEntity.setVehiculo(vehiculo);
        ordenEntity.setEmpleado(empleado);
        ordenEntity.setPropietario(propietario);

        OrdenTrabajoEntity savedEntity = ordenTrabajoRepository.save(ordenEntity);

        return toDTO(savedEntity);

    }

@Override
public OrdenTrabajoDTO finalizarOrdenTrabajo(Long id){
    Optional<OrdenTrabajoEntity> ordenOptional = ordenTrabajoRepository.findById(id);
        
    OrdenTrabajoEntity orden = ordenOptional.get(); // Obtener la entidad del Optional
    
    orden.setEstado("finalizado");
    OrdenTrabajoEntity savedOrden = ordenTrabajoRepository.save(orden);

    return toDTO(savedOrden);
    }
 

    @Override
    public void eliminarOrdenTrabajo(Long id) {
        ordenTrabajoRepository.deleteById(id);
    }

    @Override
    public double calcularCostoTotal(Long ordenTrabajoId) {
        OrdenTrabajoEntity orden = ordenTrabajoRepository.findById(ordenTrabajoId)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));
        // Calcular el costo de la mano de obra
        final double costoPorHora = 50.0;
        double costoManoObra = orden.getHorasTrabajadas() * costoPorHora;

        // Calcular el costo de los repuestos utilizados
        double costoRepuestos = orden.getRepuestosUtilizados().stream()
                .mapToDouble(r -> r.getRepuesto().getPrecio() * r.getCantidad())
                .sum();

        return costoManoObra + costoRepuestos;
    }

    public OrdenTrabajoDTO obtenerOrdenTrabajo(Long id) {
        OrdenTrabajoEntity ordenTrabajoEntity = ordenTrabajoRepository.findById(id).orElse(null);
        return toDTO(ordenTrabajoEntity); // Conversión a DTO
    }

    @Override
    public List<OrdenTrabajoDTO> listarOrdenes() {
        List<OrdenTrabajoEntity> ordenesTrabajo = ordenTrabajoRepository.findByEstado("ACTIVO");
        return ordenesTrabajo.stream()
            .map(OrdenTrabajoMapper::toDTO) // no puedo usar el truco de omitir OrdeTrabajoMapper porque uso map y stream
            .collect(Collectors.toList());
    }

    @Override
    public List<OrdenTrabajoDTO> findByPropietarioDniAndEstado(String dni, String estado) {
        List<OrdenTrabajoEntity> ordenesFinalizadas = ordenTrabajoRepository.findByPropietarioDniAndEstado(dni, estado);
        return ordenesFinalizadas.stream()
                .map(OrdenTrabajoMapper::toDTO)  
                .collect(Collectors.toList());
    }

    @Override
    public OrdenTrabajoDTO agregarHorasAOrdenTrabajo(Long ordenTrabajoId, int horas) {
        Optional<OrdenTrabajoEntity> ordenTrabajoOpt = ordenTrabajoRepository.findById(ordenTrabajoId);

        if (ordenTrabajoOpt.isPresent()) {
            OrdenTrabajoEntity ordenTrabajo = ordenTrabajoOpt.get();
            ordenTrabajo.setHorasTrabajadas(ordenTrabajo.getHorasTrabajadas() + horas);
            OrdenTrabajoEntity savedOrden = ordenTrabajoRepository.save(ordenTrabajo);
            
            return toDTO(savedOrden); // Convierte la entidad actualizada a DTO
        }
        return null;
    }

    @Override
    public Optional<OrdenTrabajoEntity> findById(Long id) {
        return ordenTrabajoRepository.findById(id);
    }

    @Override
    public OrdenTrabajoEntity save(OrdenTrabajoEntity ordenTrabajo) {
        return ordenTrabajoRepository.save(ordenTrabajo);
    }

    @Override
    public boolean existeOrdenTrabajoPorId(Long id) {
        return ordenTrabajoRepository.existsById(id);
    }

    @Override
    public boolean existeOrdenTrabajoPorVehiculo(String patente) {
        return ordenTrabajoRepository.existsByVehiculo_Patente(patente);
    }
}

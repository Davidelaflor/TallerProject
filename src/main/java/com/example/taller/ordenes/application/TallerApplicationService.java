package com.example.taller.ordenes.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.taller.ordenes.application.dto.OrdenTrabajoDTO;
import com.example.taller.ordenes.domain.dto.OrdenTrabajoResponseDTO;
import com.example.taller.ordenes.domain.model.OrdenTrabajo;
import com.example.taller.ordenes.infrastructure.port.TallerServicePort;

@Service
public class TallerApplicationService {
    private final TallerServicePort tallerServicePort;

    public TallerApplicationService(TallerServicePort tallerServicePort) {
        this.tallerServicePort = tallerServicePort;
    }

    public List<OrdenTrabajoResponseDTO> listarOrdenes() {
        List<OrdenTrabajo> ordenes = tallerServicePort.listarOrdenes();
        return ordenes.stream()
                .map(this::convertirAResponseDTO) // Método de conversión
                .collect(Collectors.toList());
    }

    public OrdenTrabajoResponseDTO crearOrdenTrabajo(OrdenTrabajoDTO ordenTrabajoDTO) {
        OrdenTrabajo ordenTrabajo = convertirADominio(ordenTrabajoDTO);
        OrdenTrabajo nuevaOrden = tallerServicePort.crearOrdenTrabajo(ordenTrabajo);
        return convertirAResponseDTO(nuevaOrden);
    }

    public OrdenTrabajoResponseDTO obtenerOrdenTrabajo(Long id) {
        OrdenTrabajo ordenTrabajo = tallerServicePort.obtenerOrdenTrabajo(id);
        return convertirAResponseDTO(ordenTrabajo);
    }

    public void eliminarOrdenTrabajo(Long id) {
        tallerServicePort.eliminarOrdenTrabajo(id);
    }

    public double calcularCostoTotal(OrdenTrabajoDTO ordenTrabajoDTO) {
        return tallerServicePort.calcularCostoTotal(ordenTrabajoDTO);
    }

    // Método de utilidad para convertir de OrdenTrabajo a OrdenTrabajoResponseDTO
    private OrdenTrabajoResponseDTO convertirAResponseDTO(OrdenTrabajo ordenTrabajo) {
        String nombreEmpleado = (ordenTrabajo.getEmpleado() != null) ? ordenTrabajo.getEmpleado().getNombre() : "Desconocido";
        String nombrePropietario = (ordenTrabajo.getPropietario() != null) ? ordenTrabajo.getPropietario().getNombre() : "Desconocido";
        String patenteVehiculo = (ordenTrabajo.getVehiculo() != null) ? ordenTrabajo.getVehiculo().getPatente() : "Desconocida";

        return new OrdenTrabajoResponseDTO(
            ordenTrabajo.getId(),
            nombreEmpleado,
            nombrePropietario,
            patenteVehiculo,
            ordenTrabajo.getFechaIngreso(),
            ordenTrabajo.getHorasTrabajadas(),
            ordenTrabajo.getEstado()
            // Agrega más campos según sea necesario
        );
    }

    // Método para convertir de OrdenTrabajoDTO a OrdenTrabajo
    private OrdenTrabajo convertirADominio(OrdenTrabajoDTO ordenTrabajoDTO) {
        return new OrdenTrabajo(
            null, // ID autogenerado
            ordenTrabajoDTO.getDetalleFalla(), // Asumiendo que esto es un campo en OrdenTrabajoDTO
            ordenTrabajoDTO.getHorasTrabajadas(), // Asumiendo que esto es un campo en OrdenTrabajoDTO
            null, // Aquí deberías obtener el Empleado desde su ID o un método
            null, // Aquí deberías obtener el Propietario desde su ID o un método
            null, // Aquí deberías obtener el Vehículo desde su ID o un método
            ordenTrabajoDTO.getFechaIngreso() // Asumiendo que esto es un campo en OrdenTrabajoDTO
        );
    }
}

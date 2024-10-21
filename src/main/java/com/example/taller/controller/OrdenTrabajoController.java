package com.example.taller.controller;

import java.util.List;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.dto.HorasTrabajadasDTO;

import com.example.taller.dto.EmpleadoResponseDTO;
import com.example.taller.dto.OrdenTrabajoDTO;
import com.example.taller.dto.OrdenTrabajoResponseDTO;
import com.example.taller.dto.PropietarioResponseDTO;
import com.example.taller.dto.RepuestoUtilizadoDTO;
import com.example.taller.dto.RepuestoUtilizadoResponseDTO;
import com.example.taller.dto.VehiculoResponseDTO;
import com.example.taller.model.Empleado;
import com.example.taller.model.OrdenTrabajo;
import com.example.taller.model.Propietario;
import com.example.taller.model.RepuestoUtilizado;
import com.example.taller.model.Vehiculo;
import com.example.taller.service.TallerServiceInterface;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenTrabajoController {
        @Autowired
        private TallerServiceInterface tallerService;

        @GetMapping
        public ResponseEntity<List<OrdenTrabajoResponseDTO>> listarOrdenes() {
            List<OrdenTrabajo> ordenesTrabajo = tallerService.listarOrdenes();
        
            // Mapeo de la lista de órdenes de trabajo a una lista de OrdenTrabajoResponseDTO
            List<OrdenTrabajoResponseDTO> ordenesTrabajoDTO = ordenesTrabajo.stream()
                    .map(ordenTrabajo -> {
                        // Obtener el vehículo directamente de la orden
                        VehiculoResponseDTO vehiculoDTO = new VehiculoResponseDTO(
                                ordenTrabajo.getVehiculo().getPatente(),
                                ordenTrabajo.getVehiculo().getMarca(),
                                ordenTrabajo.getVehiculo().getModelo()
                        );
        
                        // Mapeo del propietario a DTO
                        PropietarioResponseDTO propietarioDTO = new PropietarioResponseDTO(
                                ordenTrabajo.getPropietario().getDni(),
                                ordenTrabajo.getPropietario().getNombre(),
                                ordenTrabajo.getPropietario().getTelefono()
                        );
        
                        // Crear DTO de respuesta de la orden de trabajo
                        return new OrdenTrabajoResponseDTO(
                                ordenTrabajo.getId(),
                                ordenTrabajo.getDetalleFalla(),
                                ordenTrabajo.getHorasTrabajadas(),
                                ordenTrabajo.getEstado(),
                                ordenTrabajo.getFechaIngreso(),
                                vehiculoDTO, // Agregar vehículo DTO aquí
                                propietarioDTO
                        );
        
                    }).toList();
        
            return ResponseEntity.ok(ordenesTrabajoDTO);
        }
                
        


        @GetMapping("/{id}")
        public ResponseEntity<OrdenTrabajoResponseDTO> obtenerOrden(@PathVariable Long id) {
                OrdenTrabajo ordenTrabajo = tallerService.obtenerOrdenTrabajo(id);
                if (ordenTrabajo == null) {
                        return ResponseEntity.notFound().build();
                }

                // Mapeo de repuestos utilizados a DTO
                List<RepuestoUtilizadoResponseDTO> repuestosUtilizados = ordenTrabajo.getRepuestoUtilizado().stream()
                                .map(repuestoUtilizado -> new RepuestoUtilizadoResponseDTO(
                                                repuestoUtilizado.getRepuesto().getCodigoInventario(),
                                                repuestoUtilizado.getRepuesto().getNombre(),
                                                repuestoUtilizado.getRepuesto().getPrecio(),
                                                repuestoUtilizado.getCantidad()))
                                .toList();

                // Mapeo de los vehículos del propietario a DTO
                List<VehiculoResponseDTO> vehiculos = ordenTrabajo.getPropietario().getVehiculos().stream()
                                .map(vehiculo -> new VehiculoResponseDTO(
                                                vehiculo.getPatente(),
                                                vehiculo.getMarca(),
                                                vehiculo.getModelo()))
                                .toList();

                // Mapeo del propietario a DTO
                PropietarioResponseDTO propietarioDTO = new PropietarioResponseDTO(
                                ordenTrabajo.getPropietario().getDni(),
                                ordenTrabajo.getPropietario().getNombre(),
                                ordenTrabajo.getPropietario().getTelefono(),
                                vehiculos);

                EmpleadoResponseDTO empleadoDTO = new EmpleadoResponseDTO(
                                ordenTrabajo.getEmpleado().getId(),
                                ordenTrabajo.getEmpleado().getNombre(),
                                ordenTrabajo.getEmpleado().getApellido(), // Añadir apellido
                                ordenTrabajo.getEmpleado().getTelefono());

                // Crear DTO de respuesta de la orden de trabajo
                OrdenTrabajoResponseDTO responseDTO = new OrdenTrabajoResponseDTO(
                                ordenTrabajo.getId(),
                                ordenTrabajo.getDetalleFalla(),
                                ordenTrabajo.getHorasTrabajadas(),
                                ordenTrabajo.getEstado(),
                                ordenTrabajo.getFechaIngreso(), // Mapeo de la fecha de ingreso
                                empleadoDTO, // Mapeo del empleado
                                repuestosUtilizados,
                                propietarioDTO);
                return ResponseEntity.ok(responseDTO);
                // return ordenTrabajo != null ? ResponseEntity.ok(ordenTrabajo) :
                // ResponseEntity.notFound().build();
        }

        @PostMapping
        public ResponseEntity<OrdenTrabajoResponseDTO> crearOrdenTrabajo(@RequestBody OrdenTrabajoDTO ordenTrabajo) {
                OrdenTrabajo nuevaOrden = tallerService.crearOrdenTrabajo(ordenTrabajo);

                // Mapear OrdenTrabajo a OrdenTrabajoResponseDTO
                OrdenTrabajoResponseDTO responseDTO = new OrdenTrabajoResponseDTO(
                                nuevaOrden.getId(),
                                nuevaOrden.getDetalleFalla(),
                                nuevaOrden.getHorasTrabajadas(),
                                nuevaOrden.getEstado(),
                                nuevaOrden.getFechaIngreso(),
                                new EmpleadoResponseDTO(
                                                nuevaOrden.getEmpleado().getId(),
                                                nuevaOrden.getEmpleado().getNombre(),
                                                nuevaOrden.getEmpleado().getApellido(),
                                                nuevaOrden.getEmpleado().getTelefono()),
                                nuevaOrden.getRepuestoUtilizado().stream()
                                                .map(repuestoUtilizado -> new RepuestoUtilizadoResponseDTO(
                                                                repuestoUtilizado.getRepuesto().getCodigoInventario(),
                                                                repuestoUtilizado.getRepuesto().getNombre(),
                                                                repuestoUtilizado.getRepuesto().getPrecio(),
                                                                repuestoUtilizado.getCantidad()))
                                                .toList(),
                                new PropietarioResponseDTO(
                                                nuevaOrden.getPropietario().getDni(),
                                                nuevaOrden.getPropietario().getNombre(),
                                                nuevaOrden.getPropietario().getTelefono(),
                                                List.of(new VehiculoResponseDTO(
                                                                nuevaOrden.getVehiculo().getPatente(),
                                                                nuevaOrden.getVehiculo().getMarca(),
                                                                nuevaOrden.getVehiculo().getModelo()))));

                return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarOrdenTrabajo(@PathVariable Long id) {
                tallerService.eliminarOrdenTrabajo(id);
                return ResponseEntity.noContent().build();
        }

        @PostMapping("/{ordenTrabajoId}/repuestos/{repuestoUtilizadoId}")
        public ResponseEntity<Void> agregarRepuestoAOrden(@PathVariable Long ordenTrabajoId,
                        @PathVariable String repuestoUtilizadoId,
                        @RequestBody RepuestoUtilizadoDTO repuestoUtilizadoDTO) {
                int cantidad = repuestoUtilizadoDTO.getCantidadUtilizada();
                tallerService.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoId, cantidad);
                return ResponseEntity.ok().build();
        }

        @PostMapping("/{ordenTrabajoId}/horas")
        public ResponseEntity<Void> agregarHorasAOrden(@PathVariable Long ordenTrabajoId,
                        @RequestBody HorasTrabajadasDTO horasTrabajadasDTO) {
                int horas = horasTrabajadasDTO.getHorasTrabajadas();
                tallerService.agregarHorasAOrdenTrabajo(ordenTrabajoId, horas);
                return ResponseEntity.ok().build();
        }

        @PostMapping("/calcular-costo")
        public double calcularCosto(@RequestBody OrdenTrabajoDTO ordenTrabajoDTO) {
                // Calcular el costo total utilizando el servicio
                return tallerService.calcularCostoTotal(ordenTrabajoDTO);
        }

}

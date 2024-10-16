package com.example.taller.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.dto.OrdenTrabajoDTO;
import com.example.taller.dto.RepuestoUtilizadoDTO;
import com.example.taller.model.OrdenTrabajo;
import com.example.taller.service.TallerServiceInterface;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenTrabajoController {
@Autowired
    private TallerServiceInterface tallerService;

    @GetMapping
    public List<OrdenTrabajo> listarOrdenes() {
        return tallerService.listarOrdenes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenTrabajo> obtenerOrden(@PathVariable Long id) {
        OrdenTrabajo ordenTrabajo = tallerService.buscarOrdenTrabajoPorId(id);
        return ordenTrabajo != null ? ResponseEntity.ok(ordenTrabajo) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrdenTrabajo> crearOrdenTrabajo(@RequestBody OrdenTrabajoDTO ordenTrabajo) {
        OrdenTrabajo nuevaOrden = tallerService.crearOrdenTrabajo(ordenTrabajo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
    }

      @PutMapping("/{id}")
    public ResponseEntity<OrdenTrabajo> modificarOrdenTrabajo(@PathVariable Long id, @RequestBody OrdenTrabajoDTO dto) {
        OrdenTrabajo ordenModificada = tallerService.modificarOrdenTrabajo(id, dto);
        return ResponseEntity.ok(ordenModificada);
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
}

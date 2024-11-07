package com.example.taller.RepuestoUtilizado.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.RepuestoUtilizado.infrastructure.adapter.RepuestoUtilizadoEntity;
import com.example.taller.RepuestoUtilizado.infrastructure.port.RepuestoUtilizadoServicePort;
import com.example.taller.ordenes.application.OrdenTrabajoApplicationService;
import com.example.taller.repuestos.infrastructure.adapter.RepuestoEntity;
import com.example.taller.repuestos.infrastructure.port.RepuestoServicePort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/repuestos-utilizados")
public class RepuestoUtilizadoController { 

    @Autowired
    private RepuestoUtilizadoApplicationService repuestoUtilizadoApplicationService;
    @PostMapping("/{ordenTrabajoId}/repuestos")
    public ResponseEntity<Void> agregarRepuestoAOrden(
            @PathVariable Long ordenTrabajoId,
            @RequestBody RepuestoUtilizadoRequestDTO repuestoUtilizadoDTO) {

        // Llamar al service para agregar el repuesto a la orden de trabajo
        repuestoUtilizadoApplicationService.agregarRepuestoAOrdenTrabajo(ordenTrabajoId, repuestoUtilizadoDTO);

        // Retornar una respuesta exitosa (200 OK)
        return ResponseEntity.ok().build();
    }
}

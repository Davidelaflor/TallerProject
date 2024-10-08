package com.example.taller.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.taller.model.OrdenTrabajo;

import com.example.taller.dto.OrdenTrabajoDTO;
import com.example.taller.service.OrdenTrabajoService;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenTrabajoController {
    //Autowired =
        private final OrdenTrabajoService service;
        
        public OrdenTrabajoController(OrdenTrabajoService service) {
            this.service = service;
        }

    @PostMapping
    public ResponseEntity<OrdenTrabajo> crearOrdenTrabajo(
            @RequestBody OrdenTrabajoDTO ordenTrabajoDto)//,
          // @RequestParam List<String> codigosRepuestos,
          // @RequestParam List<Integer> cantidades,
          // @RequestParam Long empleadoCodigo,
          // @RequestParam String propietarioDni)
           {
        OrdenTrabajo nuevaOrden = service.crearOrdenTrabajo(ordenTrabajoDto);//, empleadoCodigo, propietarioDni, codigosRepuestos, cantidades);
        return ResponseEntity.ok(nuevaOrden);

    }



    @GetMapping
    public ResponseEntity<List<OrdenTrabajo>> listarOrdenes() {
        return ResponseEntity.ok(service.listarOrdenes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenTrabajo> modificarOrden(@PathVariable Long id, @RequestBody OrdenTrabajoDTO ordenTrabajoDto,         @RequestParam(required = false) List<String> codigosRepuestos){
        ordenTrabajoDto.setId(id);
        OrdenTrabajo ordenModificada = service.modificarOrdenTrabajo(ordenTrabajoDto, codigosRepuestos);

        return ResponseEntity.ok(ordenModificada);
    }

}

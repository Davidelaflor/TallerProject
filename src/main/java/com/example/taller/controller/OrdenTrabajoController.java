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
          
            if (service.existeOrdenPorPatente(ordenTrabajoDto.getPatente())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Manejo de conflicto error 406
            }
        
       
        OrdenTrabajo nuevaOrden = service.crearOrdenTrabajo(ordenTrabajoDto);//, empleadoCodigo, propietarioDni, codigosRepuestos, cantidades);
        return ResponseEntity.ok(nuevaOrden);

    }
    /*{
  "patente": "ABC123",
  "marca": "Toyota",
  "modelo": "Corolla",
  "detalleFalla": "No arranca",
  "horasTrabajadas": 5,
  "estado": "ACTIVO",
  "fechaIngreso": "2024-10-07",
  "empleado": {
    "id": 1 // ID del empleado asignado
  },
  "propietario": {
    "dni": "12345678A",
    "nombre": "Juan",
    "apellido": "Pérez",
    "telefono": "123456789",
    "direccion": "Calle Falsa 123"
  },
  "repuestosUtilizados": [
    {
      "codigoInventario": "REP001",
      "cantidad": 2
    },
    {
      "codigoInventario": "REP002",
      "cantidad": 1
    }
  ]
}
 */


 
    @GetMapping
    public ResponseEntity<List<OrdenTrabajo>> listarOrdenes() {
        return ResponseEntity.ok(service.listarOrdenes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenTrabajo> modificarOrden(@PathVariable Long id, @RequestBody OrdenTrabajoDTO ordenTrabajoDto){//,         @RequestParam(required = false) List<String> codigosRepuestos){
        ordenTrabajoDto.setId(id);
        OrdenTrabajo ordenModificada = service.modificarOrdenTrabajo(id, ordenTrabajoDto);

        return ResponseEntity.ok(ordenModificada);
    }

      @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrdenTrabajo(@PathVariable Long id) {
        service.eliminarOrdenTrabajo(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }
}

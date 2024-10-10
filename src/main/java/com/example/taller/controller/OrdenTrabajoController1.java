/*package com.example.taller.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.dto.OrdenTrabajoDTO;
import com.example.taller.model.OrdenTrabajo;
import com.example.taller.service.OrdenTrabajoService;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class OrdenTrabajoController {
    //Autowired 
        private final OrdenTrabajoService service;
        
        public OrdenTrabajoController(OrdenTrabajoService service) {
            this.service = service;
        }

    @PostMapping
    public ResponseEntity<Object> crearOrdenTrabajo(
            @RequestBody OrdenTrabajoDTO ordenTrabajoDto)//,
          // @RequestParam List<String> codigosRepuestos,
          // @RequestParam List<Integer> cantidades,
          // @RequestParam Long empleadoCodigo,
          // @RequestParam String propietarioDni)
           {
          
            if (service.existeOrdenPorPatente(ordenTrabajoDto.getPatente())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(createResponse("Orden ya registrada. Coincidencia en patente de vehiculo",null)); // Manejo de conflicto error 406
            }
        
       
        OrdenTrabajo nuevaOrden = service.crearOrdenTrabajo(ordenTrabajoDto);  //, empleadoCodigo, propietarioDni, codigosRepuestos, cantidades);
        return ResponseEntity.status(HttpStatus.CREATED) // 201 Created
        .body(createResponse("Orden creada con éxito.", nuevaOrden));

    }
    //Método auxiliar para crear la respuesta
private Object createResponse(String message, OrdenTrabajo ordenTrabajo) {
    return new Object() {
        public final String msg = message;
        public final OrdenTrabajo orden = ordenTrabajo;
    };
  }

/* 
    {
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


 
   /*  @GetMapping
    public ResponseEntity<List<OrdenTrabajo>> listarOrdenes() {
        return ResponseEntity.ok(service.listarOrdenes());
    }*/

    /*@PutMapping("/{id}")
    public ResponseEntity<OrdenTrabajo> modificarOrden(@PathVariable Long id, @RequestBody OrdenTrabajoDTO ordenTrabajoDto){//,         @RequestParam(required = false) List<String> codigosRepuestos){
        ordenTrabajoDto.setId(id);
        OrdenTrabajo ordenModificada = service.modificarOrdenTrabajo(id, ordenTrabajoDto);

        return ResponseEntity.ok(ordenModificada);
    }*/

   /*  @PutMapping("/{id}")
public ResponseEntity<Object> modificarOrden(@PathVariable Long id, @RequestBody OrdenTrabajoDTO ordenTrabajoDto) {
    // Establecer el ID de la orden en el DTO
    ordenTrabajoDto.setId(id);

    // Modificar la orden
    OrdenTrabajo ordenModificada = service.modificarOrdenTrabajo(id, ordenTrabajoDto);

    // Devolver la respuesta con el mensaje de éxito y la orden modificada
    return ResponseEntity.ok(createResponse("Orden modificada con éxito.", ordenModificada));
}


      @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOrdenTrabajo(@PathVariable Long id) {
        service.eliminarOrdenTrabajo(id);
        return ResponseEntity.ok("Orden eliminada con éxito."); // Devuelve 204 No Content
    }
}*/

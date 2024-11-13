package com.example.taller.calles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalleController {
    private final CalleService calleService;

    public CalleController(CalleService calleService) {
        this.calleService = calleService;
    }

    @GetMapping("/api/calles/geolocalizacion")
    public ResponseEntity<String> obtenerGeolocalizacion(@RequestParam String direccion/* ,
                                        @RequestParam String barrio,
                                         @RequestParam String ciudad,
                                         @RequestParam String pais*/) {
 // Primero validamos la dirección
        boolean esValida = calleService.validarDireccion(direccion);

        if (esValida) {
            // Si la dirección es válida, obtenemos la geolocalización
            String geolocalizacion = calleService.buscarDireccion(direccion);
            return ResponseEntity.ok(geolocalizacion); // Devolvemos la geolocalización
        } else {
            // Si la dirección no es válida, devolvemos un error con un mensaje apropiado
            return ResponseEntity.badRequest().body("La dirección no es válida.");
        }
    }    }


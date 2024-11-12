package com.example.taller.calles;

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
    public String obtenerGeolocalizacion(@RequestParam String direccion,
                                        @RequestParam String barrio,
                                         @RequestParam String ciudad,
                                         @RequestParam String pais) {
        return calleService.buscarDireccion(direccion, barrio, ciudad, pais);
    }
}

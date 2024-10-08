package com.example.taller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ordenes")
public class OrdenTrabajoViewController {
 @GetMapping("/crear")
    public String mostrarFormularioCrearOrden() {
        return "crearOrden"; // Nombre del archivo sin la extensión .html
    }
}

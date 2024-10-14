package com.example.taller.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Estado {
    ACTIVO,
    FINALIZADO;
}

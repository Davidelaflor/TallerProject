package com.example.taller.ordenes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Estado {
    ACTIVO,
    FINALIZADO;
}

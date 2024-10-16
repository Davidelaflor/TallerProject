package com.example.taller.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Estado {
    ACTIVO,
    FINALIZADO;
}

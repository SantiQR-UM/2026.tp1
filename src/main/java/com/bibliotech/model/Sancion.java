package com.bibliotech.model;

import java.time.LocalDate;

public record Sancion(int sancionId, int socioId, LocalDate fechaInicio, LocalDate fechaFin, String motivo) {}
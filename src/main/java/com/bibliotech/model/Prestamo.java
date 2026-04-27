package com.bibliotech.model;

import java.time.LocalDate;

public record Prestamo(int prestamoId, String isbn, int socioId, LocalDate fechaPrestamo, LocalDate fechaDevolucionPrevista, LocalDate fechaDevolucionReal, EstadoPrestamo estado) {}
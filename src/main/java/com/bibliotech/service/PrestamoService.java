package com.bibliotech.service;

// src/com/bibliotech/service/PrestamoService.java
public class PrestamoService {
    private final Repository<Libro, String> libroRepo;
    private final Repository<Socio, Integer> socioRepo;

    // Inyección por constructor
    public PrestamoService(Repository<Libro, String> libroRepo, Repository<Socio, Integer> socioRepo) {
        this.libroRepo = libroRepo;
        this.socioRepo = socioRepo;
    }

    public void realizarPrestamo(String isbn, int socioId) throws BibliotecaException {
        // Implementar validaciones y lógica
    }
}
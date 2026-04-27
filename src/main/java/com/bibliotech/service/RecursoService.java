package com.bibliotech.service;

import java.util.List;
import java.util.Optional;

import com.bibliotech.model.Recurso;
import com.bibliotech.model.Prestamo;
import com.bibliotech.repository.Repository;
import com.bibliotech.repository.RecursoRepository;
import com.bibliotech.repository.PrestamoRepository;
import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.IsbnDuplicadoException;


public class RecursoService {

    private final Repository<Recurso, String> recursoRepo;
    private final Repository<Prestamo, Integer> prestamoRepo;

    public RecursoService(Repository<Recurso, String> recursoRepo, Repository<Prestamo, Integer> prestamoRepo) {
        this.recursoRepo = recursoRepo;
        this.prestamoRepo = prestamoRepo;
    }

    public void registrarRecurso(Recurso recurso) throws BibliotecaException {
        Optional<Recurso> existente = recursoRepo.buscarPorId(recurso.isbn());
        if (existente.isPresent()) {
            throw new IsbnDuplicadoException("Ya existe un recurso con ISBN: " + recurso.isbn());
        }
        recursoRepo.guardar(recurso);
    }

    public Optional<Recurso> buscarPorIsbn(String isbn) {
        return recursoRepo.buscarPorId(isbn);
    }

    public List<Recurso> buscarPorTitulo(String titulo) {
        return ((RecursoRepository)recursoRepo).buscarPorTitulo(titulo);
    }

    public List<Recurso> buscarPorAutor(String autor) {
        return ((RecursoRepository)recursoRepo).buscarPorAutor(autor);
    }

    public List<Recurso> buscarPorCategoria(String categoria) {
        return ((RecursoRepository)recursoRepo).buscarPorCategoria(categoria);
    }

    public List<Recurso> buscarPorAno(int ano) {
        return ((RecursoRepository)recursoRepo).buscarPorAno(ano);
    }

    public boolean estaDisponible(String isbn) {
        List<Prestamo> prestamosActivos = ((PrestamoRepository)prestamoRepo).buscarPrestamosActivosPorRecurso(isbn);
        // Si la lista está vacía, está disponible
        return prestamosActivos.isEmpty();
    }
}
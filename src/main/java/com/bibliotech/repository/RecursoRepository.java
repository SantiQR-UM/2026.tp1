package com.bibliotech.repository;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.bibliotech.model.Recurso;

public class RecursoRepository implements Repository<Recurso, String> {

    private final Map<String, Recurso> storage = new HashMap<>();

    @Override
    public void guardar(Recurso entidad) {
        storage.put(entidad.isbn(), entidad);
    }

    @Override
    public Optional<Recurso> buscarPorId(String isbn) {
        return Optional.ofNullable(storage.get(isbn));
    }

    @Override
    public List<Recurso> buscarTodos() {
        return new ArrayList<>(storage.values());
    }

    public List<Recurso> buscarPorTitulo(String titulo) {
        return storage.values().stream()
                .filter(recurso -> recurso.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    public List<Recurso> buscarPorAutor(String autor) {
        return storage.values().stream()
                .filter(recurso -> recurso.autor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }

    public List<Recurso> buscarPorCategoria(String categoria) {
        return storage.values().stream()
                .filter(recurso -> recurso.categoria().toLowerCase().contains(categoria.toLowerCase()))
                .toList();
    }

    public List<Recurso> buscarPorAno(Integer ano) {
        return storage.values().stream()
                .filter(recurso -> recurso.ano() == ano)
                .toList();
    }
}
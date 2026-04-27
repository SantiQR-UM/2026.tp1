package com.bibliotech.repository;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.bibliotech.model.Socio;

public class SocioRepository implements Repository<Socio, Integer> {

    private final Map<Integer, Socio> storage = new HashMap<>();

    @Override
    public void guardar(Socio entidad) {
        storage.put(entidad.socioId(), entidad);
    }

    @Override
    public Optional<Socio> buscarPorId(Integer socioId) {
        return Optional.ofNullable(storage.get(socioId));
    }

    @Override
    public List<Socio> buscarTodos() {
        return new ArrayList<>(storage.values());
    }

    public List<Socio> buscarPorNombre(String nombre) {
        return storage.values().stream()
                .filter(socio -> socio.nombre().equalsIgnoreCase(nombre))
                .toList();
    }

    public Optional<Socio> buscarDni(int dni) {
        return storage.values().stream()
                .filter(socio -> socio.dni() == dni)
                .findFirst();
    }

    public Optional<Socio> buscarEmail(String email) {
        return storage.values().stream()
                .filter(socio -> socio.email().equalsIgnoreCase(email))
                .findFirst();
    }
}
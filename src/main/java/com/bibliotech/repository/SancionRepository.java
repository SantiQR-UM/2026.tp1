package com.bibliotech.repository;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDate;

import com.bibliotech.model.Sancion;

public class SancionRepository implements Repository<Sancion, Integer> {

    private final Map<Integer, Sancion> storage = new HashMap<>();

    @Override
    public void guardar(Sancion entidad) {
        storage.put(entidad.sancionId(), entidad);
    }

    @Override
    public Optional<Sancion> buscarPorId(Integer sancionId) {
        return Optional.ofNullable(storage.get(sancionId));
    }

    @Override
    public List<Sancion> buscarTodos() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Sancion> buscarSancionActivaPorSocio(int socioId) {
        return storage.values().stream()
                .filter(sancion -> sancion.socioId() == socioId
                        && sancion.fechaFin().isAfter(LocalDate.now()))
                .findFirst();
    }

    public List<Sancion> buscarPorSocio(int socioId) {
        return storage.values().stream()
                .filter(sancion -> sancion.socioId() == socioId)
                .toList();
    }

    public List<Sancion> buscarSancionesVencidas() {
        return storage.values().stream()
                .filter(sancion -> sancion.fechaFin().isBefore(LocalDate.now()))
                .toList();
    }
}

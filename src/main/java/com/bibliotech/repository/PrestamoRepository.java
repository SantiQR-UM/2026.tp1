package com.bibliotech.repository;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDate;

import com.bibliotech.model.Prestamo;
import com.bibliotech.model.EstadoPrestamo;

public class PrestamoRepository implements Repository<Prestamo, Integer> {

    private final Map<Integer, Prestamo> storage = new HashMap<>();

    @Override
    public void guardar(Prestamo entidad) {
        storage.put(entidad.prestamoId(), entidad);
    }

    @Override
    public Optional<Prestamo> buscarPorId(Integer prestamoId) {
        return Optional.ofNullable(storage.get(prestamoId));
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return new ArrayList<>(storage.values());
    }

    public List<Prestamo> buscarPrestamosActivosPorSocio(int socioId) {
        return storage.values().stream()
                .filter(prestamo -> prestamo.socioId() == socioId && prestamo.estado() == EstadoPrestamo.ACTIVO)
                .toList();
    }

    public List<Prestamo> buscarPrestamosActivosPorRecurso(String isbn) {
        return storage.values().stream()
                .filter(prestamo -> prestamo.isbn().equalsIgnoreCase(isbn) && prestamo.estado() == EstadoPrestamo.ACTIVO)
                .toList();
    }

    public List<Prestamo> buscarActivosConFechaVencida() {
        return storage.values().stream()
                .filter(prestamo -> prestamo.estado() == EstadoPrestamo.ACTIVO
                        && prestamo.fechaDevolucionPrevista().isBefore(LocalDate.now()))
                .toList();
    }

    public List<Prestamo> buscarPorEstado(EstadoPrestamo estado) {
        return storage.values().stream()
                .filter(prestamo -> prestamo.estado() == estado)
                .toList();
    }
}
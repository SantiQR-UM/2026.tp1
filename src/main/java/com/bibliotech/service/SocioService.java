package com.bibliotech.service;

import java.util.List;
import java.util.Optional;

import com.bibliotech.model.Socio;
import com.bibliotech.model.Prestamo;
import com.bibliotech.repository.Repository;
import com.bibliotech.repository.SocioRepository;
import com.bibliotech.repository.PrestamoRepository;
import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.DniDuplicadoException;
import com.bibliotech.exception.SocioNoEncontradoException;

public class SocioService {

    private final Repository<Socio, Integer> socioRepo;
    private final Repository<Prestamo, Integer> prestamoRepo;

    public SocioService(Repository<Socio, Integer> socioRepo, Repository<Prestamo, Integer> prestamoRepo) {
        this.socioRepo = socioRepo;
        this.prestamoRepo = prestamoRepo;
    }

    public void registrarSocio(Socio socio) throws BibliotecaException {
        Optional<Socio> existente = ((SocioRepository)socioRepo).buscarDni(socio.dni());
        if (existente.isPresent()) {
            throw new DniDuplicadoException("Ya existe un socio con DNI: " + socio.dni());
        }
        socioRepo.guardar(socio);
    }

    public Optional<Socio> buscarPorId(int socioId) {
        return socioRepo.buscarPorId(socioId);
    }

    public Optional<Socio> buscarPorDni(int dni) {
        return ((SocioRepository)socioRepo).buscarDni(dni);
    }

    public Optional<Socio> buscarPorNombre(String nombre) {
        return ((SocioRepository)socioRepo).buscarNombre(nombre);
    }

    public List<Socio> buscarTodos() {
        return socioRepo.buscarTodos();
    }

    public int contarPrestamosActivos(int socioId) {
        List<Prestamo> prestamosActivos = ((PrestamoRepository)prestamoRepo).buscarPrestamosActivosPorSocio(socioId);
        return prestamosActivos.size();
    }
}

package com.bibliotech.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Sancion;
import com.bibliotech.model.EstadoPrestamo;
import com.bibliotech.repository.*;
import com.bibliotech.exception.*;

public class PrestamoService {

    private final Repository<Recurso, String> recursoRepo;
    private final Repository<Socio, Integer> socioRepo;
    private final Repository<Prestamo, Integer> prestamoRepo;
    private final Repository<Sancion, Integer> sancionRepo;

    private int contadorPrestamos = 1;
    private int contadorSanciones = 1;

    public PrestamoService(
            Repository<Recurso, String> recursoRepo,
            Repository<Socio, Integer> socioRepo,
            Repository<Prestamo, Integer> prestamoRepo,
            Repository<Sancion, Integer> sancionRepo) {
        this.recursoRepo = recursoRepo;
        this.socioRepo = socioRepo;
        this.prestamoRepo = prestamoRepo;
        this.sancionRepo = sancionRepo;
    }

    public void actualizarEstadosPrestamos() {
        List<Prestamo> vencidos = ((PrestamoRepository)prestamoRepo).buscarActivosConFechaVencida();
        for (Prestamo prestamo : vencidos) {
            Prestamo actualizado = new Prestamo(
                prestamo.prestamoId(),
                prestamo.isbn(),
                prestamo.socioId(),
                prestamo.fechaPrestamo(),
                prestamo.fechaDevolucionPrevista(),
                prestamo.fechaDevolucionReal(),
                EstadoPrestamo.VENCIDO
            );
            prestamoRepo.guardar(actualizado);
        }
    }

    public void realizarPrestamo(String isbn, int socioId) throws BibliotecaException {
        // 1. Actualizar estados vencidos
        actualizarEstadosPrestamos();

        // 2. Verificar que el recurso existe
        Recurso recurso = recursoRepo.buscarPorId(isbn)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe recurso con ISBN: " + isbn));

        // 3. Verificar que el recurso es prestable (no es ebook)
        if (!recurso.esPrestable()) {
            throw new RecursoNoPrestableException("Solo se pueden prestar libros físicos. Los ebooks no son prestables.");
        }

        // 4. Verificar que está disponible (no prestado)
        List<Prestamo> prestamosActivos = ((PrestamoRepository)prestamoRepo).buscarPrestamosActivosPorRecurso(isbn);
        if (!prestamosActivos.isEmpty()) {
            throw new LibroNoDisponibleException("El libro con ISBN " + isbn + " ya está prestado");
        }

        // 5. Verificar que el socio existe
        Socio socio = socioRepo.buscarPorId(socioId)
            .orElseThrow(() -> new SocioNoEncontradoException("No existe socio con ID: " + socioId));

        // 6. Verificar que no tiene sanciones activas
        Optional<Sancion> sancion = ((SancionRepository)sancionRepo).buscarSancionActivaPorSocio(socioId);
        if (sancion.isPresent()) {
            throw new SocioSancionadoException(
                "El socio está sancionado hasta " + sancion.get().fechaFin() + ". Motivo: " + sancion.get().motivo()
            );
        }

        // 7. Verificar límite de préstamos
        List<Prestamo> prestamosSocio = ((PrestamoRepository)prestamoRepo).buscarPrestamosActivosPorSocio(socioId);
        if (prestamosSocio.size() >= socio.prestamosMax()) {
            throw new LimitePrestamosExcedidoException(
                "El socio alcanzó el límite de " + socio.prestamosMax() + " préstamos activos"
            );
        }

        // 8. Crear el préstamo
        Prestamo nuevoPrestamo = new Prestamo(
            contadorPrestamos++,
            isbn,
            socioId,
            LocalDate.now(),
            LocalDate.now().plusDays(14),  // 14 días para devolver
            null,  // aún no se devolvió
            EstadoPrestamo.ACTIVO
        );
        prestamoRepo.guardar(nuevoPrestamo);
    }

    public void devolverPrestamo(int prestamoId) throws BibliotecaException {
        // 1. Buscar el préstamo
        Prestamo prestamo = prestamoRepo.buscarPorId(prestamoId)
            .orElseThrow(() -> new PrestamoNoEncontradoException("No existe préstamo con ID: " + prestamoId));

        // 2. Verificar que está activo o vencido (no ya devuelto)
        if (prestamo.estado() == EstadoPrestamo.DEVUELTO) {
            throw new BibliotecaException("El préstamo con ID " + prestamoId + " ya fue devuelto anteriormente");
        }

        // 3. Calcular días de retraso
        LocalDate hoy = LocalDate.now();
        long diasRetraso = ChronoUnit.DAYS.between(prestamo.fechaDevolucionPrevista(), hoy);

        // 4. Si hay retraso, crear sanción
        if (diasRetraso > 0) {
            int diasSancion = (int) diasRetraso;  // 1 día de sanción por cada día de retraso
            Sancion nuevaSancion = new Sancion(
                contadorSanciones++,
                prestamo.socioId(),
                hoy,
                hoy.plusDays(diasSancion),
                "Devolución tardía de " + diasRetraso + " día(s)"
            );
            sancionRepo.guardar(nuevaSancion);
        }

        // 5. Marcar préstamo como devuelto
        Prestamo actualizado = new Prestamo(
            prestamo.prestamoId(),
            prestamo.isbn(),
            prestamo.socioId(),
            prestamo.fechaPrestamo(),
            prestamo.fechaDevolucionPrevista(),
            hoy,  // Fecha de devolución real
            EstadoPrestamo.DEVUELTO
        );
        prestamoRepo.guardar(actualizado);
    }

    public List<Prestamo> consultarPrestamosActivos(int socioId) {
        return ((PrestamoRepository)prestamoRepo).buscarPrestamosActivosPorSocio(socioId);
    }

    public List<Prestamo> consultarTodosPrestamos() {
        return prestamoRepo.buscarTodos();
    }

    public long calcularDiasRetraso(Prestamo prestamo) {
        if (prestamo.estado() == EstadoPrestamo.DEVUELTO && prestamo.fechaDevolucionReal() != null) {
            return ChronoUnit.DAYS.between(prestamo.fechaDevolucionPrevista(), prestamo.fechaDevolucionReal());
        } else if (prestamo.estado() == EstadoPrestamo.ACTIVO || prestamo.estado() == EstadoPrestamo.VENCIDO) {
            return ChronoUnit.DAYS.between(prestamo.fechaDevolucionPrevista(), LocalDate.now());
        }
        return 0;
    }

    public List<Sancion> consultarSancionesSocio(int socioId) {
        return ((SancionRepository)sancionRepo).buscarPorSocio(socioId);
    }

    public boolean tieneSancionActiva(int socioId) {
        return ((SancionRepository)sancionRepo).buscarSancionActivaPorSocio(socioId).isPresent();
    }
}

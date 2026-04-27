package com.bibliotech;

import com.bibliotech.model.*;
import com.bibliotech.repository.*;
import com.bibliotech.service.*;
import com.bibliotech.exception.*;

import java.util.Scanner;
import java.util.List;
import java.util.Optional;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    // Repositorios
    private static RecursoRepository recursoRepo = new RecursoRepository();
    private static SocioRepository socioRepo = new SocioRepository();
    private static PrestamoRepository prestamoRepo = new PrestamoRepository();
    private static SancionRepository sancionRepo = new SancionRepository();

    // Services
    private static RecursoService recursoService;
    private static SocioService socioService;
    private static PrestamoService prestamoService;

    public static void main(String[] args) {
        // Inicializar services con inyección de dependencias
        recursoService = new RecursoService(recursoRepo, prestamoRepo);
        socioService = new SocioService(socioRepo, prestamoRepo);
        prestamoService = new PrestamoService(recursoRepo, socioRepo, prestamoRepo, sancionRepo);

        // Cargar datos de ejemplo (opcional)
        cargarDatosEjemplo();

        // Menú principal
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> menuRecursos();
                case "2" -> menuSocios();
                case "3" -> menuPrestamos();
                case "4" -> menuConsultas();
                case "0" -> {
                    System.out.println("\n¡Hasta luego!");
                    salir = true;
                }
                default -> System.out.println("Opción inválida");
            }
        }

        scanner.close();
    }

    // ========== MENÚS ==========

    private static void mostrarMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       BIBLIOTECH - Sistema de          ║");
        System.out.println("║      Gestión de Biblioteca             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Gestión de Recursos (Libros/Ebooks)");
        System.out.println("2. Gestión de Socios");
        System.out.println("3. Gestión de Préstamos");
        System.out.println("4. Consultas e Informes");
        System.out.println("0. Salir");
        System.out.print("\nSeleccione una opción: ");
    }

    private static void menuRecursos() {
        System.out.println("\n--- GESTIÓN DE RECURSOS ---");
        System.out.println("1. Registrar Libro Físico");
        System.out.println("2. Registrar Ebook");
        System.out.println("3. Buscar por ISBN");
        System.out.println("4. Buscar por Título");
        System.out.println("5. Buscar por Autor");
        System.out.println("6. Buscar por Categoría");
        System.out.println("7. Buscar por Año");
        System.out.println("8. Listar todos los recursos");
        System.out.println("0. Volver");
        System.out.print("\nSeleccione una opción: ");

        String opcion = scanner.nextLine().trim();

        switch (opcion) {
            case "1" -> registrarLibro();
            case "2" -> registrarEbook();
            case "3" -> buscarPorIsbn();
            case "4" -> buscarPorTitulo();
            case "5" -> buscarPorAutor();
            case "6" -> buscarPorCategoria();
            case "7" -> buscarPorAno();
            case "8" -> listarRecursos();
            case "0" -> {}
            default -> System.out.println("Opción inválida");
        }
    }

    private static void menuSocios() {
        System.out.println("\n--- GESTIÓN DE SOCIOS ---");
        System.out.println("1. Registrar Estudiante");
        System.out.println("2. Registrar Docente");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Buscar por DNI");
        System.out.println("5. Buscar por Nombre");
        System.out.println("6. Listar todos los socios");
        System.out.println("0. Volver");
        System.out.print("\nSeleccione una opción: ");

        String opcion = scanner.nextLine().trim();

        switch (opcion) {
            case "1" -> registrarEstudiante();
            case "2" -> registrarDocente();
            case "3" -> buscarSocioPorId();
            case "4" -> buscarSocioPorDni();
            case "5" -> buscarSocioPorNombre();
            case "6" -> listarSocios();
            case "0" -> {}
            default -> System.out.println("Opción inválida");
        }
    }

    private static void menuPrestamos() {
        System.out.println("\n--- GESTIÓN DE PRÉSTAMOS ---");
        System.out.println("1. Realizar Préstamo");
        System.out.println("2. Devolver Préstamo");
        System.out.println("3. Listar todos los préstamos");
        System.out.println("0. Volver");
        System.out.print("\nSeleccione una opción: ");

        String opcion = scanner.nextLine().trim();

        switch (opcion) {
            case "1" -> realizarPrestamo();
            case "2" -> devolverPrestamo();
            case "3" -> listarPrestamos();
            case "0" -> {}
            default -> System.out.println("Opción inválida");
        }
    }

    private static void menuConsultas() {
        System.out.println("\n--- CONSULTAS E INFORMES ---");
        System.out.println("1. Ver préstamos activos de un socio");
        System.out.println("2. Ver sanciones de un socio");
        System.out.println("3. Ver disponibilidad de un recurso");
        System.out.println("4. Listar préstamos vencidos");
        System.out.println("5. Listar todas las sanciones");
        System.out.println("0. Volver");
        System.out.print("\nSeleccione una opción: ");

        String opcion = scanner.nextLine().trim();

        switch (opcion) {
            case "1" -> consultarPrestamosActivosSocio();
            case "2" -> consultarSancionesSocio();
            case "3" -> consultarDisponibilidad();
            case "4" -> listarPrestamosVencidos();
            case "5" -> listarSanciones();
            case "0" -> {}
            default -> System.out.println("Opción inválida");
        }
    }

    // ========== FUNCIONES DE RECURSOS ==========

    private static void registrarLibro() {
        try {
            System.out.println("\n--- REGISTRAR LIBRO FÍSICO ---");
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Autor: ");
            String autor = scanner.nextLine();
            System.out.print("Páginas: ");
            int paginas = Integer.parseInt(scanner.nextLine());
            System.out.print("Año: ");
            int ano = Integer.parseInt(scanner.nextLine());
            System.out.print("Categoría: ");
            String categoria = scanner.nextLine();

            Libro libro = new Libro(isbn, titulo, autor, paginas, ano, categoria);
            recursoService.registrarRecurso(libro);
            System.out.println("✓ Libro registrado exitosamente");
        } catch (BibliotecaException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        } catch (Exception e) {
            System.out.println("✗ Error inesperado: " + e.getMessage());
        }
    }

    private static void registrarEbook() {
        try {
            System.out.println("\n--- REGISTRAR EBOOK ---");
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Autor: ");
            String autor = scanner.nextLine();
            System.out.print("Páginas: ");
            int paginas = Integer.parseInt(scanner.nextLine());
            System.out.print("Año: ");
            int ano = Integer.parseInt(scanner.nextLine());
            System.out.print("Categoría: ");
            String categoria = scanner.nextLine();

            Ebook ebook = new Ebook(isbn, titulo, autor, paginas, ano, categoria);
            recursoService.registrarRecurso(ebook);
            System.out.println("✓ Ebook registrado exitosamente");
        } catch (BibliotecaException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        } catch (Exception e) {
            System.out.println("✗ Error inesperado: " + e.getMessage());
        }
    }

    private static void buscarPorIsbn() {
        System.out.print("\nIngrese ISBN: ");
        String isbn = scanner.nextLine();

        Optional<Recurso> recurso = recursoService.buscarPorIsbn(isbn);
        if (recurso.isPresent()) {
            mostrarRecurso(recurso.get());
        } else {
            System.out.println("✗ No se encontró recurso con ese ISBN");
        }
    }

    private static void buscarPorTitulo() {
        System.out.print("\nIngrese título (o parte): ");
        String titulo = scanner.nextLine();

        List<Recurso> recursos = recursoService.buscarPorTitulo(titulo);
        if (recursos.isEmpty()) {
            System.out.println("✗ No se encontraron recursos");
        } else {
            System.out.println("\n--- Resultados (" + recursos.size() + ") ---");
            recursos.forEach(Main::mostrarRecurso);
        }
    }

    private static void buscarPorAutor() {
        System.out.print("\nIngrese autor: ");
        String autor = scanner.nextLine();

        List<Recurso> recursos = recursoService.buscarPorAutor(autor);
        if (recursos.isEmpty()) {
            System.out.println("✗ No se encontraron recursos");
        } else {
            System.out.println("\n--- Resultados (" + recursos.size() + ") ---");
            recursos.forEach(Main::mostrarRecurso);
        }
    }

    private static void buscarPorCategoria() {
        System.out.print("\nIngrese categoría: ");
        String categoria = scanner.nextLine();

        List<Recurso> recursos = recursoService.buscarPorCategoria(categoria);
        if (recursos.isEmpty()) {
            System.out.println("✗ No se encontraron recursos");
        } else {
            System.out.println("\n--- Resultados (" + recursos.size() + ") ---");
            recursos.forEach(Main::mostrarRecurso);
        }
    }

    private static void buscarPorAno() {
        System.out.print("\nIngrese año: ");
        try {
            int ano = Integer.parseInt(scanner.nextLine());
            List<Recurso> recursos = recursoService.buscarPorAno(ano);
            if (recursos.isEmpty()) {
                System.out.println("✗ No se encontraron recursos de ese año");
            } else {
                System.out.println("\n--- Resultados (" + recursos.size() + ") ---");
                recursos.forEach(Main::mostrarRecurso);
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        }
    }

    private static void listarRecursos() {
        List<Recurso> recursos = recursoRepo.buscarTodos();
        if (recursos.isEmpty()) {
            System.out.println("\n✗ No hay recursos registrados");
        } else {
            System.out.println("\n--- TODOS LOS RECURSOS (" + recursos.size() + ") ---");
            recursos.forEach(Main::mostrarRecurso);
        }
    }

    // ========== FUNCIONES DE SOCIOS ==========

    private static void registrarEstudiante() {
        try {
            System.out.println("\n--- REGISTRAR ESTUDIANTE ---");
            System.out.print("ID del socio: ");
            int socioId = Integer.parseInt(scanner.nextLine());
            System.out.print("DNI: ");
            int dni = Integer.parseInt(scanner.nextLine());
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            Estudiante estudiante = new Estudiante(socioId, dni, nombre, email);
            socioService.registrarSocio(estudiante);
            System.out.println("✓ Estudiante registrado exitosamente (límite: 3 préstamos)");
        } catch (BibliotecaException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        } catch (Exception e) {
            System.out.println("✗ Error inesperado: " + e.getMessage());
        }
    }

    private static void registrarDocente() {
        try {
            System.out.println("\n--- REGISTRAR DOCENTE ---");
            System.out.print("ID del socio: ");
            int socioId = Integer.parseInt(scanner.nextLine());
            System.out.print("DNI: ");
            int dni = Integer.parseInt(scanner.nextLine());
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            Docente docente = new Docente(socioId, dni, nombre, email);
            socioService.registrarSocio(docente);
            System.out.println("✓ Docente registrado exitosamente (límite: 5 préstamos)");
        } catch (BibliotecaException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        } catch (Exception e) {
            System.out.println("✗ Error inesperado: " + e.getMessage());
        }
    }

    private static void buscarSocioPorId() {
        System.out.print("\nIngrese ID del socio: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Optional<Socio> socio = socioService.buscarPorId(id);
            if (socio.isPresent()) {
                mostrarSocio(socio.get());
            } else {
                System.out.println("✗ No se encontró socio con ese ID");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        }
    }

    private static void buscarSocioPorDni() {
        System.out.print("\nIngrese DNI: ");
        try {
            int dni = Integer.parseInt(scanner.nextLine());
            Optional<Socio> socio = socioService.buscarPorDni(dni);
            if (socio.isPresent()) {
                mostrarSocio(socio.get());
            } else {
                System.out.println("✗ No se encontró socio con ese DNI");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        }
    }

    private static void buscarSocioPorNombre() {
        System.out.print("\nIngrese nombre (o parte): ");
        String nombre = scanner.nextLine();
        List<Socio> socios = socioService.buscarPorNombre(nombre);
        if (socios.isEmpty()) {
            System.out.println("✗ No se encontraron socios con ese nombre");
        } else {
            System.out.println("\n--- Resultados (" + socios.size() + ") ---");
            socios.forEach(Main::mostrarSocio);
        }
    }

    private static void listarSocios() {
        List<Socio> socios = socioRepo.buscarTodos();
        if (socios.isEmpty()) {
            System.out.println("\n✗ No hay socios registrados");
        } else {
            System.out.println("\n--- TODOS LOS SOCIOS (" + socios.size() + ") ---");
            socios.forEach(Main::mostrarSocio);
        }
    }

    // ========== FUNCIONES DE PRÉSTAMOS ==========

    private static void realizarPrestamo() {
        try {
            System.out.println("\n--- REALIZAR PRÉSTAMO ---");
            System.out.print("ISBN del recurso: ");
            String isbn = scanner.nextLine();
            System.out.print("ID del socio: ");
            int socioId = Integer.parseInt(scanner.nextLine());

            prestamoService.realizarPrestamo(isbn, socioId);
            System.out.println("✓ Préstamo realizado exitosamente (14 días de plazo)");
        } catch (BibliotecaException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        }
    }

    private static void devolverPrestamo() {
        try {
            System.out.println("\n--- DEVOLVER PRÉSTAMO ---");
            System.out.print("ID del préstamo: ");
            int prestamoId = Integer.parseInt(scanner.nextLine());

            prestamoService.devolverPrestamo(prestamoId);
            System.out.println("✓ Devolución registrada exitosamente");
        } catch (BibliotecaException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        }
    }

    private static void listarPrestamos() {
        List<Prestamo> prestamos = prestamoRepo.buscarTodos();
        if (prestamos.isEmpty()) {
            System.out.println("\n✗ No hay préstamos registrados");
        } else {
            System.out.println("\n--- TODOS LOS PRÉSTAMOS (" + prestamos.size() + ") ---");
            prestamos.forEach(Main::mostrarPrestamo);
        }
    }

    // ========== FUNCIONES DE CONSULTAS ==========

    private static void consultarPrestamosActivosSocio() {
        System.out.print("\nIngrese ID del socio: ");
        try {
            int socioId = Integer.parseInt(scanner.nextLine());
            List<Prestamo> prestamos = prestamoService.consultarPrestamosActivos(socioId);

            if (prestamos.isEmpty()) {
                System.out.println("✓ El socio no tiene préstamos activos");
            } else {
                System.out.println("\n--- Préstamos activos (" + prestamos.size() + ") ---");
                prestamos.forEach(Main::mostrarPrestamo);
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        }
    }

    private static void consultarSancionesSocio() {
        System.out.print("\nIngrese ID del socio: ");
        try {
            int socioId = Integer.parseInt(scanner.nextLine());
            List<Sancion> sanciones = prestamoService.consultarSancionesSocio(socioId);

            if (sanciones.isEmpty()) {
                System.out.println("✓ El socio no tiene sanciones");
            } else {
                System.out.println("\n--- Sanciones (" + sanciones.size() + ") ---");
                sanciones.forEach(Main::mostrarSancion);

                if (prestamoService.tieneSancionActiva(socioId)) {
                    System.out.println("\n⚠ El socio tiene sanción ACTIVA y no puede solicitar préstamos");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Debe ingresar un número válido");
        }
    }

    private static void consultarDisponibilidad() {
        System.out.print("\nIngrese ISBN del recurso: ");
        String isbn = scanner.nextLine();

        Optional<Recurso> recurso = recursoService.buscarPorIsbn(isbn);
        if (recurso.isEmpty()) {
            System.out.println("✗ No existe recurso con ese ISBN");
            return;
        }

        mostrarRecurso(recurso.get());

        if (!recurso.get().esPrestable()) {
            System.out.println("⚠ Este recurso es un Ebook (NO se puede prestar)");
        } else {
            boolean disponible = recursoService.estaDisponible(isbn);
            if (disponible) {
                System.out.println("✓ DISPONIBLE para préstamo");
            } else {
                System.out.println("✗ NO DISPONIBLE (ya está prestado)");
            }
        }
    }

    private static void listarPrestamosVencidos() {
        List<Prestamo> vencidos = prestamoRepo.buscarActivosConFechaVencida();
        if (vencidos.isEmpty()) {
            System.out.println("\n✓ No hay préstamos vencidos");
        } else {
            System.out.println("\n⚠ PRÉSTAMOS VENCIDOS (" + vencidos.size() + ") ---");
            vencidos.forEach(prestamo -> {
                mostrarPrestamo(prestamo);
                long dias = prestamoService.calcularDiasRetraso(prestamo);
                System.out.println("   └─> Días de retraso: " + dias);
            });
        }
    }

    private static void listarSanciones() {
        List<Sancion> sanciones = sancionRepo.buscarTodos();
        if (sanciones.isEmpty()) {
            System.out.println("\n✓ No hay sanciones registradas");
        } else {
            System.out.println("\n--- TODAS LAS SANCIONES (" + sanciones.size() + ") ---");
            sanciones.forEach(Main::mostrarSancion);
        }
    }

    // ========== FUNCIONES DE VISUALIZACIÓN ==========

    private static void mostrarRecurso(Recurso r) {
        String tipo = r.getClass().getSimpleName();
        String prestable = r.esPrestable() ? "SÍ" : "NO";
        System.out.println("\n[" + tipo + "] " + r.titulo());
        System.out.println("  ISBN: " + r.isbn());
        System.out.println("  Autor: " + r.autor());
        System.out.println("  Año: " + r.ano() + " | Páginas: " + r.paginas());
        System.out.println("  Categoría: " + r.categoria());
        System.out.println("  Prestable: " + prestable);
    }

    private static void mostrarSocio(Socio s) {
        String tipo = s.getClass().getSimpleName();
        System.out.println("\n[" + tipo + "] " + s.nombre());
        System.out.println("  ID: " + s.socioId() + " | DNI: " + s.dni());
        System.out.println("  Email: " + s.email());
        System.out.println("  Límite de préstamos: " + s.prestamosMax());
        int activos = socioService.contarPrestamosActivos(s.socioId());
        System.out.println("  Préstamos activos: " + activos + "/" + s.prestamosMax());
    }

    private static void mostrarPrestamo(Prestamo p) {
        System.out.println("\n[Préstamo #" + p.prestamoId() + "] Estado: " + p.estado());
        System.out.println("  ISBN: " + p.isbn() + " | Socio ID: " + p.socioId());
        System.out.println("  Préstamo: " + p.fechaPrestamo());
        System.out.println("  Devolución prevista: " + p.fechaDevolucionPrevista());
        if (p.fechaDevolucionReal() != null) {
            System.out.println("  Devolución real: " + p.fechaDevolucionReal());
        }
    }

    private static void mostrarSancion(Sancion s) {
        System.out.println("\n[Sanción #" + s.sancionId() + "] Socio: " + s.socioId());
        System.out.println("  Motivo: " + s.motivo());
        System.out.println("  Inicio: " + s.fechaInicio() + " | Fin: " + s.fechaFin());
    }

    // ========== DATOS DE EJEMPLO ==========

    private static void cargarDatosEjemplo() {
        try {
            // Libros
            recursoService.registrarRecurso(new Libro("978-3-16-148410-0", "Cien años de soledad", "Gabriel García Márquez", 471, 1967, "Ficción"));
            recursoService.registrarRecurso(new Libro("978-0-06-112008-4", "1984", "George Orwell", 328, 1949, "Ficción"));
            recursoService.registrarRecurso(new Libro("978-0-13-468599-1", "Clean Code", "Robert C. Martin", 464, 2008, "Programación"));

            // Ebooks
            recursoService.registrarRecurso(new Ebook("978-0-596-52068-7", "JavaScript: The Good Parts", "Douglas Crockford", 176, 2008, "Programación"));
            recursoService.registrarRecurso(new Ebook("978-0-321-56384-2", "Effective Java", "Joshua Bloch", 416, 2017, "Programación"));

            // Socios
            socioService.registrarSocio(new Estudiante(1, 12345678, "Juan Pérez", "juan@email.com"));
            socioService.registrarSocio(new Estudiante(2, 87654321, "María García", "maria@email.com"));
            socioService.registrarSocio(new Docente(3, 11223344, "Carlos López", "carlos@email.com"));

            System.out.println("✓ Datos de ejemplo cargados");
        } catch (Exception e) {
            // Silenciar errores de datos de ejemplo
        }
    }
}

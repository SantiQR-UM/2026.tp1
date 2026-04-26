package com.bibliotech.exception;

public class LibroNoDisponibleException extends BibliotecaException {

    public LibroNoDisponibleException() {
        super("Este libro físico ya fue prestado o no está disponible");
    }
    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
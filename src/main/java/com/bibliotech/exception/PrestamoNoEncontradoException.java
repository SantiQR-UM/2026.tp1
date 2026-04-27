package com.bibliotech.exception;

public class PrestamoNoEncontradoException extends BibliotecaException {

    public PrestamoNoEncontradoException() {
        super("Préstamo no encontrado");
    }
    public PrestamoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

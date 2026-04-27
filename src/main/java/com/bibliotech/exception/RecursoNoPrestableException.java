package com.bibliotech.exception;

public class RecursoNoPrestableException extends BibliotecaException {

    public RecursoNoPrestableException() {
        super("El recurso no se puede prestar");
    }
    public RecursoNoPrestableException(String mensaje) {
        super(mensaje);
    }
}

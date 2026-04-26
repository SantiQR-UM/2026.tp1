package com.bibliotech.exception;

public class RecursoNoEncontradoException extends BibliotecaException {

    public RecursoNoEncontradoException() {
        super("Este recurso no existe");
    }
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
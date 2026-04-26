package com.bibliotech.exception;

public class IsbnDuplicadoException extends BibliotecaException {

    public IsbnDuplicadoException() {
        super("Este ISBN ya existe");
    }
    public IsbnDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
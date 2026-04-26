package com.bibliotech.exception;

public class DatoInvalidoException extends BibliotecaException {

    public DatoInvalidoException() {
        super("Datos inválidos");
    }
    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
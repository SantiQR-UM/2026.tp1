package com.bibliotech.exception;

public class DniDuplicadoException extends BibliotecaException {

    public DniDuplicadoException() {
        super("Este DNI ya existe");
    }
    public DniDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
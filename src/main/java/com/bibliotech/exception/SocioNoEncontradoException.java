package com.bibliotech.exception;

public class SocioNoEncontradoException extends BibliotecaException {

    public SocioNoEncontradoException() {
        super("Este socio no existe");
    }
    public SocioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
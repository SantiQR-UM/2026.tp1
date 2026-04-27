package com.bibliotech.exception;

public class SocioSancionadoException extends BibliotecaException {

    public SocioSancionadoException() {
        super("El socio está sancionado y no puede solicitar préstamos");
    }
    public SocioSancionadoException(String mensaje) {
        super(mensaje);
    }
}

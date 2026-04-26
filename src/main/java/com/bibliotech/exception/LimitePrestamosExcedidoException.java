package com.bibliotech.exception;

public class LimitePrestamosExcedidoException extends BibliotecaException {

    public LimitePrestamosExcedidoException() {
        super("Ya excediste el límite de préstamos");
    }
    public LimitePrestamosExcedidoException(String mensaje) {
        super(mensaje);
    }
}
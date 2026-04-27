package com.bibliotech.exception;

public class BibliotecaException extends RuntimeException {

    // Constructor que recibe un mensaje
    public BibliotecaException(String mensaje) {
        super(mensaje);  // llama al constructor del padre
    }
}
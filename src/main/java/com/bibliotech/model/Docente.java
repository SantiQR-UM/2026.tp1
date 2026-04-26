package com.bibliotech.model;

import com.bibliotech.exception.DatoInvalidoException;

public record Docente(int socioId, int dni, String nombre, String email) implements Socio {

    public Docente {
        if (nombre == null || nombre.isEmpty()) {throw new DatoInvalidoException("El nombre no puede estar vacío");}
        if (dni < 1000000 || dni > 99999999) {throw new DatoInvalidoException("El DNI debe tener entre 7 y 8 dígitos y ser positivo, recibido: " + dni);}
        if (email == null || email.isEmpty()) {throw new DatoInvalidoException("El email no puede estar vacío");}
        if (!email.contains("@") || !email.contains(".") || email.indexOf("@") > email.lastIndexOf(".")) {throw new DatoInvalidoException("El email debe contener '@' y un dominio válido");}
    }

    @Override
    public int prestamosMax () {
        return 5;
    }
}
package com.bibliotech.model;

import com.bibliotech.exception.DatoInvalidoException;

public record Ebook(String isbn, String titulo, String autor, int paginas, int ano, String categoria) implements Recurso {

    public Ebook {
        if (titulo == null || titulo.isEmpty()) {throw new DatoInvalidoException("El titulo no puede estar vacío");}
        if (autor == null || autor.isEmpty()) {throw new DatoInvalidoException("El autor no puede estar vacío");}
        if (paginas < 1) {throw new DatoInvalidoException("La cantidad de páginas debe superar 1");}
        if (ano < 0 || ano > 2026) {throw new DatoInvalidoException("El año es incorrecto, debe estar entre 0 y 2026");}
        if (categoria == null || categoria.isEmpty()) {throw new DatoInvalidoException("La categoría no puede estar vacía");}
    }

    @Override
    public boolean esPrestable() {
        return false;
    }

}
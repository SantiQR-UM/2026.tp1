package com.bibliotech.model

public record Ebook(String isbn, String titulo, String autor, int paginas, int ano, String categoria) implements Recurso {}
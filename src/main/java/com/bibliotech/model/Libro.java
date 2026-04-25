// src/com/bibliotech/model/Libro.java
public record Libro(String isbn, String titulo, String autor, int paginas, int ano, String categoria) implements Recurso {}
// src/com/bibliotech/model/Recurso.java
public interface Recurso {
    String isbn();
    String titulo();
}

// src/com/bibliotech/model/Libro.java
public record Libro(String isbn, String titulo, String autor, int paginas) implements Recurso {}
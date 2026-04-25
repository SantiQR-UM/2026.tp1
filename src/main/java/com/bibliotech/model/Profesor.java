public record Estudiante(int socioId(), int dni(), String nombre(), String email()) implements Socio {}

@Override
public int prestamosMax() {
    return 5;
}
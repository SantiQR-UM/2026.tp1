# Decisiones de Diseño — BiblioTech

## Arquitectura en capas

El proyecto se divide en cuatro capas: `model`, `repository`, `service` y `Main`. Esta separación permite que cada capa cambie de forma independiente: si mañana se reemplaza el almacenamiento en memoria por una base de datos, solo se tocan los repositorios; si cambia la lógica de negocio, solo los servicios.

## Interfaces `Recurso` y `Socio`

Tanto los recursos (`Libro`, `Ebook`) como los socios (`Estudiante`, `Docente`) se modelaron como implementaciones de una interfaz común. Esto evita tener que preguntar por el tipo concreto con `instanceof` en la lógica de negocio: `esPrestable()` y `prestamosMax()` se invocan polimórficamente, y agregar un nuevo tipo (ej. `Revista`, `Investigador`) no requiere tocar el código existente.

## Records para entidades

`Libro`, `Ebook`, `Estudiante`, `Docente`, `Prestamo` y `Sancion` son `record`. La razón es que son datos puros sin comportamiento mutable: un préstamo no cambia de ISBN ni de fecha de inicio. Usar `record` garantiza inmutabilidad, genera `equals`/`hashCode`/`toString` automáticamente y permite validar los datos en el constructor compacto, fallando rápido ante entradas inválidas en lugar de propagar un estado corrupto.

## Repositorio genérico `Repository<T, ID>`

La interfaz genérica define solo tres operaciones (`guardar`, `buscarPorId`, `buscarTodos`). Cada repositorio concreto la implementa y agrega sus propias consultas (`buscarPorTitulo`, `buscarActivosConFechaVencida`, etc.). Los servicios reciben la interfaz genérica por constructor, lo que los desacopla de la implementación concreta y facilita el reemplazo de almacenamiento.

## Inyección de dependencias por constructor

Los servicios declaran sus repositorios como campos `final` y los reciben en el constructor. Esto hace explícitas las dependencias, impide que un servicio quede en estado incompleto.

## `Optional` en lugar de `null`

Todos los métodos de búsqueda que pueden no encontrar resultados devuelven `Optional<T>`. Esto obliga a quien llama a manejar el caso vacío de forma explícita (`orElseThrow`, `ifPresent`) y elimina los `NullPointerException` silenciosos.

## Jerarquía de excepciones con base `RuntimeException`

`BibliotecaException` extiende `RuntimeException` para no forzar cláusulas `throws` en cada firma de método, lo que simplifica el código de los servicios. Cada situación de error tiene su propia subclase (`LibroNoDisponibleException`, `SocioSancionadoException`, etc.) para que el CLI pueda mostrar mensajes precisos y, a futuro, un test pueda verificar exactamente qué excepción se lanzó.

## Almacenamiento en `HashMap`

Se eligió `HashMap` como implementación de todos los repositorios porque satisface los requisitos del trabajo práctico sin introducir dependencias externas. El ISBN (String) y el socioId/prestamoId (Integer) funcionan como claves naturales.

## Inmutabilidad de `Prestamo` al cambiar estado

`Prestamo` es un `record` inmutable, por lo que cambiar su estado (de `ACTIVO` a `VENCIDO` o `DEVUELTO`) implica crear una nueva instancia con el mismo `prestamoId` y guardarla, reemplazando la anterior en el `HashMap`. Se eligió este enfoque para mantener la consistencia del modelo inmutable sin introducir mutabilidad en las entidades.

## `esPrestable()` en la interfaz en lugar de `instanceof`

La decisión de distinguir entre `Libro` y `Ebook` mediante un método polimórfico en lugar de una verificación de tipo evita acoplar `PrestamoService` a las clases concretas. Si en el futuro existe un `LibroRaro` que tampoco se presta, solo hay que implementar `esPrestable()` devolviendo `false`, sin cambiar el servicio.

## Sanción proporcional al retraso

Cada día de retraso genera un día de sanción (`diasSancion = diasRetraso`). Parece una política justa para el daño causado . La sanción se crea al momento de la devolución, no al vencer el plazo, porque es el momento en que se conoce la fecha real de devolución y porque así lo pide la consigna.

## Cast a repositorios concretos en `PrestamoService`

`PrestamoService` recibe `Repository<Prestamo, Integer>` por constructor pero lo castea a `PrestamoRepository` para acceder a métodos como `buscarPrestamosActivosPorSocio`. Lo que evita duplicar la interfaz genérica solo para añadir un par de consultas especializadas.

## Período de préstamo hardcodeado (14 días)

El plazo de devolución está fijo en 14 días porque es el valor especificado en el enunciado.

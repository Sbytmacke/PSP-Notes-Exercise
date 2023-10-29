# A-Simulación Planificación de Procesos

## Datos alumno

- Nombre: Angel Maroto Chivite
- Email Luis Vives: angel.maroto@alumno.iesluisvives.org
- GitHub Username: Sbytmacke

## Resolución

- Se ha participado en la ampliación del ejercicio eliminando la `inanición` de los procesos y añadiendo una simulación
  ficticia para los `bloqueos de entrada/salida`.

- Se ha resuelto todo el ejercicio aplicando patrones de diseño como: `Factory` y `Fachade`.

- Se ha intentado aplicar en lo posible `Clean Code` y reutilizar código.

### ✅ Enunciado obligatorio:

    1. Realizar una clase genérica Cola <T> que tenga las operaciones básicas de una cola (añadir, extraer, estaVacia,..)
    2. Realizar una clase genérica ColaPrioridades<T> que, basándose en la clase Cola<T> anterior gestione una cola de prioridades.
        En el constructor se definirá el número de prioridades disponibles y se crearán tantas Colas<T> internas como prioridades disponibles.
        Se recomienda utilizar un array o vector de colas.

    [x] - Cuando se añada un elemento se indicará la prioridad : anadir (T obj, int prioridad) y si la prioridad existe se añadirá el objeto a la cola asociada a esa prioridad.
    [x] - Cuando se extraiga un elemento (extraer )se devolverá el que está al principio de la cola de más alta prioridad que tenga elementos. Se supone que números altos indican baja prioridad y números bajos (0) indican alta prioridad.

    3. Realizar una aplicación Planificador para simular la gestión de procesos  que:

    [x] - Lea los datos de los procesos de un fichero de texto csv. La primera línea tendrá un número entero el tamaño de la rodaja de tiempo para la planificación con Round Robin.
        Las demás líneas tendrán, separadas por ;, los siguientes datos de los procesos: identificador, tiempo de ejecución, tiempo de entrada(acceso al sistema) y prioridad.
    [x] - La aplicación leerá el fichero y creará tantos procesos como líneas de procesos haya en el mismo y simulará una ejecución con Round Robin de los mismos utilizando la ColaPrioridades.
        Se mostrará en pantalla, el identificador del proceso que está ejecutando en cada momento, indicando en qué momento empezó esa parte de esa ejecución y cuando la finalizará.
        Además, indicará FIN seguido del identificador del proceso cuando finalice la ejecución total del proceso.
    [x] - Un proceso en ejecución debe salir de la cola de prioridades y volver a entrar, al final de la misma, cuando finalice su ejecución temporal, que durará lo que la rodaja de tiempo especificada.
    [x] - Cuando finalice la ejecución de todos los procesos del fichero .csv finalizará la ejecución de la aplicación.

### ✅ Ampliaciones no solicitadas:

    [x]  - Tener bloqueos de entrada/salida en los procesos.
    [x]  - Evitar inanición.

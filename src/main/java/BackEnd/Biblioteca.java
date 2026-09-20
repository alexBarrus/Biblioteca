/*Barrutiea Nulutagua Juan Alexander
Alcala Bejarano Jennifer Paola
*/

package BackEnd;

/**
 * @author Alexander
 */
public class Biblioteca {

    
    public static final int CAPACIDAD_MAXIMA = 10000;

    
    private Libro[] libros;

    /**
     * Crea la biblioteca con la capacidad indicada.
     *
     * @param capacidad número de posiciones (mayor que cero)
     * @throws IllegalArgumentException si la capacidad no es válida
     */
    public Biblioteca(int capacidad) {
        validarCapacidad(capacidad);
        this.libros = new Libro[capacidad];
    }

    // ---------- Validaciones internas ----------

    private static void validarCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero (recibido: " + capacidad + ").");
        }
        if (capacidad > CAPACIDAD_MAXIMA) {
            throw new IllegalArgumentException("La capacidad máxima permitida es " + CAPACIDAD_MAXIMA
                    + " (recibido: " + capacidad + ").");
        }
    }

    private void validarExistencia() {
        if (libros == null) {
            throw new IllegalStateException("La biblioteca no ha sido creada. Primero debes crearla.");
        }
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= libros.length) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice
                    + ". Valores permitidos: 0 a " + (libros.length - 1) + ".");
        }
    }

    // ---------- Consultas auxiliares ----------

    /** @return true si el arreglo interno fue inicializado y no ha sido destruido. */
    public boolean existe() {
        return libros != null;
    }

    /** @return número total de posiciones del arreglo. */
    public int getCapacidad() {
        validarExistencia();
        return libros.length;
    }

    // ---------- Métodos obligatorios ----------

    /**
     * Crea (o vuelve a crear) el arreglo interno con la capacidad indicada.
     * Si ya existía un arreglo, se reemplaza por uno nuevo y vacío.
     */
    public void crearBiblioteca(int capacidad) {
        validarCapacidad(capacidad);
        this.libros = new Libro[capacidad];
    }

    /**
     * Recorre el arreglo completo y devuelve el listado de posiciones,
     * usando toString() de cada Libro e indicando las posiciones vacías.
     */
    public String mostrarLibros() {
        validarExistencia();
        StringBuilder sb = new StringBuilder();
        sb.append("===== LIBROS DE LA BIBLIOTECA (capacidad: ").append(libros.length).append(") =====\n");
        for (int i = 0; i < libros.length; i++) {
            sb.append("[").append(i).append("] ");
            if (libros[i] == null) {
                sb.append("(posición vacía)");
            } else {
                sb.append(libros[i].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Guarda un libro en una posición libre.
     */
    public void agregarLibro(Libro libro, int indice) {
        validarExistencia();
        validarIndice(indice);
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser nulo.");
        }
        if (libros[indice] != null) {
            throw new IllegalStateException("La posición " + indice + " ya está ocupada por: " + libros[indice]);
        }
        libros[indice] = libro;
    }

    /**
     * Devuelve el libro almacenado en la posición indicada (null si está vacía).
     */
    public Libro mostrarLibroPorIndice(int indice) {
        validarExistencia();
        validarIndice(indice);
        return libros[indice];
    }

    /**
     * Reemplaza el libro de una posición que ya contiene un libro.
     */
    public void modificarLibro(int indice, Libro libro) {
        validarExistencia();
        validarIndice(indice);
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser nulo.");
        }
        if (libros[indice] == null) {
            throw new IllegalStateException("No hay ningún libro en la posición " + indice
                    + ". Usa la opción de agregar.");
        }
        libros[indice] = libro;
    }

    /**
     * Elimina el libro de la posición indicada asignando null.
     */
    public void eliminarLibro(int indice) {
        validarExistencia();
        validarIndice(indice);
        if (libros[indice] == null) {
            throw new IllegalStateException("No hay ningún libro en la posición " + indice + " para eliminar.");
        }
        libros[indice] = null;
    }

    /**
     * Libera todas las referencias y deja la biblioteca como "no creada".
     */
    public void destruirBiblioteca() {
        if (libros != null) {
            for (int i = 0; i < libros.length; i++) {
                libros[i] = null;
            }
            libros = null;
        }
    }
}

/*Barrutiea Nulutagua Juan Alexander
Alcala Bejarano Jennifer Paola
*/

package BackEnd;

import java.time.Year;
import java.util.Objects;

/**
 * @author Alexander
 */
public class Libro {

    // ---------- Atributos privados (ocultación de información) ----------
    private String titulo;
    private String autor;
    private int anioPublicacion;

    // ---------- Constructores ----------

    /**
     * Constructor vacío: crea un libro con valores por defecto válidos.
     */
    public Libro() {
        this.titulo = "Sin título";
        this.autor = "Sin autor";
        this.anioPublicacion = Year.now().getValue();
    }

    /**
     * Constructor con parámetros: valida cada dato antes de asignarlo.
     *
     * @param titulo título del libro (obligatorio)
     * @param autor autor del libro (obligatorio)
     * @param anioPublicacion año entre 1 y el año actual
     */
    public Libro(String titulo, String autor, int anioPublicacion) {
        this.titulo = validarTexto(titulo, "título");
        this.autor = validarTexto(autor, "autor");
        this.anioPublicacion = validarAnio(anioPublicacion);
    }

    // ---------- Validaciones internas (privadas, reutilizadas por constructor y setters) ----------

    private static String validarTexto(String valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo.");
        }
        String limpio = valor.trim();
        if (limpio.isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio (no puede estar vacío).");
        }
        return limpio;
    }

    private static int validarAnio(int anio) {
        int actual = Year.now().getValue();
        if (anio < 1 || anio > actual) {
            throw new IllegalArgumentException("El año de publicación debe estar entre 1 y "
                    + actual + " (recibido: " + anio + ").");
        }
        return anio;
    }

    // ---------- Getters y setters ----------

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = validarTexto(titulo, "título");
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = validarTexto(autor, "autor");
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = validarAnio(anioPublicacion);
    }

    // ---------- equals, hashCode y toString ----------

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.titulo);
        hash = 53 * hash + Objects.hashCode(this.autor);
        hash = 53 * hash + this.anioPublicacion;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Libro other = (Libro) obj;
        if (this.anioPublicacion != other.anioPublicacion) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        return Objects.equals(this.autor, other.autor);
    }

    @Override
    public String toString() {
        return "Título: " + titulo + " | Autor: " + autor + " | Año: " + anioPublicacion;
    }
}

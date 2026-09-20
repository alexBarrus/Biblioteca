/*Barrutiea Nulutagua Juan Alexander
Alcala Bejarano Jennifer Paola
*/

package BackEnd;

import java.time.Year;

/**
 * Pruebas por consola del backend (sin interfaz gráfica).
 * Ejecutar con: clic derecho > Run File.
 *
 * @author Alexander
 */
public class PruebasBiblioteca {

    private static int correctas = 0;
    private static int fallidas = 0;

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        titulo("1. Creación de la biblioteca y uso de constructores");
        Biblioteca b = new Biblioteca(3);
        verificar("La biblioteca fue creada (existe)", b.existe());
        verificar("La capacidad es 3", b.getCapacidad() == 3);

        Libro l1 = new Libro("Cien años de soledad", "Gabriel García Márquez", 1967);
        Libro l2 = new Libro("El principito", "Antoine de Saint-Exupéry", 1943);
        Libro vacio = new Libro();
        verificar("Constructor con parámetros asigna el título", l1.getTitulo().equals("Cien años de soledad"));
        verificar("Constructor vacío asigna valores por defecto", vacio.getTitulo().equals("Sin título"));

        // ---------------------------------------------------------------
        titulo("2. Getters y setters (modificación mediante setters)");
        verificar("getAutor()", l1.getAutor().equals("Gabriel García Márquez"));
        verificar("getAnioPublicacion()", l1.getAnioPublicacion() == 1967);
        vacio.setTitulo("Rayuela");
        vacio.setAutor("Julio Cortázar");
        vacio.setAnioPublicacion(1963);
        verificar("Los setters cambiaron los tres atributos",
                vacio.getTitulo().equals("Rayuela") && vacio.getAutor().equals("Julio Cortázar")
                && vacio.getAnioPublicacion() == 1963);

        // ---------------------------------------------------------------
        titulo("3. equals, hashCode y toString");
        Libro copia = new Libro("El principito", "Antoine de Saint-Exupéry", 1943);
        verificar("equals: dos libros con los mismos datos son iguales", l2.equals(copia));
        verificar("hashCode coherente con equals", l2.hashCode() == copia.hashCode());
        verificar("equals: libros distintos no son iguales", !l1.equals(l2));
        verificar("equals: comparar con null devuelve false", !l1.equals(null));
        System.out.println("     toString -> " + l1);

        // ---------------------------------------------------------------
        titulo("4. Agregar libros / almacenamiento en el arreglo");
        b.agregarLibro(l1, 0);
        b.agregarLibro(l2, 2);
        verificar("Se guardó el libro en la posición 0", b.mostrarLibroPorIndice(0) == l1);
        verificar("Se guardó el libro en la posición 2", b.mostrarLibroPorIndice(2) == l2);

        // ---------------------------------------------------------------
        titulo("5. Mostrar libros (con posiciones vacías)");
        System.out.print(b.mostrarLibros());

        // ---------------------------------------------------------------
        titulo("6. Consulta por índice");
        verificar("Posición ocupada devuelve el libro", b.mostrarLibroPorIndice(0).equals(l1));
        verificar("Posición vacía devuelve null", b.mostrarLibroPorIndice(1) == null);

        // ---------------------------------------------------------------
        titulo("7. Modificar mediante setters sobre el objeto guardado");
        b.mostrarLibroPorIndice(0).setAnioPublicacion(1970);
        verificar("El cambio se refleja en el arreglo", b.mostrarLibroPorIndice(0).getAnioPublicacion() == 1970);

        // ---------------------------------------------------------------
        titulo("8. Modificar libros (reemplazo con modificarLibro)");
        b.modificarLibro(2, vacio);
        verificar("La posición 2 ahora contiene Rayuela", b.mostrarLibroPorIndice(2).getTitulo().equals("Rayuela"));

        // ---------------------------------------------------------------
        titulo("9. Eliminar libros");
        b.eliminarLibro(0);
        verificar("La posición 0 quedó vacía (null)", b.mostrarLibroPorIndice(0) == null);

        // ---------------------------------------------------------------
        titulo("10. Validaciones: capacidad inválida");
        esperar("new Biblioteca(0)", IllegalArgumentException.class, () -> new Biblioteca(0));
        esperar("new Biblioteca(-5)", IllegalArgumentException.class, () -> new Biblioteca(-5));
        esperar("crearBiblioteca(0)", IllegalArgumentException.class, () -> b.crearBiblioteca(0));
        esperar("capacidad mayor al máximo", IllegalArgumentException.class,
                () -> new Biblioteca(Biblioteca.CAPACIDAD_MAXIMA + 1));

        titulo("11. Validaciones: información incorrecta");
        esperar("título nulo", IllegalArgumentException.class, () -> new Libro(null, "Autor", 2000));
        esperar("título vacío", IllegalArgumentException.class, () -> new Libro("   ", "Autor", 2000));
        esperar("autor vacío (setter)", IllegalArgumentException.class, () -> l2.setAutor(""));
        esperar("año negativo", IllegalArgumentException.class, () -> new Libro("T", "A", -1));
        esperar("año en el futuro", IllegalArgumentException.class,
                () -> l2.setAnioPublicacion(Year.now().getValue() + 1));
        esperar("agregar un libro nulo", IllegalArgumentException.class, () -> b.agregarLibro(null, 1));

        titulo("12. Validaciones: índices fuera de rango");
        esperar("agregar en índice -1", IndexOutOfBoundsException.class, () -> b.agregarLibro(l1, -1));
        esperar("agregar en índice 3 (capacidad 3)", IndexOutOfBoundsException.class, () -> b.agregarLibro(l1, 3));
        esperar("mostrar por índice 99", IndexOutOfBoundsException.class, () -> b.mostrarLibroPorIndice(99));
        esperar("modificar índice -2", IndexOutOfBoundsException.class, () -> b.modificarLibro(-2, l1));
        esperar("eliminar índice 3", IndexOutOfBoundsException.class, () -> b.eliminarLibro(3));

        titulo("13. Validaciones: posiciones ocupadas y vacías");
        esperar("agregar en posición ocupada (2)", IllegalStateException.class, () -> b.agregarLibro(l1, 2));
        esperar("modificar posición vacía (1)", IllegalStateException.class, () -> b.modificarLibro(1, l1));
        esperar("eliminar posición vacía (1)", IllegalStateException.class, () -> b.eliminarLibro(1));

        // ---------------------------------------------------------------
        titulo("14. Destruir la biblioteca y manejo de 'biblioteca no creada'");
        b.destruirBiblioteca();
        verificar("La biblioteca ya no existe", !b.existe());
        esperar("mostrarLibros sin biblioteca", IllegalStateException.class, () -> b.mostrarLibros());
        esperar("agregarLibro sin biblioteca", IllegalStateException.class, () -> b.agregarLibro(l1, 0));
        esperar("mostrarLibroPorIndice sin biblioteca", IllegalStateException.class, () -> b.mostrarLibroPorIndice(0));
        esperar("modificarLibro sin biblioteca", IllegalStateException.class, () -> b.modificarLibro(0, l1));
        esperar("eliminarLibro sin biblioteca", IllegalStateException.class, () -> b.eliminarLibro(0));
        b.crearBiblioteca(2);
        verificar("Se puede volver a crear después de destruirla", b.existe() && b.getCapacidad() == 2);

        // ---------------------------------------------------------------
        System.out.println();
        System.out.println("RESUMEN -> correctas: " + correctas + " | fallidas: " + fallidas);
    }

    // ---------- Utilidades de las pruebas ----------

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("--- " + texto + " ---");
    }

    private static void verificar(String descripcion, boolean condicion) {
        if (condicion) {
            correctas++;
            System.out.println("  [OK]    " + descripcion);
        } else {
            fallidas++;
            System.out.println("  [FALLO] " + descripcion);
        }
    }

    /** Verifica que la acción lance la excepción esperada (y muestra el mensaje del sistema). */
    private static void esperar(String descripcion, Class<? extends RuntimeException> tipo, Runnable accion) {
        try {
            accion.run();
            fallidas++;
            System.out.println("  [FALLO] " + descripcion + " (no se lanzó ninguna excepción)");
        } catch (RuntimeException ex) {
            if (tipo.isInstance(ex)) {
                correctas++;
                System.out.println("  [OK]    " + descripcion + " -> " + ex.getMessage());
            } else {
                fallidas++;
                System.out.println("  [FALLO] " + descripcion + " (se lanzó " + ex.getClass().getSimpleName() + ")");
            }
        }
    }
}

package SERVICIOS;

import DAO.EjemplarDAO;
import DAO.LibroDAO;
import DTO.Ejemplar;
import DTO.Libro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EjemplarServ {
    Scanner teclado = new Scanner(System.in);
    List<Ejemplar> listaEjemplaresMemoria = new ArrayList<Ejemplar>();
    EjemplarDAO ejemplarDAO = new EjemplarDAO();
    LibroDAO libroDAO = new LibroDAO();

    public EjemplarServ() {
        this.ejemplarDAO = new EjemplarDAO();
        sincronizar();
    }

    public void sincronizar() {
        listaEjemplaresMemoria = ejemplarDAO.listarEjemplares();
    }

    public void listarEjemplarMemoria() {
        for (Ejemplar ejemplar : listaEjemplaresMemoria) {
            System.out.println(ejemplar);
        }
    }

    public void añadirEjemplarMemoria() {
        System.out.println("Introduce el ISBN del libro asociado: ");
        String isbn = teclado.nextLine();  // Consumir el salto de línea
        System.out.println("Introduce el estado del ejemplar (Disponible, Prestado, Dañado,nuevo): ");
        String estado = teclado.nextLine();
        Libro libroIsbn = new LibroDAO().getLibroIsbn(isbn);

        Ejemplar ejemplar = new Ejemplar(estado,libroIsbn);
        ejemplarDAO.insertarEjemplares(ejemplar);
    }


    public void eliminarEjemplarMemoria(){
        System.out.println("Introduce el id del ejemplar a eliminar: ");
        int id = Integer.parseInt(teclado.nextLine());
        if (ejemplarDAO.getEjemplarId(id) != null) {
            ejemplarDAO.eliminarEjemplares(ejemplarDAO.getEjemplarId(id));
        }
    }

    //Metodo para saber cuantos ejemplares están en disponible
    public void contarEjemplaresDisponibles() {
        List<Ejemplar> listaEjemplaresDisponibles = new ArrayList<>();
        for (Ejemplar ejemplar : listaEjemplaresMemoria) {
            if ("disponible".equals(ejemplar.getEstado())) {
                listaEjemplaresDisponibles.add(ejemplar);
            }
        }
        System.out.println("Cantidad de ejemplares disponibles: " + listaEjemplaresDisponibles.size());
        System.out.println("La lista de ejemplares disponibles son: " + listaEjemplaresDisponibles);
    }

}

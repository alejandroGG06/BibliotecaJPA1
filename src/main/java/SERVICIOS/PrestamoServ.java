package SERVICIOS;

import DAO.EjemplarDAO;
import DAO.PrestamoDAO;
import DAO.UsuarioDAO;
import DTO.Ejemplar;
import DTO.Prestamo;
import DTO.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrestamoServ {
    Scanner sc = new Scanner(System.in);
    List<Prestamo> memoria = new ArrayList<Prestamo>();
    PrestamoDAO prestamoDAO;
    UsuarioDAO usuarioDAO;
    EjemplarDAO ejemplarDAO;

    public PrestamoServ() {
        this.usuarioDAO = new UsuarioDAO();
        this.ejemplarDAO = new EjemplarDAO();
        this.prestamoDAO = new PrestamoDAO();
        sincronizar();
    }
    public void sincronizar() {
        memoria = prestamoDAO.listarPrestamos();
    }

    public void listarPrestamosMemoriaDeUsuario() {
        System.out.println("Inserte el id del usuario: ");
        int idUsuario = sc.nextInt();
        for (Prestamo prestamo : memoria) {
            if (prestamo.getUsuario().getId() == idUsuario) {
                System.out.println(prestamo);
            }
        }
    }


    public void listarPrestamosMemoria() {
        for (Prestamo prestamo : memoria) {
            System.out.println(prestamo);
        }
    }

    public void registrarPrestamoMemoria() {
        System.out.println("Introduce el id del usuario: ");
        int idUsuario = sc.nextInt();
        System.out.println("Introduce el id del ejemplar: ");
        int idEjemplar = sc.nextInt();

        Usuario usuario = usuarioDAO.getUsuarioId(idUsuario);
        Ejemplar ejemplar = ejemplarDAO.getEjemplarId(idEjemplar);

        // Verificar que el ejemplar esté disponible
        if (!ejemplar.getEstado().equals("Disponible")) {
            System.out.println("El ejemplar no está disponible.");
        }

        // Verificar que el usuario no tenga más de 3 préstamos activos
        long prestamosActivos = memoria.stream()
                .filter(p -> p.getUsuario().getId() == idUsuario ).count();

        if (prestamosActivos >= 3) {
            System.out.println("El usuario ya tiene 3 préstamos activos y no puede solicitar más.");
            return;
        }

        // Verificar que el usuario no tenga una penalización activa
        if (usuario.getPenalizacionHasta() != null) {
            System.out.println("El usuario tiene una penalización activa hasta " + usuario.getPenalizacionHasta() + ".");
        }

        // Registrar el préstamo
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = LocalDate.now().plusDays(15);
        Prestamo prestamo = new Prestamo(usuario,ejemplar,fechaPrestamo,fechaDevolucion);

        prestamoDAO.insertarPrestamos(prestamo);

        // Cambiar el estado del ejemplar a "No disponible"
        ejemplar.setEstado("No disponible");
        ejemplarDAO.modificarEjemplares(ejemplar);

        System.out.println("Préstamo registrado correctamente.");
    }

    public void registrarDevolucionPrestamo() {
        System.out.println("Introduce el id del préstamo a devolver: ");
        int idPrestamo = sc.nextInt();

        // Recuperar el préstamo desde la base de datos
        Prestamo prestamo = prestamoDAO.getPrestamoId(idPrestamo);
        Ejemplar ejemplar = prestamo.getEjemplar();
        Usuario usuario = prestamo.getUsuario();

        // Cambiar el estado del ejemplar a "Disponible"
        ejemplar.setEstado("Disponible");
        ejemplarDAO.modificarEjemplares(ejemplar);

        // Registrar la fecha de devolución del préstamo
        LocalDate fechaDevolucion = LocalDate.now();
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamoDAO.actualizarPrestamos(prestamo);

        // Calcular penalización si el préstamo se devolvió fuera de plazo
        if (fechaDevolucion.isAfter(prestamo.getFechaDevolucion())) {
            long diasRetraso = java.time.temporal.ChronoUnit.DAYS.between(prestamo.getFechaDevolucion(), fechaDevolucion);
            long penalizacionDias = diasRetraso * 15;

            // Registrar la penalización al usuario
            LocalDate nuevaPenalizacion = LocalDate.now().plusDays(penalizacionDias);
            if (usuario.getPenalizacionHasta() == null || nuevaPenalizacion.isAfter(usuario.getPenalizacionHasta())) {
                usuario.setPenalizacionHasta(nuevaPenalizacion);
                usuarioDAO.actualizarUsuario(usuario);
            }

            System.out.println("El usuario tiene una nueva penalización hasta " + nuevaPenalizacion + " por devolver tarde.");
        }

        System.out.println("El ejemplar se cambió a Disponible");
    }




    public void eliminarPrestamoMemoria() {
        System.out.println("Introduce el id del prestamo a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());
        if (prestamoDAO.getPrestamoId(id) != null) {
            prestamoDAO.eliminarPrestamos(prestamoDAO.getPrestamoId(id));
            System.out.println("Prestamo eliminado correctamente");
        }
    }
}

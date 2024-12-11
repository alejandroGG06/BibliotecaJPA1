package MENU;

//@author AlejandroGpublic

import SERVICIOS.EjemplarServ;
import SERVICIOS.LibroServ;
import SERVICIOS.PrestamoServ;
import SERVICIOS.UsuarioServ;

import java.util.Scanner;

public class Launcher {

    public void menus() {

        EjemplarServ ejemplarService = new EjemplarServ();
        LibroServ libroService = new LibroServ();
        PrestamoServ prestamoService = new PrestamoServ();
        UsuarioServ usuarioService = new UsuarioServ();
        Scanner teclado = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("-----------------------------");
            System.out.println("Elige una opción: ");
            System.out.println("1. Iniciar Sesión como Usuario");
            System.out.println("2. Iniciar Sesión como Administrador");
            System.out.println("0. Salir");
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1 -> { // Inicio de sesión como Usuario
                    System.out.print("Ingrese su ID de Usuario: ");
                    if (usuarioService.esUsuario()) {
                        int opcionUsuario;
                        do {
                            System.out.println("-----------------");
                            System.out.println("Menú de Usuario:");
                            System.out.println("1. Listar préstamos");
                            System.out.println("2. Listar datos usuarios");
                            System.out.println("0. Salir");
                            System.out.print("Elige opción: ");
                            opcionUsuario = teclado.nextInt();

                            switch (opcionUsuario) {
                                case 0 -> System.out.println("Regresando al menú principal...");
                                case 1 -> prestamoService.listarPrestamosMemoriaDeUsuario();
                                case 2 -> usuarioService.listarUsuariosMemoriaDeUsuario();


                            }
                        } while (opcionUsuario != 0);
                    }
                }
                case 2 -> { // Inicio de sesión como Administrador
                    if (usuarioService.esAdministrador()) {
                        int opcionAdmin;
                        do {
                            System.out.println("---------------------");
                            System.out.println("Menú de Administrador:");
                            System.out.println("1. Registrar nuevo libro");
                            System.out.println("2. Listar Libro");
                            System.out.println("3. Eliminar Libro");
                            System.out.println("4. Contar ejemplares disponibles");
                            System.out.println("5. Registrar ejemplar");
                            System.out.println("6. Listar Ejemplares");
                            System.out.println("7. Registrar nuevo usuario");
                            System.out.println("8. Registrar penalizaciones para el usuario");
                            System.out.println("9. Registrar prestamo");
                            System.out.println("10. Listar prestamos");
                            System.out.println("11. Eliminar prestamo");
                            System.out.println("12. Devolver prestamo");

                            System.out.println("0. Volver al menú principal");
                            System.out.print("Elige una opción: ");
                            opcionAdmin = teclado.nextInt();

                            switch (opcionAdmin) {
                                case 0 -> System.out.println("Regresando al menú principal...");
                                case 1-> libroService.añadirLibroMemoria();
                                case 2 -> libroService.listarLibrosMemoria();
                                case 3-> libroService.eliminarLibroMemoria();
                                case 4-> ejemplarService.contarEjemplaresDisponibles();
                                case 5->ejemplarService.añadirEjemplarMemoria();
                                case 6->ejemplarService.listarEjemplarMemoria();
                                case 7->usuarioService.registrarUsuarioMemoria();
                                case 8-> usuarioService.registrarPenalizacion();
                                case 9->prestamoService.registrarPrestamoMemoria();
                                case 10->prestamoService.listarPrestamosMemoria();
                                case 11->prestamoService.eliminarPrestamoMemoria();
                                case 12->prestamoService.registrarDevolucionPrestamo();
                            }
                        } while (opcionAdmin != 0);
                    }
                }
                case 0 -> System.out.println("Saliendo de la biblioteca...");
                default -> System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 0);

        teclado.close();
    }
}

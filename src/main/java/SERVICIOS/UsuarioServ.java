package SERVICIOS;

import DAO.UsuarioDAO;
import DTO.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UsuarioServ {
    Scanner teclado = new Scanner(System.in);
    List<Usuario> listaUsuariosMemoria = new ArrayList<Usuario>();
    UsuarioDAO usuarioDAO;

    public UsuarioServ() {
        this.usuarioDAO = new UsuarioDAO();
        sincronizar();
    }

    public void sincronizar() {
        listaUsuariosMemoria = usuarioDAO.listarUsuarios();
    }

    public void listarUsuariosMemoriaDeUsuario() {
        System.out.println("Introduce id del usuario: ");
        int idUsuario = teclado.nextInt();
        for (Usuario usuario : listaUsuariosMemoria) {
            if (usuario.getId() == idUsuario) {
                System.out.println(usuario);
            }
        }
    }

    public void registrarUsuarioMemoria() {
        System.out.println("Introduce el DNI del usuario: ");
        String dni = teclado.nextLine();
        System.out.println("Introduce el nombre del usuario: ");
        String nombre = teclado.nextLine();
        System.out.println("Introduce el email del usuario: ");
        String email = teclado.nextLine();
        System.out.println("Introduce la contraseña del usuario: ");
        String contrasena = teclado.nextLine();
        System.out.println("Introduce el tipo de usuario (Normal/Administrador): ");
        String tipo = teclado.nextLine();

        Usuario usuario = new Usuario(dni, nombre, email, contrasena, tipo);
        usuarioDAO.insertarUsuario(usuario);
    }

    public boolean esAdministrador() {
        System.out.println("Ingrese su id: ");
        int id = teclado.nextInt();
        teclado.nextLine();  // Limpiar el buffer de la línea restante
        System.out.println("Introduce su contraseña: ");
        String contraseña = teclado.nextLine();

        for (Usuario usuario : listaUsuariosMemoria) {
            if (usuario.getId() == id) {
                // ID correcto, ahora verificamos la contraseña
                System.out.println("ID correcto");

                if (usuario.getPassword().equals(contraseña)) {
                    System.out.println("Contraseña correcta");

                    // Verificamos si es un administrador
                    if (usuario.getTipo().equals("administrador")) {
                        System.out.println("Inicio de sesión correcta");
                        return true;  // Es administrador
                    } else {
                        System.out.println("Acceso denegado.");
                        return false;  // No es administrador
                    }
                } else {
                    System.out.println("Contraseña incorrecta");
                    return false;  // Contraseña incorrecta
                }
            }
        }

        System.out.println("ID incorrecto");
        return false;  // El ID no fue encontrado
    }


    public boolean esUsuario() {
        System.out.println("Ingrese su id: ");
        int id = teclado.nextInt();
        teclado.nextLine();  // Limpiar el buffer de la línea restante
        System.out.println("Introduce su contraseña: ");
        String contraseña = teclado.nextLine();

        for (Usuario usuario : listaUsuariosMemoria) {
            if (usuario.getId() == id) {
                // ID correcto, ahora verificamos la contraseña
                System.out.println("ID correcto");

                if (usuario.getPassword().equals(contraseña)) {
                    System.out.println("Contraseña correcta");

                    // Verificamos si es un usuario normal
                    if (usuario.getTipo().equals("normal")) {
                        System.out.println("Inicio de sesión correcta.");
                        return true;  // Es usuario normal
                    } else {
                        System.out.println("Acceso denegado.");
                        return false;  // No es un usuario normal
                    }
                } else {
                    System.out.println("Contraseña incorrecta");
                    return false;  // Contraseña incorrecta
                }
            }
        }

        System.out.println("ID incorrecto");
        return false;  // El ID no fue encontrado
    }


    public void eliminarUsuarioMemoria() {
        System.out.println("Introduce el id del usuario a eliminar: ");
        int id = teclado.nextInt();
        if (usuarioDAO.getUsuarioId(id)!= null){
            usuarioDAO.eliminarUsuario(usuarioDAO.getUsuarioId(id));
        }
    }

    public void registrarPenalizacion() {
        System.out.println("Introduce el id del usuario que quieres penalizar: ");
        int id = teclado.nextInt();

        Usuario usuario = usuarioDAO.getUsuarioId(id); // Busca directamente por el DNI

        if (usuario != null) {
            LocalDate fechaActualMas15 = LocalDate.now().plusDays(15);
            usuario.setPenalizacionHasta(fechaActualMas15);
            usuarioDAO.actualizarUsuario(usuario);
            System.out.println("Usuario " + usuario.getNombre() + " penalizado hasta " + fechaActualMas15);
        } else {
            System.out.println("Usuario no encontrado");
        }
    }

}

package DAO;

import DTO.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class UsuarioDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist");
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaccion = em.getTransaction();

    public List<Usuario> listarUsuarios() {
        List<Usuario> listaUsuarios;
        listaUsuarios = em.createQuery("select u from Usuario u", Usuario.class).getResultList();
        if(listaUsuarios.isEmpty()){
            return null;
        }
        return listaUsuarios;
    }



    public  void insertarUsuario(Usuario usuario) {
        transaccion.begin();
        em.persist(usuario);
        transaccion.commit();
    }

    public  void eliminarUsuario(Usuario usuario) {
        transaccion.begin();
        em.remove(usuario);
        transaccion.commit();
    }

    public  void actualizarUsuario(Usuario usuario) {
        transaccion.begin();
        em.merge(usuario);
        transaccion.commit();
    }

    public Usuario getUsuarioId(int id) {
        Usuario usuario = em.find(Usuario.class, id);
        return usuario;
    }

    public Usuario getUsuarioDni(String dni) {
        Usuario usuario = em.find(Usuario.class, dni);
        return usuario;
    }
}

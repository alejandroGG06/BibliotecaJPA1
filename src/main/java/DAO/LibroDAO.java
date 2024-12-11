package DAO;

import DTO.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class LibroDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    public List<Libro> listarLibros(){
        List<Libro> listaLibros = em.createQuery("select l from Libro l").getResultList();
        if (listaLibros.isEmpty()){
            return null;
        }
        return listaLibros;
    }

    public void insertarLibro(Libro libro){
        tx.begin();
        em.persist(libro);
        tx.commit();
    }

    public void eliminarLibro(Libro libro){
        tx.begin();
        em.remove(libro);
        tx.commit();
    }

    public void actualizarLibro(Libro libro){
        tx.begin();
        em.merge(libro);
        tx.commit();
    }

    public Libro getLibroIsbn(String isbn){
        Libro libro = em.find(Libro.class, isbn);
        return libro;
    }
}

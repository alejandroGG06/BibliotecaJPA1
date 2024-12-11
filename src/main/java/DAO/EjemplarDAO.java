package DAO;

import DTO.Ejemplar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class EjemplarDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    public List<Ejemplar> listarEjemplares(){
        List<Ejemplar> listaEjemplares = em.createQuery("select e from Ejemplar e").getResultList();
        if (listaEjemplares.isEmpty()){
            return null;
        }
        return listaEjemplares;
    }

    public void insertarEjemplares (Ejemplar ejemplar){
        tx.begin();
        em.persist(ejemplar);
        tx.commit();
    }

    public void eliminarEjemplares (Ejemplar ejemplar){
        tx.begin();
        em.remove(ejemplar);
        tx.commit();
    }

    public void modificarEjemplares (Ejemplar ejemplar){
        tx.begin();
        em.merge(ejemplar);
        tx.commit();
    }

    public Ejemplar getEjemplarId (int id){
        Ejemplar ejemplar = em.find(Ejemplar.class, id);
        return ejemplar;
    }

    public Ejemplar getEjemplarIsbn (String isbn){
        Ejemplar ejemplar = em.find(Ejemplar.class, isbn);
        return ejemplar;
    }



}

package DAO;

import DTO.Ejemplar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class EjemplarDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaccion = em.getTransaction();

    public List<Ejemplar> listarEjemplares(){
        List<Ejemplar> listaEjemplares = em.createQuery("select e from Ejemplar e").getResultList();
        if (listaEjemplares.isEmpty()){
            return null;
        }
        return listaEjemplares;
    }

    public void insertarEjemplares (Ejemplar ejemplar){
        transaccion.begin();
        em.persist(ejemplar);
        transaccion.commit();
    }

    public void eliminarEjemplares (Ejemplar ejemplar){
        transaccion.begin();
        em.remove(ejemplar);
        transaccion.commit();
    }

    public void modificarEjemplares (Ejemplar ejemplar){
        transaccion.begin();
        em.merge(ejemplar);
        transaccion.commit();
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

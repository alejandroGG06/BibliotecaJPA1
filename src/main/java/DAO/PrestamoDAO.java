package DAO;

import DTO.Prestamo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class PrestamoDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist");
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaccion = em.getTransaction();

    public List<Prestamo> listarPrestamos() {
        List<Prestamo> listaPrestamos = em.createQuery("select p from Prestamo p").getResultList();
        if(listaPrestamos.isEmpty()){
            return null;
        }
        return listaPrestamos;
    }

    public void insertarPrestamos(Prestamo prestamo) {
        transaccion.begin();
        em.persist(prestamo);
        transaccion.commit();
    }
    public void eliminarPrestamos(Prestamo prestamo) {
        transaccion.begin();
        em.remove(prestamo);
        transaccion.commit();
    }

    public void actualizarPrestamos(Prestamo prestamo) {
        transaccion.begin();
        em.merge(prestamo);
        transaccion.commit();
    }

    public Prestamo getPrestamoId(int id) {
        Prestamo prestamo = em.find(Prestamo.class, id);
        return prestamo;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DTO.Cajachica;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Compra;
import DTO.Usuario;
import DTO.Venta;
import DTO.Cajatipo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class CajachicaJpaController implements Serializable {

    public CajachicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cajachica cajachica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra caIdcompra = cajachica.getCaIdcompra();
            if (caIdcompra != null) {
                caIdcompra = em.getReference(caIdcompra.getClass(), caIdcompra.getCoId());
                cajachica.setCaIdcompra(caIdcompra);
            }
            Usuario caIduser = cajachica.getCaIduser();
            if (caIduser != null) {
                caIduser = em.getReference(caIduser.getClass(), caIduser.getUsId());
                cajachica.setCaIduser(caIduser);
            }
            Venta caIdventa = cajachica.getCaIdventa();
            if (caIdventa != null) {
                caIdventa = em.getReference(caIdventa.getClass(), caIdventa.getVeId());
                cajachica.setCaIdventa(caIdventa);
            }
            Cajatipo caTipo = cajachica.getCaTipo();
            if (caTipo != null) {
                caTipo = em.getReference(caTipo.getClass(), caTipo.getId());
                cajachica.setCaTipo(caTipo);
            }
            em.persist(cajachica);
            if (caIdcompra != null) {
                caIdcompra.getCajachicaList().add(cajachica);
                caIdcompra = em.merge(caIdcompra);
            }
            if (caIduser != null) {
                caIduser.getCajachicaList().add(cajachica);
                caIduser = em.merge(caIduser);
            }
            if (caIdventa != null) {
                caIdventa.getCajachicaList().add(cajachica);
                caIdventa = em.merge(caIdventa);
            }
            if (caTipo != null) {
                caTipo.getCajachicaList().add(cajachica);
                caTipo = em.merge(caTipo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cajachica cajachica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cajachica persistentCajachica = em.find(Cajachica.class, cajachica.getCaId());
            Compra caIdcompraOld = persistentCajachica.getCaIdcompra();
            Compra caIdcompraNew = cajachica.getCaIdcompra();
            Usuario caIduserOld = persistentCajachica.getCaIduser();
            Usuario caIduserNew = cajachica.getCaIduser();
            Venta caIdventaOld = persistentCajachica.getCaIdventa();
            Venta caIdventaNew = cajachica.getCaIdventa();
            Cajatipo caTipoOld = persistentCajachica.getCaTipo();
            Cajatipo caTipoNew = cajachica.getCaTipo();
            if (caIdcompraNew != null) {
                caIdcompraNew = em.getReference(caIdcompraNew.getClass(), caIdcompraNew.getCoId());
                cajachica.setCaIdcompra(caIdcompraNew);
            }
            if (caIduserNew != null) {
                caIduserNew = em.getReference(caIduserNew.getClass(), caIduserNew.getUsId());
                cajachica.setCaIduser(caIduserNew);
            }
            if (caIdventaNew != null) {
                caIdventaNew = em.getReference(caIdventaNew.getClass(), caIdventaNew.getVeId());
                cajachica.setCaIdventa(caIdventaNew);
            }
            if (caTipoNew != null) {
                caTipoNew = em.getReference(caTipoNew.getClass(), caTipoNew.getId());
                cajachica.setCaTipo(caTipoNew);
            }
            cajachica = em.merge(cajachica);
            if (caIdcompraOld != null && !caIdcompraOld.equals(caIdcompraNew)) {
                caIdcompraOld.getCajachicaList().remove(cajachica);
                caIdcompraOld = em.merge(caIdcompraOld);
            }
            if (caIdcompraNew != null && !caIdcompraNew.equals(caIdcompraOld)) {
                caIdcompraNew.getCajachicaList().add(cajachica);
                caIdcompraNew = em.merge(caIdcompraNew);
            }
            if (caIduserOld != null && !caIduserOld.equals(caIduserNew)) {
                caIduserOld.getCajachicaList().remove(cajachica);
                caIduserOld = em.merge(caIduserOld);
            }
            if (caIduserNew != null && !caIduserNew.equals(caIduserOld)) {
                caIduserNew.getCajachicaList().add(cajachica);
                caIduserNew = em.merge(caIduserNew);
            }
            if (caIdventaOld != null && !caIdventaOld.equals(caIdventaNew)) {
                caIdventaOld.getCajachicaList().remove(cajachica);
                caIdventaOld = em.merge(caIdventaOld);
            }
            if (caIdventaNew != null && !caIdventaNew.equals(caIdventaOld)) {
                caIdventaNew.getCajachicaList().add(cajachica);
                caIdventaNew = em.merge(caIdventaNew);
            }
            if (caTipoOld != null && !caTipoOld.equals(caTipoNew)) {
                caTipoOld.getCajachicaList().remove(cajachica);
                caTipoOld = em.merge(caTipoOld);
            }
            if (caTipoNew != null && !caTipoNew.equals(caTipoOld)) {
                caTipoNew.getCajachicaList().add(cajachica);
                caTipoNew = em.merge(caTipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cajachica.getCaId();
                if (findCajachica(id) == null) {
                    throw new NonexistentEntityException("The cajachica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cajachica cajachica;
            try {
                cajachica = em.getReference(Cajachica.class, id);
                cajachica.getCaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cajachica with id " + id + " no longer exists.", enfe);
            }
            Compra caIdcompra = cajachica.getCaIdcompra();
            if (caIdcompra != null) {
                caIdcompra.getCajachicaList().remove(cajachica);
                caIdcompra = em.merge(caIdcompra);
            }
            Usuario caIduser = cajachica.getCaIduser();
            if (caIduser != null) {
                caIduser.getCajachicaList().remove(cajachica);
                caIduser = em.merge(caIduser);
            }
            Venta caIdventa = cajachica.getCaIdventa();
            if (caIdventa != null) {
                caIdventa.getCajachicaList().remove(cajachica);
                caIdventa = em.merge(caIdventa);
            }
            Cajatipo caTipo = cajachica.getCaTipo();
            if (caTipo != null) {
                caTipo.getCajachicaList().remove(cajachica);
                caTipo = em.merge(caTipo);
            }
            em.remove(cajachica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cajachica> findCajachicaEntities() {
        return findCajachicaEntities(true, -1, -1);
    }

    public List<Cajachica> findCajachicaEntities(int maxResults, int firstResult) {
        return findCajachicaEntities(false, maxResults, firstResult);
    }

    private List<Cajachica> findCajachicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cajachica.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cajachica findCajachica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cajachica.class, id);
        } finally {
            em.close();
        }
    }

    public int getCajachicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cajachica> rt = cq.from(Cajachica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Compra;
import DTO.Dcompra;
import DTO.Inventario;
import DTO.Proveedor;
import DTO.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class DcompraJpaController implements Serializable {

    public DcompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dcompra dcompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra dcoIdcompra = dcompra.getDcoIdcompra();
            if (dcoIdcompra != null) {
                dcoIdcompra = em.getReference(dcoIdcompra.getClass(), dcoIdcompra.getCoId());
                dcompra.setDcoIdcompra(dcoIdcompra);
            }
            Inventario dcoIdinventario = dcompra.getDcoIdinventario();
            if (dcoIdinventario != null) {
                dcoIdinventario = em.getReference(dcoIdinventario.getClass(), dcoIdinventario.getInId());
                dcompra.setDcoIdinventario(dcoIdinventario);
            }
            Proveedor dcoIdproveedor = dcompra.getDcoIdproveedor();
            if (dcoIdproveedor != null) {
                dcoIdproveedor = em.getReference(dcoIdproveedor.getClass(), dcoIdproveedor.getPrId());
                dcompra.setDcoIdproveedor(dcoIdproveedor);
            }
            Usuario dcoIduser = dcompra.getDcoIduser();
            if (dcoIduser != null) {
                dcoIduser = em.getReference(dcoIduser.getClass(), dcoIduser.getUsId());
                dcompra.setDcoIduser(dcoIduser);
            }
            em.persist(dcompra);
            if (dcoIdcompra != null) {
                dcoIdcompra.getDcompraList().add(dcompra);
                dcoIdcompra = em.merge(dcoIdcompra);
            }
            if (dcoIdinventario != null) {
                dcoIdinventario.getDcompraList().add(dcompra);
                dcoIdinventario = em.merge(dcoIdinventario);
            }
            if (dcoIdproveedor != null) {
                dcoIdproveedor.getDcompraList().add(dcompra);
                dcoIdproveedor = em.merge(dcoIdproveedor);
            }
            if (dcoIduser != null) {
                dcoIduser.getDcompraList().add(dcompra);
                dcoIduser = em.merge(dcoIduser);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dcompra dcompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dcompra persistentDcompra = em.find(Dcompra.class, dcompra.getDcoId());
            Compra dcoIdcompraOld = persistentDcompra.getDcoIdcompra();
            Compra dcoIdcompraNew = dcompra.getDcoIdcompra();
            Inventario dcoIdinventarioOld = persistentDcompra.getDcoIdinventario();
            Inventario dcoIdinventarioNew = dcompra.getDcoIdinventario();
            Proveedor dcoIdproveedorOld = persistentDcompra.getDcoIdproveedor();
            Proveedor dcoIdproveedorNew = dcompra.getDcoIdproveedor();
            Usuario dcoIduserOld = persistentDcompra.getDcoIduser();
            Usuario dcoIduserNew = dcompra.getDcoIduser();
            if (dcoIdcompraNew != null) {
                dcoIdcompraNew = em.getReference(dcoIdcompraNew.getClass(), dcoIdcompraNew.getCoId());
                dcompra.setDcoIdcompra(dcoIdcompraNew);
            }
            if (dcoIdinventarioNew != null) {
                dcoIdinventarioNew = em.getReference(dcoIdinventarioNew.getClass(), dcoIdinventarioNew.getInId());
                dcompra.setDcoIdinventario(dcoIdinventarioNew);
            }
            if (dcoIdproveedorNew != null) {
                dcoIdproveedorNew = em.getReference(dcoIdproveedorNew.getClass(), dcoIdproveedorNew.getPrId());
                dcompra.setDcoIdproveedor(dcoIdproveedorNew);
            }
            if (dcoIduserNew != null) {
                dcoIduserNew = em.getReference(dcoIduserNew.getClass(), dcoIduserNew.getUsId());
                dcompra.setDcoIduser(dcoIduserNew);
            }
            dcompra = em.merge(dcompra);
            if (dcoIdcompraOld != null && !dcoIdcompraOld.equals(dcoIdcompraNew)) {
                dcoIdcompraOld.getDcompraList().remove(dcompra);
                dcoIdcompraOld = em.merge(dcoIdcompraOld);
            }
            if (dcoIdcompraNew != null && !dcoIdcompraNew.equals(dcoIdcompraOld)) {
                dcoIdcompraNew.getDcompraList().add(dcompra);
                dcoIdcompraNew = em.merge(dcoIdcompraNew);
            }
            if (dcoIdinventarioOld != null && !dcoIdinventarioOld.equals(dcoIdinventarioNew)) {
                dcoIdinventarioOld.getDcompraList().remove(dcompra);
                dcoIdinventarioOld = em.merge(dcoIdinventarioOld);
            }
            if (dcoIdinventarioNew != null && !dcoIdinventarioNew.equals(dcoIdinventarioOld)) {
                dcoIdinventarioNew.getDcompraList().add(dcompra);
                dcoIdinventarioNew = em.merge(dcoIdinventarioNew);
            }
            if (dcoIdproveedorOld != null && !dcoIdproveedorOld.equals(dcoIdproveedorNew)) {
                dcoIdproveedorOld.getDcompraList().remove(dcompra);
                dcoIdproveedorOld = em.merge(dcoIdproveedorOld);
            }
            if (dcoIdproveedorNew != null && !dcoIdproveedorNew.equals(dcoIdproveedorOld)) {
                dcoIdproveedorNew.getDcompraList().add(dcompra);
                dcoIdproveedorNew = em.merge(dcoIdproveedorNew);
            }
            if (dcoIduserOld != null && !dcoIduserOld.equals(dcoIduserNew)) {
                dcoIduserOld.getDcompraList().remove(dcompra);
                dcoIduserOld = em.merge(dcoIduserOld);
            }
            if (dcoIduserNew != null && !dcoIduserNew.equals(dcoIduserOld)) {
                dcoIduserNew.getDcompraList().add(dcompra);
                dcoIduserNew = em.merge(dcoIduserNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dcompra.getDcoId();
                if (findDcompra(id) == null) {
                    throw new NonexistentEntityException("The dcompra with id " + id + " no longer exists.");
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
            Dcompra dcompra;
            try {
                dcompra = em.getReference(Dcompra.class, id);
                dcompra.getDcoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dcompra with id " + id + " no longer exists.", enfe);
            }
            Compra dcoIdcompra = dcompra.getDcoIdcompra();
            if (dcoIdcompra != null) {
                dcoIdcompra.getDcompraList().remove(dcompra);
                dcoIdcompra = em.merge(dcoIdcompra);
            }
            Inventario dcoIdinventario = dcompra.getDcoIdinventario();
            if (dcoIdinventario != null) {
                dcoIdinventario.getDcompraList().remove(dcompra);
                dcoIdinventario = em.merge(dcoIdinventario);
            }
            Proveedor dcoIdproveedor = dcompra.getDcoIdproveedor();
            if (dcoIdproveedor != null) {
                dcoIdproveedor.getDcompraList().remove(dcompra);
                dcoIdproveedor = em.merge(dcoIdproveedor);
            }
            Usuario dcoIduser = dcompra.getDcoIduser();
            if (dcoIduser != null) {
                dcoIduser.getDcompraList().remove(dcompra);
                dcoIduser = em.merge(dcoIduser);
            }
            em.remove(dcompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dcompra> findDcompraEntities() {
        return findDcompraEntities(true, -1, -1);
    }

    public List<Dcompra> findDcompraEntities(int maxResults, int firstResult) {
        return findDcompraEntities(false, maxResults, firstResult);
    }

    private List<Dcompra> findDcompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dcompra.class));
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

    public Dcompra findDcompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dcompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getDcompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dcompra> rt = cq.from(Dcompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

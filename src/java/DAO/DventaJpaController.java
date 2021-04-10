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
import DTO.Cliente;
import DTO.Departamento;
import DTO.Dventa;
import DTO.Inventario;
import DTO.Usuario;
import DTO.Venta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class DventaJpaController implements Serializable {

    public DventaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dventa dventa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente dveIdcliente = dventa.getDveIdcliente();
            if (dveIdcliente != null) {
                dveIdcliente = em.getReference(dveIdcliente.getClass(), dveIdcliente.getClId());
                dventa.setDveIdcliente(dveIdcliente);
            }
            Departamento dveIddepartamento = dventa.getDveIddepartamento();
            if (dveIddepartamento != null) {
                dveIddepartamento = em.getReference(dveIddepartamento.getClass(), dveIddepartamento.getDpId());
                dventa.setDveIddepartamento(dveIddepartamento);
            }
            Inventario dveIdinventario = dventa.getDveIdinventario();
            if (dveIdinventario != null) {
                dveIdinventario = em.getReference(dveIdinventario.getClass(), dveIdinventario.getInId());
                dventa.setDveIdinventario(dveIdinventario);
            }
            Usuario dveIduser = dventa.getDveIduser();
            if (dveIduser != null) {
                dveIduser = em.getReference(dveIduser.getClass(), dveIduser.getUsId());
                dventa.setDveIduser(dveIduser);
            }
            Venta dveIdventa = dventa.getDveIdventa();
            if (dveIdventa != null) {
                dveIdventa = em.getReference(dveIdventa.getClass(), dveIdventa.getVeId());
                dventa.setDveIdventa(dveIdventa);
            }
            em.persist(dventa);
            if (dveIdcliente != null) {
                dveIdcliente.getDventaList().add(dventa);
                dveIdcliente = em.merge(dveIdcliente);
            }
            if (dveIddepartamento != null) {
                dveIddepartamento.getDventaList().add(dventa);
                dveIddepartamento = em.merge(dveIddepartamento);
            }
            if (dveIdinventario != null) {
                dveIdinventario.getDventaList().add(dventa);
                dveIdinventario = em.merge(dveIdinventario);
            }
            if (dveIduser != null) {
                dveIduser.getDventaList().add(dventa);
                dveIduser = em.merge(dveIduser);
            }
            if (dveIdventa != null) {
                dveIdventa.getDventaList().add(dventa);
                dveIdventa = em.merge(dveIdventa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dventa dventa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dventa persistentDventa = em.find(Dventa.class, dventa.getDveId());
            Cliente dveIdclienteOld = persistentDventa.getDveIdcliente();
            Cliente dveIdclienteNew = dventa.getDveIdcliente();
            Departamento dveIddepartamentoOld = persistentDventa.getDveIddepartamento();
            Departamento dveIddepartamentoNew = dventa.getDveIddepartamento();
            Inventario dveIdinventarioOld = persistentDventa.getDveIdinventario();
            Inventario dveIdinventarioNew = dventa.getDveIdinventario();
            Usuario dveIduserOld = persistentDventa.getDveIduser();
            Usuario dveIduserNew = dventa.getDveIduser();
            Venta dveIdventaOld = persistentDventa.getDveIdventa();
            Venta dveIdventaNew = dventa.getDveIdventa();
            if (dveIdclienteNew != null) {
                dveIdclienteNew = em.getReference(dveIdclienteNew.getClass(), dveIdclienteNew.getClId());
                dventa.setDveIdcliente(dveIdclienteNew);
            }
            if (dveIddepartamentoNew != null) {
                dveIddepartamentoNew = em.getReference(dveIddepartamentoNew.getClass(), dveIddepartamentoNew.getDpId());
                dventa.setDveIddepartamento(dveIddepartamentoNew);
            }
            if (dveIdinventarioNew != null) {
                dveIdinventarioNew = em.getReference(dveIdinventarioNew.getClass(), dveIdinventarioNew.getInId());
                dventa.setDveIdinventario(dveIdinventarioNew);
            }
            if (dveIduserNew != null) {
                dveIduserNew = em.getReference(dveIduserNew.getClass(), dveIduserNew.getUsId());
                dventa.setDveIduser(dveIduserNew);
            }
            if (dveIdventaNew != null) {
                dveIdventaNew = em.getReference(dveIdventaNew.getClass(), dveIdventaNew.getVeId());
                dventa.setDveIdventa(dveIdventaNew);
            }
            dventa = em.merge(dventa);
            if (dveIdclienteOld != null && !dveIdclienteOld.equals(dveIdclienteNew)) {
                dveIdclienteOld.getDventaList().remove(dventa);
                dveIdclienteOld = em.merge(dveIdclienteOld);
            }
            if (dveIdclienteNew != null && !dveIdclienteNew.equals(dveIdclienteOld)) {
                dveIdclienteNew.getDventaList().add(dventa);
                dveIdclienteNew = em.merge(dveIdclienteNew);
            }
            if (dveIddepartamentoOld != null && !dveIddepartamentoOld.equals(dveIddepartamentoNew)) {
                dveIddepartamentoOld.getDventaList().remove(dventa);
                dveIddepartamentoOld = em.merge(dveIddepartamentoOld);
            }
            if (dveIddepartamentoNew != null && !dveIddepartamentoNew.equals(dveIddepartamentoOld)) {
                dveIddepartamentoNew.getDventaList().add(dventa);
                dveIddepartamentoNew = em.merge(dveIddepartamentoNew);
            }
            if (dveIdinventarioOld != null && !dveIdinventarioOld.equals(dveIdinventarioNew)) {
                dveIdinventarioOld.getDventaList().remove(dventa);
                dveIdinventarioOld = em.merge(dveIdinventarioOld);
            }
            if (dveIdinventarioNew != null && !dveIdinventarioNew.equals(dveIdinventarioOld)) {
                dveIdinventarioNew.getDventaList().add(dventa);
                dveIdinventarioNew = em.merge(dveIdinventarioNew);
            }
            if (dveIduserOld != null && !dveIduserOld.equals(dveIduserNew)) {
                dveIduserOld.getDventaList().remove(dventa);
                dveIduserOld = em.merge(dveIduserOld);
            }
            if (dveIduserNew != null && !dveIduserNew.equals(dveIduserOld)) {
                dveIduserNew.getDventaList().add(dventa);
                dveIduserNew = em.merge(dveIduserNew);
            }
            if (dveIdventaOld != null && !dveIdventaOld.equals(dveIdventaNew)) {
                dveIdventaOld.getDventaList().remove(dventa);
                dveIdventaOld = em.merge(dveIdventaOld);
            }
            if (dveIdventaNew != null && !dveIdventaNew.equals(dveIdventaOld)) {
                dveIdventaNew.getDventaList().add(dventa);
                dveIdventaNew = em.merge(dveIdventaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dventa.getDveId();
                if (findDventa(id) == null) {
                    throw new NonexistentEntityException("The dventa with id " + id + " no longer exists.");
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
            Dventa dventa;
            try {
                dventa = em.getReference(Dventa.class, id);
                dventa.getDveId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dventa with id " + id + " no longer exists.", enfe);
            }
            Cliente dveIdcliente = dventa.getDveIdcliente();
            if (dveIdcliente != null) {
                dveIdcliente.getDventaList().remove(dventa);
                dveIdcliente = em.merge(dveIdcliente);
            }
            Departamento dveIddepartamento = dventa.getDveIddepartamento();
            if (dveIddepartamento != null) {
                dveIddepartamento.getDventaList().remove(dventa);
                dveIddepartamento = em.merge(dveIddepartamento);
            }
            Inventario dveIdinventario = dventa.getDveIdinventario();
            if (dveIdinventario != null) {
                dveIdinventario.getDventaList().remove(dventa);
                dveIdinventario = em.merge(dveIdinventario);
            }
            Usuario dveIduser = dventa.getDveIduser();
            if (dveIduser != null) {
                dveIduser.getDventaList().remove(dventa);
                dveIduser = em.merge(dveIduser);
            }
            Venta dveIdventa = dventa.getDveIdventa();
            if (dveIdventa != null) {
                dveIdventa.getDventaList().remove(dventa);
                dveIdventa = em.merge(dveIdventa);
            }
            em.remove(dventa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dventa> findDventaEntities() {
        return findDventaEntities(true, -1, -1);
    }

    public List<Dventa> findDventaEntities(int maxResults, int firstResult) {
        return findDventaEntities(false, maxResults, firstResult);
    }

    private List<Dventa> findDventaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dventa.class));
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

    public Dventa findDventa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dventa.class, id);
        } finally {
            em.close();
        }
    }

    public int getDventaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dventa> rt = cq.from(Dventa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Dventa> getBestProducts() {
        EntityManager em = getEntityManager();
        try {
             List<Dventa> l = em.createNativeQuery("SELECT dve_id,dve_idinventario FROM dventa GROUP by dve_idinventario order by sum(dve_cantidad) desc limit 10",Dventa.class).getResultList();      
            return l;
        }catch(Exception e){            
            return null;
        } finally {
            em.close();
        }
    } 
    
    
}

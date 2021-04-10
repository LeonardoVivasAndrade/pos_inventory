/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Cliente;
import DTO.Usuario;
import DTO.Cajachica;
import java.util.ArrayList;
import java.util.List;
import DTO.Dventa;
import DTO.Venta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) {
        if (venta.getCajachicaList() == null) {
            venta.setCajachicaList(new ArrayList<Cajachica>());
        }
        if (venta.getDventaList() == null) {
            venta.setDventaList(new ArrayList<Dventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente veIdcliente = venta.getVeIdcliente();
            if (veIdcliente != null) {
                veIdcliente = em.getReference(veIdcliente.getClass(), veIdcliente.getClId());
                venta.setVeIdcliente(veIdcliente);
            }
            Usuario veIduser = venta.getVeIduser();
            if (veIduser != null) {
                veIduser = em.getReference(veIduser.getClass(), veIduser.getUsId());
                venta.setVeIduser(veIduser);
            }
            List<Cajachica> attachedCajachicaList = new ArrayList<Cajachica>();
            for (Cajachica cajachicaListCajachicaToAttach : venta.getCajachicaList()) {
                cajachicaListCajachicaToAttach = em.getReference(cajachicaListCajachicaToAttach.getClass(), cajachicaListCajachicaToAttach.getCaId());
                attachedCajachicaList.add(cajachicaListCajachicaToAttach);
            }
            venta.setCajachicaList(attachedCajachicaList);
            List<Dventa> attachedDventaList = new ArrayList<Dventa>();
            for (Dventa dventaListDventaToAttach : venta.getDventaList()) {
                dventaListDventaToAttach = em.getReference(dventaListDventaToAttach.getClass(), dventaListDventaToAttach.getDveId());
                attachedDventaList.add(dventaListDventaToAttach);
            }
            venta.setDventaList(attachedDventaList);
            em.persist(venta);
            if (veIdcliente != null) {
                veIdcliente.getVentaList().add(venta);
                veIdcliente = em.merge(veIdcliente);
            }
            if (veIduser != null) {
                veIduser.getVentaList().add(venta);
                veIduser = em.merge(veIduser);
            }
            for (Cajachica cajachicaListCajachica : venta.getCajachicaList()) {
                Venta oldCaIdventaOfCajachicaListCajachica = cajachicaListCajachica.getCaIdventa();
                cajachicaListCajachica.setCaIdventa(venta);
                cajachicaListCajachica = em.merge(cajachicaListCajachica);
                if (oldCaIdventaOfCajachicaListCajachica != null) {
                    oldCaIdventaOfCajachicaListCajachica.getCajachicaList().remove(cajachicaListCajachica);
                    oldCaIdventaOfCajachicaListCajachica = em.merge(oldCaIdventaOfCajachicaListCajachica);
                }
            }
            for (Dventa dventaListDventa : venta.getDventaList()) {
                Venta oldDveIdventaOfDventaListDventa = dventaListDventa.getDveIdventa();
                dventaListDventa.setDveIdventa(venta);
                dventaListDventa = em.merge(dventaListDventa);
                if (oldDveIdventaOfDventaListDventa != null) {
                    oldDveIdventaOfDventaListDventa.getDventaList().remove(dventaListDventa);
                    oldDveIdventaOfDventaListDventa = em.merge(oldDveIdventaOfDventaListDventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta persistentVenta = em.find(Venta.class, venta.getVeId());
            Cliente veIdclienteOld = persistentVenta.getVeIdcliente();
            Cliente veIdclienteNew = venta.getVeIdcliente();
            Usuario veIduserOld = persistentVenta.getVeIduser();
            Usuario veIduserNew = venta.getVeIduser();
            List<Cajachica> cajachicaListOld = persistentVenta.getCajachicaList();
            List<Cajachica> cajachicaListNew = venta.getCajachicaList();
            List<Dventa> dventaListOld = persistentVenta.getDventaList();
            List<Dventa> dventaListNew = venta.getDventaList();
            List<String> illegalOrphanMessages = null;
            for (Dventa dventaListOldDventa : dventaListOld) {
                if (!dventaListNew.contains(dventaListOldDventa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dventa " + dventaListOldDventa + " since its dveIdventa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (veIdclienteNew != null) {
                veIdclienteNew = em.getReference(veIdclienteNew.getClass(), veIdclienteNew.getClId());
                venta.setVeIdcliente(veIdclienteNew);
            }
            if (veIduserNew != null) {
                veIduserNew = em.getReference(veIduserNew.getClass(), veIduserNew.getUsId());
                venta.setVeIduser(veIduserNew);
            }
            List<Cajachica> attachedCajachicaListNew = new ArrayList<Cajachica>();
            for (Cajachica cajachicaListNewCajachicaToAttach : cajachicaListNew) {
                cajachicaListNewCajachicaToAttach = em.getReference(cajachicaListNewCajachicaToAttach.getClass(), cajachicaListNewCajachicaToAttach.getCaId());
                attachedCajachicaListNew.add(cajachicaListNewCajachicaToAttach);
            }
            cajachicaListNew = attachedCajachicaListNew;
            venta.setCajachicaList(cajachicaListNew);
            List<Dventa> attachedDventaListNew = new ArrayList<Dventa>();
            for (Dventa dventaListNewDventaToAttach : dventaListNew) {
                dventaListNewDventaToAttach = em.getReference(dventaListNewDventaToAttach.getClass(), dventaListNewDventaToAttach.getDveId());
                attachedDventaListNew.add(dventaListNewDventaToAttach);
            }
            dventaListNew = attachedDventaListNew;
            venta.setDventaList(dventaListNew);
            venta = em.merge(venta);
            if (veIdclienteOld != null && !veIdclienteOld.equals(veIdclienteNew)) {
                veIdclienteOld.getVentaList().remove(venta);
                veIdclienteOld = em.merge(veIdclienteOld);
            }
            if (veIdclienteNew != null && !veIdclienteNew.equals(veIdclienteOld)) {
                veIdclienteNew.getVentaList().add(venta);
                veIdclienteNew = em.merge(veIdclienteNew);
            }
            if (veIduserOld != null && !veIduserOld.equals(veIduserNew)) {
                veIduserOld.getVentaList().remove(venta);
                veIduserOld = em.merge(veIduserOld);
            }
            if (veIduserNew != null && !veIduserNew.equals(veIduserOld)) {
                veIduserNew.getVentaList().add(venta);
                veIduserNew = em.merge(veIduserNew);
            }
            for (Cajachica cajachicaListOldCajachica : cajachicaListOld) {
                if (!cajachicaListNew.contains(cajachicaListOldCajachica)) {
                    cajachicaListOldCajachica.setCaIdventa(null);
                    cajachicaListOldCajachica = em.merge(cajachicaListOldCajachica);
                }
            }
            for (Cajachica cajachicaListNewCajachica : cajachicaListNew) {
                if (!cajachicaListOld.contains(cajachicaListNewCajachica)) {
                    Venta oldCaIdventaOfCajachicaListNewCajachica = cajachicaListNewCajachica.getCaIdventa();
                    cajachicaListNewCajachica.setCaIdventa(venta);
                    cajachicaListNewCajachica = em.merge(cajachicaListNewCajachica);
                    if (oldCaIdventaOfCajachicaListNewCajachica != null && !oldCaIdventaOfCajachicaListNewCajachica.equals(venta)) {
                        oldCaIdventaOfCajachicaListNewCajachica.getCajachicaList().remove(cajachicaListNewCajachica);
                        oldCaIdventaOfCajachicaListNewCajachica = em.merge(oldCaIdventaOfCajachicaListNewCajachica);
                    }
                }
            }
            for (Dventa dventaListNewDventa : dventaListNew) {
                if (!dventaListOld.contains(dventaListNewDventa)) {
                    Venta oldDveIdventaOfDventaListNewDventa = dventaListNewDventa.getDveIdventa();
                    dventaListNewDventa.setDveIdventa(venta);
                    dventaListNewDventa = em.merge(dventaListNewDventa);
                    if (oldDveIdventaOfDventaListNewDventa != null && !oldDveIdventaOfDventaListNewDventa.equals(venta)) {
                        oldDveIdventaOfDventaListNewDventa.getDventaList().remove(dventaListNewDventa);
                        oldDveIdventaOfDventaListNewDventa = em.merge(oldDveIdventaOfDventaListNewDventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venta.getVeId();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getVeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<Dventa> dventaListOrphanCheck = venta.getDventaList();
//            for (Dventa dventaListOrphanCheckDventa : dventaListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Venta (" + venta + ") cannot be destroyed since the Dventa " + dventaListOrphanCheckDventa + " in its dventaList field has a non-nullable dveIdventa field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            Cliente veIdcliente = venta.getVeIdcliente();
            if (veIdcliente != null) {
                veIdcliente.getVentaList().remove(venta);
                veIdcliente = em.merge(veIdcliente);
            }
            Usuario veIduser = venta.getVeIduser();
            if (veIduser != null) {
                veIduser.getVentaList().remove(venta);
                veIduser = em.merge(veIduser);
            }
            List<Cajachica> cajachicaList = venta.getCajachicaList();
            for (Cajachica cajachicaListCajachica : cajachicaList) {
                cajachicaListCajachica.setCaIdventa(null);
                cajachicaListCajachica = em.merge(cajachicaListCajachica);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Venta getNumFacLast() {
        EntityManager em = getEntityManager();
        try {
            return (Venta) em.createNativeQuery("Select * from venta order by ve_factventa desc limit 1", Venta.class).getResultList().get(0);
        }catch(Exception e){
            return null;
        } finally {
            em.close();
        }
    }


    public Double getSumPrecioVentaTotal(String date) {
        EntityManager em = getEntityManager();
        try {
            return (Double) em.createNativeQuery("SELECT SUM(ve_preciototal) FROM venta WHERE date(ve_fechaventa)='"+date+"'").getResultList().get(0);
        } finally {
            em.close();
        }
    }
    
    public Double getSumCostoVentaTotal(String date) {
        EntityManager em = getEntityManager();
        try {
            return (Double) em.createNativeQuery("SELECT SUM(ve_costototal) FROM venta WHERE date(ve_fechaventa)='"+date+"'").getResultList().get(0);
        } finally {
            em.close();
        }
    }
    
}

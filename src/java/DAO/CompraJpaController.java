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
import DTO.Proveedor;
import DTO.Usuario;
import DTO.Cajachica;
import DTO.Compra;
import java.util.ArrayList;
import java.util.List;
import DTO.Dcompra;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class CompraJpaController implements Serializable {

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) {
        if (compra.getCajachicaList() == null) {
            compra.setCajachicaList(new ArrayList<Cajachica>());
        }
        if (compra.getDcompraList() == null) {
            compra.setDcompraList(new ArrayList<Dcompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor coIdproveedor = compra.getCoIdproveedor();
            if (coIdproveedor != null) {
                coIdproveedor = em.getReference(coIdproveedor.getClass(), coIdproveedor.getPrId());
                compra.setCoIdproveedor(coIdproveedor);
            }
            Usuario coIduser = compra.getCoIduser();
            if (coIduser != null) {
                coIduser = em.getReference(coIduser.getClass(), coIduser.getUsId());
                compra.setCoIduser(coIduser);
            }
            List<Cajachica> attachedCajachicaList = new ArrayList<Cajachica>();
            for (Cajachica cajachicaListCajachicaToAttach : compra.getCajachicaList()) {
                cajachicaListCajachicaToAttach = em.getReference(cajachicaListCajachicaToAttach.getClass(), cajachicaListCajachicaToAttach.getCaId());
                attachedCajachicaList.add(cajachicaListCajachicaToAttach);
            }
            compra.setCajachicaList(attachedCajachicaList);
            List<Dcompra> attachedDcompraList = new ArrayList<Dcompra>();
            for (Dcompra dcompraListDcompraToAttach : compra.getDcompraList()) {
                dcompraListDcompraToAttach = em.getReference(dcompraListDcompraToAttach.getClass(), dcompraListDcompraToAttach.getDcoId());
                attachedDcompraList.add(dcompraListDcompraToAttach);
            }
            compra.setDcompraList(attachedDcompraList);
            em.persist(compra);
            if (coIdproveedor != null) {
                coIdproveedor.getCompraList().add(compra);
                coIdproveedor = em.merge(coIdproveedor);
            }
            if (coIduser != null) {
                coIduser.getCompraList().add(compra);
                coIduser = em.merge(coIduser);
            }
            for (Cajachica cajachicaListCajachica : compra.getCajachicaList()) {
                Compra oldCaIdcompraOfCajachicaListCajachica = cajachicaListCajachica.getCaIdcompra();
                cajachicaListCajachica.setCaIdcompra(compra);
                cajachicaListCajachica = em.merge(cajachicaListCajachica);
                if (oldCaIdcompraOfCajachicaListCajachica != null) {
                    oldCaIdcompraOfCajachicaListCajachica.getCajachicaList().remove(cajachicaListCajachica);
                    oldCaIdcompraOfCajachicaListCajachica = em.merge(oldCaIdcompraOfCajachicaListCajachica);
                }
            }
            for (Dcompra dcompraListDcompra : compra.getDcompraList()) {
                Compra oldDcoIdcompraOfDcompraListDcompra = dcompraListDcompra.getDcoIdcompra();
                dcompraListDcompra.setDcoIdcompra(compra);
                dcompraListDcompra = em.merge(dcompraListDcompra);
                if (oldDcoIdcompraOfDcompraListDcompra != null) {
                    oldDcoIdcompraOfDcompraListDcompra.getDcompraList().remove(dcompraListDcompra);
                    oldDcoIdcompraOfDcompraListDcompra = em.merge(oldDcoIdcompraOfDcompraListDcompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compra) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra persistentCompra = em.find(Compra.class, compra.getCoId());
            Proveedor coIdproveedorOld = persistentCompra.getCoIdproveedor();
            Proveedor coIdproveedorNew = compra.getCoIdproveedor();
            Usuario coIduserOld = persistentCompra.getCoIduser();
            Usuario coIduserNew = compra.getCoIduser();
            List<Cajachica> cajachicaListOld = persistentCompra.getCajachicaList();
            List<Cajachica> cajachicaListNew = compra.getCajachicaList();
            List<Dcompra> dcompraListOld = persistentCompra.getDcompraList();
            List<Dcompra> dcompraListNew = compra.getDcompraList();
            List<String> illegalOrphanMessages = null;
            for (Dcompra dcompraListOldDcompra : dcompraListOld) {
                if (!dcompraListNew.contains(dcompraListOldDcompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dcompra " + dcompraListOldDcompra + " since its dcoIdcompra field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (coIdproveedorNew != null) {
                coIdproveedorNew = em.getReference(coIdproveedorNew.getClass(), coIdproveedorNew.getPrId());
                compra.setCoIdproveedor(coIdproveedorNew);
            }
            if (coIduserNew != null) {
                coIduserNew = em.getReference(coIduserNew.getClass(), coIduserNew.getUsId());
                compra.setCoIduser(coIduserNew);
            }
            List<Cajachica> attachedCajachicaListNew = new ArrayList<Cajachica>();
            for (Cajachica cajachicaListNewCajachicaToAttach : cajachicaListNew) {
                cajachicaListNewCajachicaToAttach = em.getReference(cajachicaListNewCajachicaToAttach.getClass(), cajachicaListNewCajachicaToAttach.getCaId());
                attachedCajachicaListNew.add(cajachicaListNewCajachicaToAttach);
            }
            cajachicaListNew = attachedCajachicaListNew;
            compra.setCajachicaList(cajachicaListNew);
            List<Dcompra> attachedDcompraListNew = new ArrayList<Dcompra>();
            for (Dcompra dcompraListNewDcompraToAttach : dcompraListNew) {
                dcompraListNewDcompraToAttach = em.getReference(dcompraListNewDcompraToAttach.getClass(), dcompraListNewDcompraToAttach.getDcoId());
                attachedDcompraListNew.add(dcompraListNewDcompraToAttach);
            }
            dcompraListNew = attachedDcompraListNew;
            compra.setDcompraList(dcompraListNew);
            compra = em.merge(compra);
            if (coIdproveedorOld != null && !coIdproveedorOld.equals(coIdproveedorNew)) {
                coIdproveedorOld.getCompraList().remove(compra);
                coIdproveedorOld = em.merge(coIdproveedorOld);
            }
            if (coIdproveedorNew != null && !coIdproveedorNew.equals(coIdproveedorOld)) {
                coIdproveedorNew.getCompraList().add(compra);
                coIdproveedorNew = em.merge(coIdproveedorNew);
            }
            if (coIduserOld != null && !coIduserOld.equals(coIduserNew)) {
                coIduserOld.getCompraList().remove(compra);
                coIduserOld = em.merge(coIduserOld);
            }
            if (coIduserNew != null && !coIduserNew.equals(coIduserOld)) {
                coIduserNew.getCompraList().add(compra);
                coIduserNew = em.merge(coIduserNew);
            }
            for (Cajachica cajachicaListOldCajachica : cajachicaListOld) {
                if (!cajachicaListNew.contains(cajachicaListOldCajachica)) {
                    cajachicaListOldCajachica.setCaIdcompra(null);
                    cajachicaListOldCajachica = em.merge(cajachicaListOldCajachica);
                }
            }
            for (Cajachica cajachicaListNewCajachica : cajachicaListNew) {
                if (!cajachicaListOld.contains(cajachicaListNewCajachica)) {
                    Compra oldCaIdcompraOfCajachicaListNewCajachica = cajachicaListNewCajachica.getCaIdcompra();
                    cajachicaListNewCajachica.setCaIdcompra(compra);
                    cajachicaListNewCajachica = em.merge(cajachicaListNewCajachica);
                    if (oldCaIdcompraOfCajachicaListNewCajachica != null && !oldCaIdcompraOfCajachicaListNewCajachica.equals(compra)) {
                        oldCaIdcompraOfCajachicaListNewCajachica.getCajachicaList().remove(cajachicaListNewCajachica);
                        oldCaIdcompraOfCajachicaListNewCajachica = em.merge(oldCaIdcompraOfCajachicaListNewCajachica);
                    }
                }
            }
            for (Dcompra dcompraListNewDcompra : dcompraListNew) {
                if (!dcompraListOld.contains(dcompraListNewDcompra)) {
                    Compra oldDcoIdcompraOfDcompraListNewDcompra = dcompraListNewDcompra.getDcoIdcompra();
                    dcompraListNewDcompra.setDcoIdcompra(compra);
                    dcompraListNewDcompra = em.merge(dcompraListNewDcompra);
                    if (oldDcoIdcompraOfDcompraListNewDcompra != null && !oldDcoIdcompraOfDcompraListNewDcompra.equals(compra)) {
                        oldDcoIdcompraOfDcompraListNewDcompra.getDcompraList().remove(dcompraListNewDcompra);
                        oldDcoIdcompraOfDcompraListNewDcompra = em.merge(oldDcoIdcompraOfDcompraListNewDcompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compra.getCoId();
                if (findCompra(id) == null) {
                    throw new NonexistentEntityException("The compra with id " + id + " no longer exists.");
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
            Compra compra;
            try {
                compra = em.getReference(Compra.class, id);
                compra.getCoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compra with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Dcompra> dcompraListOrphanCheck = compra.getDcompraList();
            for (Dcompra dcompraListOrphanCheckDcompra : dcompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Compra (" + compra + ") cannot be destroyed since the Dcompra " + dcompraListOrphanCheckDcompra + " in its dcompraList field has a non-nullable dcoIdcompra field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proveedor coIdproveedor = compra.getCoIdproveedor();
            if (coIdproveedor != null) {
                coIdproveedor.getCompraList().remove(compra);
                coIdproveedor = em.merge(coIdproveedor);
            }
            Usuario coIduser = compra.getCoIduser();
            if (coIduser != null) {
                coIduser.getCompraList().remove(compra);
                coIduser = em.merge(coIduser);
            }
            List<Cajachica> cajachicaList = compra.getCajachicaList();
            for (Cajachica cajachicaListCajachica : cajachicaList) {
                cajachicaListCajachica.setCaIdcompra(null);
                cajachicaListCajachica = em.merge(cajachicaListCajachica);
            }
            em.remove(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompraEntities() {
        return findCompraEntities(true, -1, -1);
    }

    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        return findCompraEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
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

    public Compra findCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Compra getNumFacLast() {
        EntityManager em = getEntityManager();
        try {
            return (Compra) em.createNativeQuery("Select * from compra order by co_factcompra desc limit 1", Compra.class).getResultList().get(0);
        }catch(Exception e){
            return null;
        } finally {
            em.close();
        }
    }  
    
}

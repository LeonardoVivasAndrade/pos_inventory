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
import DTO.Usuario;
import DTO.Compra;
import java.util.ArrayList;
import java.util.List;
import DTO.Dcompra;
import DTO.Proveedor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) {
        if (proveedor.getCompraList() == null) {
            proveedor.setCompraList(new ArrayList<Compra>());
        }
        if (proveedor.getDcompraList() == null) {
            proveedor.setDcompraList(new ArrayList<Dcompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario prIduser = proveedor.getPrIduser();
            if (prIduser != null) {
                prIduser = em.getReference(prIduser.getClass(), prIduser.getUsId());
                proveedor.setPrIduser(prIduser);
            }
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : proveedor.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getCoId());
                attachedCompraList.add(compraListCompraToAttach);
            }
            proveedor.setCompraList(attachedCompraList);
            List<Dcompra> attachedDcompraList = new ArrayList<Dcompra>();
            for (Dcompra dcompraListDcompraToAttach : proveedor.getDcompraList()) {
                dcompraListDcompraToAttach = em.getReference(dcompraListDcompraToAttach.getClass(), dcompraListDcompraToAttach.getDcoId());
                attachedDcompraList.add(dcompraListDcompraToAttach);
            }
            proveedor.setDcompraList(attachedDcompraList);
            em.persist(proveedor);
            if (prIduser != null) {
                prIduser.getProveedorList().add(proveedor);
                prIduser = em.merge(prIduser);
            }
            for (Compra compraListCompra : proveedor.getCompraList()) {
                Proveedor oldCoIdproveedorOfCompraListCompra = compraListCompra.getCoIdproveedor();
                compraListCompra.setCoIdproveedor(proveedor);
                compraListCompra = em.merge(compraListCompra);
                if (oldCoIdproveedorOfCompraListCompra != null) {
                    oldCoIdproveedorOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldCoIdproveedorOfCompraListCompra = em.merge(oldCoIdproveedorOfCompraListCompra);
                }
            }
            for (Dcompra dcompraListDcompra : proveedor.getDcompraList()) {
                Proveedor oldDcoIdproveedorOfDcompraListDcompra = dcompraListDcompra.getDcoIdproveedor();
                dcompraListDcompra.setDcoIdproveedor(proveedor);
                dcompraListDcompra = em.merge(dcompraListDcompra);
                if (oldDcoIdproveedorOfDcompraListDcompra != null) {
                    oldDcoIdproveedorOfDcompraListDcompra.getDcompraList().remove(dcompraListDcompra);
                    oldDcoIdproveedorOfDcompraListDcompra = em.merge(oldDcoIdproveedorOfDcompraListDcompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getPrId());
            Usuario prIduserOld = persistentProveedor.getPrIduser();
            Usuario prIduserNew = proveedor.getPrIduser();
            List<Compra> compraListOld = persistentProveedor.getCompraList();
            List<Compra> compraListNew = proveedor.getCompraList();
            List<Dcompra> dcompraListOld = persistentProveedor.getDcompraList();
            List<Dcompra> dcompraListNew = proveedor.getDcompraList();
            List<String> illegalOrphanMessages = null;
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraListOldCompra + " since its coIdproveedor field is not nullable.");
                }
            }
            for (Dcompra dcompraListOldDcompra : dcompraListOld) {
                if (!dcompraListNew.contains(dcompraListOldDcompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dcompra " + dcompraListOldDcompra + " since its dcoIdproveedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (prIduserNew != null) {
                prIduserNew = em.getReference(prIduserNew.getClass(), prIduserNew.getUsId());
                proveedor.setPrIduser(prIduserNew);
            }
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getCoId());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            proveedor.setCompraList(compraListNew);
            List<Dcompra> attachedDcompraListNew = new ArrayList<Dcompra>();
            for (Dcompra dcompraListNewDcompraToAttach : dcompraListNew) {
                dcompraListNewDcompraToAttach = em.getReference(dcompraListNewDcompraToAttach.getClass(), dcompraListNewDcompraToAttach.getDcoId());
                attachedDcompraListNew.add(dcompraListNewDcompraToAttach);
            }
            dcompraListNew = attachedDcompraListNew;
            proveedor.setDcompraList(dcompraListNew);
            proveedor = em.merge(proveedor);
            if (prIduserOld != null && !prIduserOld.equals(prIduserNew)) {
                prIduserOld.getProveedorList().remove(proveedor);
                prIduserOld = em.merge(prIduserOld);
            }
            if (prIduserNew != null && !prIduserNew.equals(prIduserOld)) {
                prIduserNew.getProveedorList().add(proveedor);
                prIduserNew = em.merge(prIduserNew);
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Proveedor oldCoIdproveedorOfCompraListNewCompra = compraListNewCompra.getCoIdproveedor();
                    compraListNewCompra.setCoIdproveedor(proveedor);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldCoIdproveedorOfCompraListNewCompra != null && !oldCoIdproveedorOfCompraListNewCompra.equals(proveedor)) {
                        oldCoIdproveedorOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldCoIdproveedorOfCompraListNewCompra = em.merge(oldCoIdproveedorOfCompraListNewCompra);
                    }
                }
            }
            for (Dcompra dcompraListNewDcompra : dcompraListNew) {
                if (!dcompraListOld.contains(dcompraListNewDcompra)) {
                    Proveedor oldDcoIdproveedorOfDcompraListNewDcompra = dcompraListNewDcompra.getDcoIdproveedor();
                    dcompraListNewDcompra.setDcoIdproveedor(proveedor);
                    dcompraListNewDcompra = em.merge(dcompraListNewDcompra);
                    if (oldDcoIdproveedorOfDcompraListNewDcompra != null && !oldDcoIdproveedorOfDcompraListNewDcompra.equals(proveedor)) {
                        oldDcoIdproveedorOfDcompraListNewDcompra.getDcompraList().remove(dcompraListNewDcompra);
                        oldDcoIdproveedorOfDcompraListNewDcompra = em.merge(oldDcoIdproveedorOfDcompraListNewDcompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getPrId();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getPrId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Compra> compraListOrphanCheck = proveedor.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable coIdproveedor field.");
            }
            List<Dcompra> dcompraListOrphanCheck = proveedor.getDcompraList();
            for (Dcompra dcompraListOrphanCheckDcompra : dcompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the Dcompra " + dcompraListOrphanCheckDcompra + " in its dcompraList field has a non-nullable dcoIdproveedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario prIduser = proveedor.getPrIduser();
            if (prIduser != null) {
                prIduser.getProveedorList().remove(proveedor);
                prIduser = em.merge(prIduser);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

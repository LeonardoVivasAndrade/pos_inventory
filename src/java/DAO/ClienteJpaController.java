/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Usuario;
import DTO.Venta;
import java.util.ArrayList;
import java.util.List;
import DTO.Dventa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getVentaList() == null) {
            cliente.setVentaList(new ArrayList<Venta>());
        }
        if (cliente.getDventaList() == null) {
            cliente.setDventaList(new ArrayList<Dventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario clIduser = cliente.getClIduser();
            if (clIduser != null) {
                clIduser = em.getReference(clIduser.getClass(), clIduser.getUsId());
                cliente.setClIduser(clIduser);
            }
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : cliente.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getVeId());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            cliente.setVentaList(attachedVentaList);
            List<Dventa> attachedDventaList = new ArrayList<Dventa>();
            for (Dventa dventaListDventaToAttach : cliente.getDventaList()) {
                dventaListDventaToAttach = em.getReference(dventaListDventaToAttach.getClass(), dventaListDventaToAttach.getDveId());
                attachedDventaList.add(dventaListDventaToAttach);
            }
            cliente.setDventaList(attachedDventaList);
            em.persist(cliente);
            if (clIduser != null) {
                clIduser.getClienteList().add(cliente);
                clIduser = em.merge(clIduser);
            }
            for (Venta ventaListVenta : cliente.getVentaList()) {
                Cliente oldVeIdclienteOfVentaListVenta = ventaListVenta.getVeIdcliente();
                ventaListVenta.setVeIdcliente(cliente);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldVeIdclienteOfVentaListVenta != null) {
                    oldVeIdclienteOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldVeIdclienteOfVentaListVenta = em.merge(oldVeIdclienteOfVentaListVenta);
                }
            }
            for (Dventa dventaListDventa : cliente.getDventaList()) {
                Cliente oldDveIdclienteOfDventaListDventa = dventaListDventa.getDveIdcliente();
                dventaListDventa.setDveIdcliente(cliente);
                dventaListDventa = em.merge(dventaListDventa);
                if (oldDveIdclienteOfDventaListDventa != null) {
                    oldDveIdclienteOfDventaListDventa.getDventaList().remove(dventaListDventa);
                    oldDveIdclienteOfDventaListDventa = em.merge(oldDveIdclienteOfDventaListDventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getClId());
            Usuario clIduserOld = persistentCliente.getClIduser();
            Usuario clIduserNew = cliente.getClIduser();
            List<Venta> ventaListOld = persistentCliente.getVentaList();
            List<Venta> ventaListNew = cliente.getVentaList();
            List<Dventa> dventaListOld = persistentCliente.getDventaList();
            List<Dventa> dventaListNew = cliente.getDventaList();
            List<String> illegalOrphanMessages = null;
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Venta " + ventaListOldVenta + " since its veIdcliente field is not nullable.");
                }
            }
            for (Dventa dventaListOldDventa : dventaListOld) {
                if (!dventaListNew.contains(dventaListOldDventa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dventa " + dventaListOldDventa + " since its dveIdcliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clIduserNew != null) {
                clIduserNew = em.getReference(clIduserNew.getClass(), clIduserNew.getUsId());
                cliente.setClIduser(clIduserNew);
            }
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getVeId());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            cliente.setVentaList(ventaListNew);
            List<Dventa> attachedDventaListNew = new ArrayList<Dventa>();
            for (Dventa dventaListNewDventaToAttach : dventaListNew) {
                dventaListNewDventaToAttach = em.getReference(dventaListNewDventaToAttach.getClass(), dventaListNewDventaToAttach.getDveId());
                attachedDventaListNew.add(dventaListNewDventaToAttach);
            }
            dventaListNew = attachedDventaListNew;
            cliente.setDventaList(dventaListNew);
            cliente = em.merge(cliente);
            if (clIduserOld != null && !clIduserOld.equals(clIduserNew)) {
                clIduserOld.getClienteList().remove(cliente);
                clIduserOld = em.merge(clIduserOld);
            }
            if (clIduserNew != null && !clIduserNew.equals(clIduserOld)) {
                clIduserNew.getClienteList().add(cliente);
                clIduserNew = em.merge(clIduserNew);
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Cliente oldVeIdclienteOfVentaListNewVenta = ventaListNewVenta.getVeIdcliente();
                    ventaListNewVenta.setVeIdcliente(cliente);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldVeIdclienteOfVentaListNewVenta != null && !oldVeIdclienteOfVentaListNewVenta.equals(cliente)) {
                        oldVeIdclienteOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldVeIdclienteOfVentaListNewVenta = em.merge(oldVeIdclienteOfVentaListNewVenta);
                    }
                }
            }
            for (Dventa dventaListNewDventa : dventaListNew) {
                if (!dventaListOld.contains(dventaListNewDventa)) {
                    Cliente oldDveIdclienteOfDventaListNewDventa = dventaListNewDventa.getDveIdcliente();
                    dventaListNewDventa.setDveIdcliente(cliente);
                    dventaListNewDventa = em.merge(dventaListNewDventa);
                    if (oldDveIdclienteOfDventaListNewDventa != null && !oldDveIdclienteOfDventaListNewDventa.equals(cliente)) {
                        oldDveIdclienteOfDventaListNewDventa.getDventaList().remove(dventaListNewDventa);
                        oldDveIdclienteOfDventaListNewDventa = em.merge(oldDveIdclienteOfDventaListNewDventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getClId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getClId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Venta> ventaListOrphanCheck = cliente.getVentaList();
            for (Venta ventaListOrphanCheckVenta : ventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Venta " + ventaListOrphanCheckVenta + " in its ventaList field has a non-nullable veIdcliente field.");
            }
            List<Dventa> dventaListOrphanCheck = cliente.getDventaList();
            for (Dventa dventaListOrphanCheckDventa : dventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Dventa " + dventaListOrphanCheckDventa + " in its dventaList field has a non-nullable dveIdcliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario clIduser = cliente.getClIduser();
            if (clIduser != null) {
                clIduser.getClienteList().remove(cliente);
                clIduser = em.merge(clIduser);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

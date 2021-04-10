/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Departamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Usuario;
import DTO.Dventa;
import java.util.ArrayList;
import java.util.List;
import DTO.Stock;
import DTO.Inventario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getDventaList() == null) {
            departamento.setDventaList(new ArrayList<Dventa>());
        }
        if (departamento.getStockList() == null) {
            departamento.setStockList(new ArrayList<Stock>());
        }
        if (departamento.getInventarioList() == null) {
            departamento.setInventarioList(new ArrayList<Inventario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario dpIduser = departamento.getDpIduser();
            if (dpIduser != null) {
                dpIduser = em.getReference(dpIduser.getClass(), dpIduser.getUsId());
                departamento.setDpIduser(dpIduser);
            }
            List<Dventa> attachedDventaList = new ArrayList<Dventa>();
            for (Dventa dventaListDventaToAttach : departamento.getDventaList()) {
                dventaListDventaToAttach = em.getReference(dventaListDventaToAttach.getClass(), dventaListDventaToAttach.getDveId());
                attachedDventaList.add(dventaListDventaToAttach);
            }
            departamento.setDventaList(attachedDventaList);
            List<Stock> attachedStockList = new ArrayList<Stock>();
            for (Stock stockListStockToAttach : departamento.getStockList()) {
                stockListStockToAttach = em.getReference(stockListStockToAttach.getClass(), stockListStockToAttach.getStIdinventario());
                attachedStockList.add(stockListStockToAttach);
            }
            departamento.setStockList(attachedStockList);
            List<Inventario> attachedInventarioList = new ArrayList<Inventario>();
            for (Inventario inventarioListInventarioToAttach : departamento.getInventarioList()) {
                inventarioListInventarioToAttach = em.getReference(inventarioListInventarioToAttach.getClass(), inventarioListInventarioToAttach.getInId());
                attachedInventarioList.add(inventarioListInventarioToAttach);
            }
            departamento.setInventarioList(attachedInventarioList);
            em.persist(departamento);
            if (dpIduser != null) {
                dpIduser.getDepartamentoList().add(departamento);
                dpIduser = em.merge(dpIduser);
            }
            for (Dventa dventaListDventa : departamento.getDventaList()) {
                Departamento oldDveIddepartamentoOfDventaListDventa = dventaListDventa.getDveIddepartamento();
                dventaListDventa.setDveIddepartamento(departamento);
                dventaListDventa = em.merge(dventaListDventa);
                if (oldDveIddepartamentoOfDventaListDventa != null) {
                    oldDveIddepartamentoOfDventaListDventa.getDventaList().remove(dventaListDventa);
                    oldDveIddepartamentoOfDventaListDventa = em.merge(oldDveIddepartamentoOfDventaListDventa);
                }
            }
            for (Stock stockListStock : departamento.getStockList()) {
                Departamento oldStIddepartamentoOfStockListStock = stockListStock.getStIddepartamento();
                stockListStock.setStIddepartamento(departamento);
                stockListStock = em.merge(stockListStock);
                if (oldStIddepartamentoOfStockListStock != null) {
                    oldStIddepartamentoOfStockListStock.getStockList().remove(stockListStock);
                    oldStIddepartamentoOfStockListStock = em.merge(oldStIddepartamentoOfStockListStock);
                }
            }
            for (Inventario inventarioListInventario : departamento.getInventarioList()) {
                Departamento oldInIddepartamentoOfInventarioListInventario = inventarioListInventario.getInIddepartamento();
                inventarioListInventario.setInIddepartamento(departamento);
                inventarioListInventario = em.merge(inventarioListInventario);
                if (oldInIddepartamentoOfInventarioListInventario != null) {
                    oldInIddepartamentoOfInventarioListInventario.getInventarioList().remove(inventarioListInventario);
                    oldInIddepartamentoOfInventarioListInventario = em.merge(oldInIddepartamentoOfInventarioListInventario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getDpId());
            Usuario dpIduserOld = persistentDepartamento.getDpIduser();
            Usuario dpIduserNew = departamento.getDpIduser();
            List<Dventa> dventaListOld = persistentDepartamento.getDventaList();
            List<Dventa> dventaListNew = departamento.getDventaList();
            List<Stock> stockListOld = persistentDepartamento.getStockList();
            List<Stock> stockListNew = departamento.getStockList();
            List<Inventario> inventarioListOld = persistentDepartamento.getInventarioList();
            List<Inventario> inventarioListNew = departamento.getInventarioList();
            List<String> illegalOrphanMessages = null;
            for (Dventa dventaListOldDventa : dventaListOld) {
                if (!dventaListNew.contains(dventaListOldDventa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dventa " + dventaListOldDventa + " since its dveIddepartamento field is not nullable.");
                }
            }
            for (Stock stockListOldStock : stockListOld) {
                if (!stockListNew.contains(stockListOldStock)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Stock " + stockListOldStock + " since its stIddepartamento field is not nullable.");
                }
            }
            for (Inventario inventarioListOldInventario : inventarioListOld) {
                if (!inventarioListNew.contains(inventarioListOldInventario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inventario " + inventarioListOldInventario + " since its inIddepartamento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (dpIduserNew != null) {
                dpIduserNew = em.getReference(dpIduserNew.getClass(), dpIduserNew.getUsId());
                departamento.setDpIduser(dpIduserNew);
            }
            List<Dventa> attachedDventaListNew = new ArrayList<Dventa>();
            for (Dventa dventaListNewDventaToAttach : dventaListNew) {
                dventaListNewDventaToAttach = em.getReference(dventaListNewDventaToAttach.getClass(), dventaListNewDventaToAttach.getDveId());
                attachedDventaListNew.add(dventaListNewDventaToAttach);
            }
            dventaListNew = attachedDventaListNew;
            departamento.setDventaList(dventaListNew);
            List<Stock> attachedStockListNew = new ArrayList<Stock>();
            for (Stock stockListNewStockToAttach : stockListNew) {
                stockListNewStockToAttach = em.getReference(stockListNewStockToAttach.getClass(), stockListNewStockToAttach.getStIdinventario());
                attachedStockListNew.add(stockListNewStockToAttach);
            }
            stockListNew = attachedStockListNew;
            departamento.setStockList(stockListNew);
            List<Inventario> attachedInventarioListNew = new ArrayList<Inventario>();
            for (Inventario inventarioListNewInventarioToAttach : inventarioListNew) {
                inventarioListNewInventarioToAttach = em.getReference(inventarioListNewInventarioToAttach.getClass(), inventarioListNewInventarioToAttach.getInId());
                attachedInventarioListNew.add(inventarioListNewInventarioToAttach);
            }
            inventarioListNew = attachedInventarioListNew;
            departamento.setInventarioList(inventarioListNew);
            departamento = em.merge(departamento);
            if (dpIduserOld != null && !dpIduserOld.equals(dpIduserNew)) {
                dpIduserOld.getDepartamentoList().remove(departamento);
                dpIduserOld = em.merge(dpIduserOld);
            }
            if (dpIduserNew != null && !dpIduserNew.equals(dpIduserOld)) {
                dpIduserNew.getDepartamentoList().add(departamento);
                dpIduserNew = em.merge(dpIduserNew);
            }
            for (Dventa dventaListNewDventa : dventaListNew) {
                if (!dventaListOld.contains(dventaListNewDventa)) {
                    Departamento oldDveIddepartamentoOfDventaListNewDventa = dventaListNewDventa.getDveIddepartamento();
                    dventaListNewDventa.setDveIddepartamento(departamento);
                    dventaListNewDventa = em.merge(dventaListNewDventa);
                    if (oldDveIddepartamentoOfDventaListNewDventa != null && !oldDveIddepartamentoOfDventaListNewDventa.equals(departamento)) {
                        oldDveIddepartamentoOfDventaListNewDventa.getDventaList().remove(dventaListNewDventa);
                        oldDveIddepartamentoOfDventaListNewDventa = em.merge(oldDveIddepartamentoOfDventaListNewDventa);
                    }
                }
            }
            for (Stock stockListNewStock : stockListNew) {
                if (!stockListOld.contains(stockListNewStock)) {
                    Departamento oldStIddepartamentoOfStockListNewStock = stockListNewStock.getStIddepartamento();
                    stockListNewStock.setStIddepartamento(departamento);
                    stockListNewStock = em.merge(stockListNewStock);
                    if (oldStIddepartamentoOfStockListNewStock != null && !oldStIddepartamentoOfStockListNewStock.equals(departamento)) {
                        oldStIddepartamentoOfStockListNewStock.getStockList().remove(stockListNewStock);
                        oldStIddepartamentoOfStockListNewStock = em.merge(oldStIddepartamentoOfStockListNewStock);
                    }
                }
            }
            for (Inventario inventarioListNewInventario : inventarioListNew) {
                if (!inventarioListOld.contains(inventarioListNewInventario)) {
                    Departamento oldInIddepartamentoOfInventarioListNewInventario = inventarioListNewInventario.getInIddepartamento();
                    inventarioListNewInventario.setInIddepartamento(departamento);
                    inventarioListNewInventario = em.merge(inventarioListNewInventario);
                    if (oldInIddepartamentoOfInventarioListNewInventario != null && !oldInIddepartamentoOfInventarioListNewInventario.equals(departamento)) {
                        oldInIddepartamentoOfInventarioListNewInventario.getInventarioList().remove(inventarioListNewInventario);
                        oldInIddepartamentoOfInventarioListNewInventario = em.merge(oldInIddepartamentoOfInventarioListNewInventario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamento.getDpId();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getDpId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Dventa> dventaListOrphanCheck = departamento.getDventaList();
            for (Dventa dventaListOrphanCheckDventa : dventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Dventa " + dventaListOrphanCheckDventa + " in its dventaList field has a non-nullable dveIddepartamento field.");
            }
            List<Stock> stockListOrphanCheck = departamento.getStockList();
            for (Stock stockListOrphanCheckStock : stockListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Stock " + stockListOrphanCheckStock + " in its stockList field has a non-nullable stIddepartamento field.");
            }
            List<Inventario> inventarioListOrphanCheck = departamento.getInventarioList();
            for (Inventario inventarioListOrphanCheckInventario : inventarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Inventario " + inventarioListOrphanCheckInventario + " in its inventarioList field has a non-nullable inIddepartamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario dpIduser = departamento.getDpIduser();
            if (dpIduser != null) {
                dpIduser.getDepartamentoList().remove(departamento);
                dpIduser = em.merge(dpIduser);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Departamento findDepartamentoByDescripcion(String descripcion) {
        EntityManager em = getEntityManager();
        try {
            return (Departamento) em.createNamedQuery("Departamento.findByDpDescripcion", Departamento.class).setParameter("dpDescripcion", descripcion).getResultList().get(0);
        }catch(Exception e){
             return null;
        }finally {
            em.close();           
        }
    }
    
}

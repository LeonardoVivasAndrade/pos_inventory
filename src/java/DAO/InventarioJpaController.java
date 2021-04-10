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
import DTO.Stock;
import DTO.Departamento;
import DTO.Usuario;
import DTO.Dventa;
import java.util.ArrayList;
import java.util.List;
import DTO.Dcompra;
import DTO.Inventario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class InventarioJpaController implements Serializable {

    public InventarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inventario inventario) {
        if (inventario.getDventaList() == null) {
            inventario.setDventaList(new ArrayList<Dventa>());
        }
        if (inventario.getDcompraList() == null) {
            inventario.setDcompraList(new ArrayList<Dcompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Stock stock = inventario.getStock();
            if (stock != null) {
                stock = em.getReference(stock.getClass(), stock.getStIdinventario());
                inventario.setStock(stock);
            }
            Departamento inIddepartamento = inventario.getInIddepartamento();
            if (inIddepartamento != null) {
                inIddepartamento = em.getReference(inIddepartamento.getClass(), inIddepartamento.getDpId());
                inventario.setInIddepartamento(inIddepartamento);
            }
            Usuario inIduser = inventario.getInIduser();
            if (inIduser != null) {
                inIduser = em.getReference(inIduser.getClass(), inIduser.getUsId());
                inventario.setInIduser(inIduser);
            }
            List<Dventa> attachedDventaList = new ArrayList<Dventa>();
            for (Dventa dventaListDventaToAttach : inventario.getDventaList()) {
                dventaListDventaToAttach = em.getReference(dventaListDventaToAttach.getClass(), dventaListDventaToAttach.getDveId());
                attachedDventaList.add(dventaListDventaToAttach);
            }
            inventario.setDventaList(attachedDventaList);
            List<Dcompra> attachedDcompraList = new ArrayList<Dcompra>();
            for (Dcompra dcompraListDcompraToAttach : inventario.getDcompraList()) {
                dcompraListDcompraToAttach = em.getReference(dcompraListDcompraToAttach.getClass(), dcompraListDcompraToAttach.getDcoId());
                attachedDcompraList.add(dcompraListDcompraToAttach);
            }
            inventario.setDcompraList(attachedDcompraList);
            em.persist(inventario);
            if (stock != null) {
                Inventario oldInventarioOfStock = stock.getInventario();
                if (oldInventarioOfStock != null) {
                    oldInventarioOfStock.setStock(null);
                    oldInventarioOfStock = em.merge(oldInventarioOfStock);
                }
                stock.setInventario(inventario);
                stock = em.merge(stock);
            }
            if (inIddepartamento != null) {
                inIddepartamento.getInventarioList().add(inventario);
                inIddepartamento = em.merge(inIddepartamento);
            }
            if (inIduser != null) {
                inIduser.getInventarioList().add(inventario);
                inIduser = em.merge(inIduser);
            }
            for (Dventa dventaListDventa : inventario.getDventaList()) {
                Inventario oldDveIdinventarioOfDventaListDventa = dventaListDventa.getDveIdinventario();
                dventaListDventa.setDveIdinventario(inventario);
                dventaListDventa = em.merge(dventaListDventa);
                if (oldDveIdinventarioOfDventaListDventa != null) {
                    oldDveIdinventarioOfDventaListDventa.getDventaList().remove(dventaListDventa);
                    oldDveIdinventarioOfDventaListDventa = em.merge(oldDveIdinventarioOfDventaListDventa);
                }
            }
            for (Dcompra dcompraListDcompra : inventario.getDcompraList()) {
                Inventario oldDcoIdinventarioOfDcompraListDcompra = dcompraListDcompra.getDcoIdinventario();
                dcompraListDcompra.setDcoIdinventario(inventario);
                dcompraListDcompra = em.merge(dcompraListDcompra);
                if (oldDcoIdinventarioOfDcompraListDcompra != null) {
                    oldDcoIdinventarioOfDcompraListDcompra.getDcompraList().remove(dcompraListDcompra);
                    oldDcoIdinventarioOfDcompraListDcompra = em.merge(oldDcoIdinventarioOfDcompraListDcompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inventario inventario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventario persistentInventario = em.find(Inventario.class, inventario.getInId());
            Stock stockOld = persistentInventario.getStock();
            Stock stockNew = inventario.getStock();
            Departamento inIddepartamentoOld = persistentInventario.getInIddepartamento();
            Departamento inIddepartamentoNew = inventario.getInIddepartamento();
            Usuario inIduserOld = persistentInventario.getInIduser();
            Usuario inIduserNew = inventario.getInIduser();
            List<Dventa> dventaListOld = persistentInventario.getDventaList();
            List<Dventa> dventaListNew = inventario.getDventaList();
            List<Dcompra> dcompraListOld = persistentInventario.getDcompraList();
            List<Dcompra> dcompraListNew = inventario.getDcompraList();
            List<String> illegalOrphanMessages = null;
            if (stockOld != null && !stockOld.equals(stockNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Stock " + stockOld + " since its inventario field is not nullable.");
            }
            for (Dventa dventaListOldDventa : dventaListOld) {
                if (!dventaListNew.contains(dventaListOldDventa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dventa " + dventaListOldDventa + " since its dveIdinventario field is not nullable.");
                }
            }
            for (Dcompra dcompraListOldDcompra : dcompraListOld) {
                if (!dcompraListNew.contains(dcompraListOldDcompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dcompra " + dcompraListOldDcompra + " since its dcoIdinventario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (stockNew != null) {
                stockNew = em.getReference(stockNew.getClass(), stockNew.getStIdinventario());
                inventario.setStock(stockNew);
            }
            if (inIddepartamentoNew != null) {
                inIddepartamentoNew = em.getReference(inIddepartamentoNew.getClass(), inIddepartamentoNew.getDpId());
                inventario.setInIddepartamento(inIddepartamentoNew);
            }
            if (inIduserNew != null) {
                inIduserNew = em.getReference(inIduserNew.getClass(), inIduserNew.getUsId());
                inventario.setInIduser(inIduserNew);
            }
            List<Dventa> attachedDventaListNew = new ArrayList<Dventa>();
            for (Dventa dventaListNewDventaToAttach : dventaListNew) {
                dventaListNewDventaToAttach = em.getReference(dventaListNewDventaToAttach.getClass(), dventaListNewDventaToAttach.getDveId());
                attachedDventaListNew.add(dventaListNewDventaToAttach);
            }
            dventaListNew = attachedDventaListNew;
            inventario.setDventaList(dventaListNew);
            List<Dcompra> attachedDcompraListNew = new ArrayList<Dcompra>();
            for (Dcompra dcompraListNewDcompraToAttach : dcompraListNew) {
                dcompraListNewDcompraToAttach = em.getReference(dcompraListNewDcompraToAttach.getClass(), dcompraListNewDcompraToAttach.getDcoId());
                attachedDcompraListNew.add(dcompraListNewDcompraToAttach);
            }
            dcompraListNew = attachedDcompraListNew;
            inventario.setDcompraList(dcompraListNew);
            inventario = em.merge(inventario);
            if (stockNew != null && !stockNew.equals(stockOld)) {
                Inventario oldInventarioOfStock = stockNew.getInventario();
                if (oldInventarioOfStock != null) {
                    oldInventarioOfStock.setStock(null);
                    oldInventarioOfStock = em.merge(oldInventarioOfStock);
                }
                stockNew.setInventario(inventario);
                stockNew = em.merge(stockNew);
            }
            if (inIddepartamentoOld != null && !inIddepartamentoOld.equals(inIddepartamentoNew)) {
                inIddepartamentoOld.getInventarioList().remove(inventario);
                inIddepartamentoOld = em.merge(inIddepartamentoOld);
            }
            if (inIddepartamentoNew != null && !inIddepartamentoNew.equals(inIddepartamentoOld)) {
                inIddepartamentoNew.getInventarioList().add(inventario);
                inIddepartamentoNew = em.merge(inIddepartamentoNew);
            }
            if (inIduserOld != null && !inIduserOld.equals(inIduserNew)) {
                inIduserOld.getInventarioList().remove(inventario);
                inIduserOld = em.merge(inIduserOld);
            }
            if (inIduserNew != null && !inIduserNew.equals(inIduserOld)) {
                inIduserNew.getInventarioList().add(inventario);
                inIduserNew = em.merge(inIduserNew);
            }
            for (Dventa dventaListNewDventa : dventaListNew) {
                if (!dventaListOld.contains(dventaListNewDventa)) {
                    Inventario oldDveIdinventarioOfDventaListNewDventa = dventaListNewDventa.getDveIdinventario();
                    dventaListNewDventa.setDveIdinventario(inventario);
                    dventaListNewDventa = em.merge(dventaListNewDventa);
                    if (oldDveIdinventarioOfDventaListNewDventa != null && !oldDveIdinventarioOfDventaListNewDventa.equals(inventario)) {
                        oldDveIdinventarioOfDventaListNewDventa.getDventaList().remove(dventaListNewDventa);
                        oldDveIdinventarioOfDventaListNewDventa = em.merge(oldDveIdinventarioOfDventaListNewDventa);
                    }
                }
            }
            for (Dcompra dcompraListNewDcompra : dcompraListNew) {
                if (!dcompraListOld.contains(dcompraListNewDcompra)) {
                    Inventario oldDcoIdinventarioOfDcompraListNewDcompra = dcompraListNewDcompra.getDcoIdinventario();
                    dcompraListNewDcompra.setDcoIdinventario(inventario);
                    dcompraListNewDcompra = em.merge(dcompraListNewDcompra);
                    if (oldDcoIdinventarioOfDcompraListNewDcompra != null && !oldDcoIdinventarioOfDcompraListNewDcompra.equals(inventario)) {
                        oldDcoIdinventarioOfDcompraListNewDcompra.getDcompraList().remove(dcompraListNewDcompra);
                        oldDcoIdinventarioOfDcompraListNewDcompra = em.merge(oldDcoIdinventarioOfDcompraListNewDcompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inventario.getInId();
                if (findInventario(id) == null) {
                    throw new NonexistentEntityException("The inventario with id " + id + " no longer exists.");
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
            Inventario inventario;
            try {
                inventario = em.getReference(Inventario.class, id);
                inventario.getInId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Stock stockOrphanCheck = inventario.getStock();
            if (stockOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inventario (" + inventario + ") cannot be destroyed since the Stock " + stockOrphanCheck + " in its stock field has a non-nullable inventario field.");
            }
            List<Dventa> dventaListOrphanCheck = inventario.getDventaList();
            for (Dventa dventaListOrphanCheckDventa : dventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inventario (" + inventario + ") cannot be destroyed since the Dventa " + dventaListOrphanCheckDventa + " in its dventaList field has a non-nullable dveIdinventario field.");
            }
            List<Dcompra> dcompraListOrphanCheck = inventario.getDcompraList();
            for (Dcompra dcompraListOrphanCheckDcompra : dcompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inventario (" + inventario + ") cannot be destroyed since the Dcompra " + dcompraListOrphanCheckDcompra + " in its dcompraList field has a non-nullable dcoIdinventario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento inIddepartamento = inventario.getInIddepartamento();
            if (inIddepartamento != null) {
                inIddepartamento.getInventarioList().remove(inventario);
                inIddepartamento = em.merge(inIddepartamento);
            }
            Usuario inIduser = inventario.getInIduser();
            if (inIduser != null) {
                inIduser.getInventarioList().remove(inventario);
                inIduser = em.merge(inIduser);
            }
            em.remove(inventario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inventario> findInventarioEntities() {
        return findInventarioEntities(true, -1, -1);
    }

    public List<Inventario> findInventarioEntities(int maxResults, int firstResult) {
        return findInventarioEntities(false, maxResults, firstResult);
    }

    private List<Inventario> findInventarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inventario.class));
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

    public Inventario findInventario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventario.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventario> rt = cq.from(Inventario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Inventario getInventarioLast() {
        EntityManager em = getEntityManager();
        try {
            return (Inventario) em.createNativeQuery("Select * from inventario order by in_codigo desc limit 1", Inventario.class).getResultList().get(0);
        }catch(Exception e){
            return null;
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Departamento;
import DTO.Inventario;
import DTO.Stock;
import DTO.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class StockJpaController implements Serializable {

    public StockJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Stock stock) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Inventario inventarioOrphanCheck = stock.getInventario();
        if (inventarioOrphanCheck != null) {
            Stock oldStockOfInventario = inventarioOrphanCheck.getStock();
            if (oldStockOfInventario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Inventario " + inventarioOrphanCheck + " already has an item of type Stock whose inventario column cannot be null. Please make another selection for the inventario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento stIddepartamento = stock.getStIddepartamento();
            if (stIddepartamento != null) {
                stIddepartamento = em.getReference(stIddepartamento.getClass(), stIddepartamento.getDpId());
                stock.setStIddepartamento(stIddepartamento);
            }
            Inventario inventario = stock.getInventario();
            if (inventario != null) {
                inventario = em.getReference(inventario.getClass(), inventario.getInId());
                stock.setInventario(inventario);
            }
            Usuario stIduser = stock.getStIduser();
            if (stIduser != null) {
                stIduser = em.getReference(stIduser.getClass(), stIduser.getUsId());
                stock.setStIduser(stIduser);
            }
            em.persist(stock);
            if (stIddepartamento != null) {
                stIddepartamento.getStockList().add(stock);
                stIddepartamento = em.merge(stIddepartamento);
            }
            if (inventario != null) {
                inventario.setStock(stock);
                inventario = em.merge(inventario);
            }
            if (stIduser != null) {
                stIduser.getStockList().add(stock);
                stIduser = em.merge(stIduser);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStock(stock.getStIdinventario()) != null) {
                throw new PreexistingEntityException("Stock " + stock + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Stock stock) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Stock persistentStock = em.find(Stock.class, stock.getStIdinventario());
            Departamento stIddepartamentoOld = persistentStock.getStIddepartamento();
            Departamento stIddepartamentoNew = stock.getStIddepartamento();
            Inventario inventarioOld = persistentStock.getInventario();
            Inventario inventarioNew = stock.getInventario();
            Usuario stIduserOld = persistentStock.getStIduser();
            Usuario stIduserNew = stock.getStIduser();
            List<String> illegalOrphanMessages = null;
            if (inventarioNew != null && !inventarioNew.equals(inventarioOld)) {
                Stock oldStockOfInventario = inventarioNew.getStock();
                if (oldStockOfInventario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Inventario " + inventarioNew + " already has an item of type Stock whose inventario column cannot be null. Please make another selection for the inventario field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (stIddepartamentoNew != null) {
                stIddepartamentoNew = em.getReference(stIddepartamentoNew.getClass(), stIddepartamentoNew.getDpId());
                stock.setStIddepartamento(stIddepartamentoNew);
            }
            if (inventarioNew != null) {
                inventarioNew = em.getReference(inventarioNew.getClass(), inventarioNew.getInId());
                stock.setInventario(inventarioNew);
            }
            if (stIduserNew != null) {
                stIduserNew = em.getReference(stIduserNew.getClass(), stIduserNew.getUsId());
                stock.setStIduser(stIduserNew);
            }
            stock = em.merge(stock);
            if (stIddepartamentoOld != null && !stIddepartamentoOld.equals(stIddepartamentoNew)) {
                stIddepartamentoOld.getStockList().remove(stock);
                stIddepartamentoOld = em.merge(stIddepartamentoOld);
            }
            if (stIddepartamentoNew != null && !stIddepartamentoNew.equals(stIddepartamentoOld)) {
                stIddepartamentoNew.getStockList().add(stock);
                stIddepartamentoNew = em.merge(stIddepartamentoNew);
            }
            if (inventarioOld != null && !inventarioOld.equals(inventarioNew)) {
                inventarioOld.setStock(null);
                inventarioOld = em.merge(inventarioOld);
            }
            if (inventarioNew != null && !inventarioNew.equals(inventarioOld)) {
                inventarioNew.setStock(stock);
                inventarioNew = em.merge(inventarioNew);
            }
            if (stIduserOld != null && !stIduserOld.equals(stIduserNew)) {
                stIduserOld.getStockList().remove(stock);
                stIduserOld = em.merge(stIduserOld);
            }
            if (stIduserNew != null && !stIduserNew.equals(stIduserOld)) {
                stIduserNew.getStockList().add(stock);
                stIduserNew = em.merge(stIduserNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = stock.getStIdinventario();
                if (findStock(id) == null) {
                    throw new NonexistentEntityException("The stock with id " + id + " no longer exists.");
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
            Stock stock;
            try {
                stock = em.getReference(Stock.class, id);
                stock.getStIdinventario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The stock with id " + id + " no longer exists.", enfe);
            }
            Departamento stIddepartamento = stock.getStIddepartamento();
            if (stIddepartamento != null) {
                stIddepartamento.getStockList().remove(stock);
                stIddepartamento = em.merge(stIddepartamento);
            }
            Inventario inventario = stock.getInventario();
            if (inventario != null) {
                inventario.setStock(null);
                inventario = em.merge(inventario);
            }
            Usuario stIduser = stock.getStIduser();
            if (stIduser != null) {
                stIduser.getStockList().remove(stock);
                stIduser = em.merge(stIduser);
            }
            em.remove(stock);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Stock> findStockEntities() {
        return findStockEntities(true, -1, -1);
    }

    public List<Stock> findStockEntities(int maxResults, int firstResult) {
        return findStockEntities(false, maxResults, firstResult);
    }

    private List<Stock> findStockEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Stock.class));
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

    public Stock findStock(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Stock.class, id);
        } finally {
            em.close();
        }
    }

    public int getStockCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Stock> rt = cq.from(Stock.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

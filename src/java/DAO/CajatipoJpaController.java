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
import DTO.Cajachica;
import DTO.Cajatipo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class CajatipoJpaController implements Serializable {

    public CajatipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cajatipo cajatipo) {
        if (cajatipo.getCajachicaList() == null) {
            cajatipo.setCajachicaList(new ArrayList<Cajachica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cajachica> attachedCajachicaList = new ArrayList<Cajachica>();
            for (Cajachica cajachicaListCajachicaToAttach : cajatipo.getCajachicaList()) {
                cajachicaListCajachicaToAttach = em.getReference(cajachicaListCajachicaToAttach.getClass(), cajachicaListCajachicaToAttach.getCaId());
                attachedCajachicaList.add(cajachicaListCajachicaToAttach);
            }
            cajatipo.setCajachicaList(attachedCajachicaList);
            em.persist(cajatipo);
            for (Cajachica cajachicaListCajachica : cajatipo.getCajachicaList()) {
                Cajatipo oldCaTipoOfCajachicaListCajachica = cajachicaListCajachica.getCaTipo();
                cajachicaListCajachica.setCaTipo(cajatipo);
                cajachicaListCajachica = em.merge(cajachicaListCajachica);
                if (oldCaTipoOfCajachicaListCajachica != null) {
                    oldCaTipoOfCajachicaListCajachica.getCajachicaList().remove(cajachicaListCajachica);
                    oldCaTipoOfCajachicaListCajachica = em.merge(oldCaTipoOfCajachicaListCajachica);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cajatipo cajatipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cajatipo persistentCajatipo = em.find(Cajatipo.class, cajatipo.getId());
            List<Cajachica> cajachicaListOld = persistentCajatipo.getCajachicaList();
            List<Cajachica> cajachicaListNew = cajatipo.getCajachicaList();
            List<String> illegalOrphanMessages = null;
            for (Cajachica cajachicaListOldCajachica : cajachicaListOld) {
                if (!cajachicaListNew.contains(cajachicaListOldCajachica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cajachica " + cajachicaListOldCajachica + " since its caTipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cajachica> attachedCajachicaListNew = new ArrayList<Cajachica>();
            for (Cajachica cajachicaListNewCajachicaToAttach : cajachicaListNew) {
                cajachicaListNewCajachicaToAttach = em.getReference(cajachicaListNewCajachicaToAttach.getClass(), cajachicaListNewCajachicaToAttach.getCaId());
                attachedCajachicaListNew.add(cajachicaListNewCajachicaToAttach);
            }
            cajachicaListNew = attachedCajachicaListNew;
            cajatipo.setCajachicaList(cajachicaListNew);
            cajatipo = em.merge(cajatipo);
            for (Cajachica cajachicaListNewCajachica : cajachicaListNew) {
                if (!cajachicaListOld.contains(cajachicaListNewCajachica)) {
                    Cajatipo oldCaTipoOfCajachicaListNewCajachica = cajachicaListNewCajachica.getCaTipo();
                    cajachicaListNewCajachica.setCaTipo(cajatipo);
                    cajachicaListNewCajachica = em.merge(cajachicaListNewCajachica);
                    if (oldCaTipoOfCajachicaListNewCajachica != null && !oldCaTipoOfCajachicaListNewCajachica.equals(cajatipo)) {
                        oldCaTipoOfCajachicaListNewCajachica.getCajachicaList().remove(cajachicaListNewCajachica);
                        oldCaTipoOfCajachicaListNewCajachica = em.merge(oldCaTipoOfCajachicaListNewCajachica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cajatipo.getId();
                if (findCajatipo(id) == null) {
                    throw new NonexistentEntityException("The cajatipo with id " + id + " no longer exists.");
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
            Cajatipo cajatipo;
            try {
                cajatipo = em.getReference(Cajatipo.class, id);
                cajatipo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cajatipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cajachica> cajachicaListOrphanCheck = cajatipo.getCajachicaList();
            for (Cajachica cajachicaListOrphanCheckCajachica : cajachicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cajatipo (" + cajatipo + ") cannot be destroyed since the Cajachica " + cajachicaListOrphanCheckCajachica + " in its cajachicaList field has a non-nullable caTipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cajatipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cajatipo> findCajatipoEntities() {
        return findCajatipoEntities(true, -1, -1);
    }

    public List<Cajatipo> findCajatipoEntities(int maxResults, int firstResult) {
        return findCajatipoEntities(false, maxResults, firstResult);
    }

    private List<Cajatipo> findCajatipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cajatipo.class));
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

    public Cajatipo findCajatipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cajatipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCajatipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cajatipo> rt = cq.from(Cajatipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

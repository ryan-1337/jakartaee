package org.eclipse.jakarta.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.eclipse.jakarta.model.Utilisateur;

import java.util.List;

@RequestScoped
public class UtilisateurDao {
    @PersistenceContext(unitName = "jpa-unit")
    private EntityManager em;

    public Utilisateur findById(Integer id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if(id == null) {
            throw new EntityNotFoundException("Can't find Utilisateur for ID " + id);
        }
        return utilisateur;
    }

    public void add(Utilisateur utilisateur) {
        em.persist(utilisateur);
    }

    public List<Utilisateur> getAll() {
        return em.createNamedQuery("Utilisateur.findAll", Utilisateur.class).getResultList();
    }

    public void delete(Utilisateur utilisateur) {
        em.remove(utilisateur);
    }

    public void update(Utilisateur utilisateur) {
       em.merge(utilisateur);
    }
}

package org.eclipse.jakarta.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.eclipse.jakarta.model.Utilisateur;

import java.util.List;

@RequestScoped
public class UtilisateurDao {
    @PersistenceContext(unitName = "jpa-unit")
    private EntityManager em;

    public Utilisateur findById(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if(id == null) {
            throw new EntityNotFoundException("Can't find Utilisateur for ID " + id);
        }
        return utilisateur;
    }

    public Utilisateur findByUsername(String username) {
        try {

            Utilisateur utilisateur = em.createQuery(
                            "SELECT u from Utilisateur u WHERE u.username = :username", Utilisateur.class).
                    setParameter("username", username).getSingleResult();
            if (utilisateur == null) {
                throw new EntityNotFoundException("Can't find Utilisateur for username " + username);
            }
            return utilisateur;
        }catch (NoResultException e) {
            return null;
        }
    }

    public boolean isUsernameAndPasswordValid(String username, String password) {
        Utilisateur login = em.find(Utilisateur.class, username);
        if(login == null) {
            throw new EntityNotFoundException("Can't find Utilisateur for username or password ");
        }
        Utilisateur pass = em.find(Utilisateur.class, password);
        if(pass == null) {
            throw new EntityNotFoundException("Can't find Utilisateur for login or password ");
        }
        return true;
    }

    public boolean connexion(String username, String password) {
        Utilisateur login = em.find(Utilisateur.class, username);
        if(login == null) {
            throw new EntityNotFoundException("Can't find Utilisateur for username or password ");
        }
        Utilisateur pass = em.find(Utilisateur.class, password);
        if(pass == null) {
            throw new EntityNotFoundException("Can't find Utilisateur for login or password ");
        }

        return true;
    }

    public boolean isPasswordValid(String password) {
        if(password == null) {
            throw new EntityNotFoundException("Can't find Utilisateur for password " + password);
        }
        return true;
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

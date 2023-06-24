package org.eclipse.jakarta.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.eclipse.jakarta.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class UtilisateurDao {
    @PersistenceContext(unitName = "jpa-unit")
    private EntityManager em;

    private static List<Utilisateur> users = new ArrayList<>();


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

    public boolean isValidUser(String username, String password) {
        users = getAll();
        return users.stream()
                .anyMatch(utilisateur -> utilisateur.getUsername().equals(username) && utilisateur.getPassword().equals(password));
    }

    public Utilisateur getUserByUsername(String username) {
        return users.stream()
                .filter(utilisateur -> utilisateur.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Optional<Utilisateur> getUserByToken(String token) {
        users = getAll();
        System.err.println(users.get(0).getToken());
        return users.stream()
                .filter(utilisateur -> utilisateur.getToken().equals(token))
                .findFirst();
    }

    public Utilisateur findById(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if(id == null) {
            throw new EntityNotFoundException("Can't find Utilisateur for ID " + id);
        }
        return utilisateur;
    }

    @Transactional
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

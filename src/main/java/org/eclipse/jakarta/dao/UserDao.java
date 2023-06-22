package org.eclipse.jakarta.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.eclipse.jakarta.model.User;

import java.util.List;

@RequestScoped
public class UserDao {
    @PersistenceContext(unitName = "jpa-unit")
    private EntityManager em;

    public User findById(Long id) {
        User user = em.find(User.class, id);
        if(id == null) {
            throw new EntityNotFoundException("Can't find User for ID " + id);
        }
        return user;
    }

    public User findByUsername(String username) {
        try {

            User user = em.createQuery(
                            "SELECT u from User u WHERE u.username = :username", User.class).
                    setParameter("username", username).getSingleResult();
            if (user == null) {
                throw new EntityNotFoundException("Can't find User for username " + username);
            }
            return user;
        }catch (NoResultException e) {
            return null;
        }
    }

    public boolean isUsernameAndPasswordValid(String username, String password) {
        User login = em.find(User.class, username);
        if(login == null) {
            throw new EntityNotFoundException("Can't find User for username or password ");
        }
        User pass = em.find(User.class, password);
        if(pass == null) {
            throw new EntityNotFoundException("Can't find User for login or password ");
        }
        return true;
    }

    public boolean connexion(String username, String password) {
        User login = em.find(User.class, username);
        if(login == null) {
            throw new EntityNotFoundException("Can't find User for username or password ");
        }
        User pass = em.find(User.class, password);
        if(pass == null) {
            throw new EntityNotFoundException("Can't find User for login or password ");
        }

        return true;
    }

    public boolean isPasswordValid(String password) {
        if(password == null) {
            throw new EntityNotFoundException("Can't find User for password " + password);
        }
        return true;
    }

    public void add(User user) {
        em.persist(user);
    }

    public List<User> getAll() {
        return em.createNamedQuery("Utilisateur.findAll", User.class).getResultList();
    }

    public void delete(User user) {
        em.remove(user);
    }

    public void update(User user) {
        em.merge(user);
    }
}

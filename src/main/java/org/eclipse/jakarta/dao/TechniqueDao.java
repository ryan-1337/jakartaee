package org.eclipse.jakarta.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.jakarta.model.Technique;
import org.eclipse.jakarta.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class TechniqueDao {
    @PersistenceContext(unitName = "jpa-unit")
    private EntityManager em;

    private static List<Technique> techniques = new ArrayList<>();


    public Technique findByName(String name) {
        try {
            Technique technique = em.createQuery(
                            "SELECT t from Technique t WHERE t.name = :name", Technique.class).
                    setParameter("name", name).getSingleResult();
            if (technique == null) {
                throw new EntityNotFoundException("Can't find Technique for name " + name);
            }
            return technique;
        }catch (NoResultException e) {
            return null;
        }
    }

    public Technique findById(Long id) {
        Technique technique = em.find(Technique.class, id);
        if(technique == null) {
            throw new NotFoundException("Can't find technique for ID " + id);
        }
        return technique;
    }

    public Technique getTechniqueByName(String name) {
        return techniques.stream()
                .filter(technique -> technique.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void add(Technique technique) {
        em.persist(technique);
    }

    public List<Technique> getAll() {
        return em.createNamedQuery("Technique.findAll", Technique.class).getResultList();
    }

    public void delete(Technique technique) {
        em.remove(technique);
    }

    public void update(Technique technique) {
        em.merge(technique);
    }
}

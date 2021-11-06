package category.repository;

import category.entity.Category;

import repository.Repository;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Repository for profession entity. Repositories should be used in business layer (e.g.: in services).
 */
@RequestScoped
public class CategoryRepository implements Repository<Category, String> {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public Optional<Category> find(String name) {
        try {
            return Optional.of(em.createQuery("select c from Category c where c.name = :name", Category.class)
                    .setParameter("name", name)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class).getResultList();
    }

    @Override
    public void create(Category entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Category entity) {
        em.remove(em.find(Category.class, entity.getId()));
    }

    @Override
    public void update(Category entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Category entity) {
        em.detach(entity);
    }

}

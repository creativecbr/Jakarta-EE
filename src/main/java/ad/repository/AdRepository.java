package ad.repository;


import ad.entity.Ad;
import category.entity.Category;
import datastore.DataStore;
import repository.Repository;
import user.entity.User;
import utils.CloningUtility;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository for character entity. Repositories should be used in business layer (e.g.: in services).
 */
@Dependent
public class AdRepository implements Repository<Ad, Long> {


    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public Optional<Ad> find(Long id) {
        return Optional.ofNullable(em.find(Ad.class, id));
    }

    @Override
    public List<Ad> findAll() {
        return em.createQuery("select a from Ad a", Ad.class).getResultList();
    }

    @Override
    public void create(Ad entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Ad entity) {
        em.remove(em.find(Ad.class, entity.getId()));
    }

    @Override
    public void update(Ad entity) {
        em.merge(entity);
    }

    /**
     * Seeks for single user's ads.
     *
     * @param id   ad's id
     * @param user user's owner
     * @return container (can be empty) with character
     */
    public Optional<Ad> findByIdAndUser(Long id, User user) {
        try {
            return Optional.of(em.createQuery("select a from Ad a where a.id = :id and a.user = :user", Ad.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /**
     * Seeks for all user's ads.
     *
     * @param user characters' owner
     * @return list (can be empty) of user's characters
     */
    public List<Ad> findAllByUser(User user) {
        return em.createQuery("select a from Ad a where a.user = :user", Ad.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Ad> findAllByCategory(Category category) {

        return em.createQuery("select a from Ad a where a.category = :category", Ad.class)
                .setParameter("category", category)
                .getResultList();
    }

    public void createWithSpecifiedCategory(Ad ad, Category category) {
        ad.setCategory(category);
        em.persist(ad);
    }

}

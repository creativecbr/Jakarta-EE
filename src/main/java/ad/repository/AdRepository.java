package ad.repository;


import ad.entity.Ad;
import category.entity.Category;
import datastore.DataStore;
import repository.Repository;
import user.entity.User;
import utils.CloningUtility;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository for character entity. Repositories should be used in business layer (e.g.: in services).
 */
@Dependent
public class AdRepository implements Repository<Ad, Long> {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private DataStore store;


    /**
     * @param store data store
     */
    @Inject
    public AdRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Ad> find(Long id) {
        return store.findAd(id);
    }

    @Override
    public List<Ad> findAll() {
        return store.findAllAds();
    }

    @Override
    public void create(Ad entity) {
        store.createAd(entity);
    }

    @Override
    public void delete(Ad entity) {
        store.deleteAd(entity.getId());
    }

    @Override
    public void update(Ad entity) {
        store.updateAd(entity);
    }

    /**
     * Seeks for single user's ads.
     *
     * @param id   ad's id
     * @param user user's owner
     * @return container (can be empty) with character
     */
    public Optional<Ad> findByIdAndUser(Long id, User user) {
        return store.findAllAds().stream()
                .filter(ad -> ad.getUser().equals(user))
                .filter(ad -> ad.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * Seeks for all user's ads.
     *
     * @param user characters' owner
     * @return list (can be empty) of user's characters
     */
    public List<Ad> findAllByUser(User user) {
        return store.findAllAds().stream()
                .filter(ad -> ad.getUser().equals(user))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public List<Ad> findAllByCategory(Category category) {
        return store.findAllAds().stream()
                .filter(ad -> ad.getCategory().equals(category))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void createWithSpecifiedCategory(Ad ad, Category category) {
        store.createAd(ad, category);
    }

}

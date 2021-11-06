package ad.service;

import ad.entity.Ad;
import ad.repository.AdRepository;
import category.entity.Category;
import category.repository.CategoryRepository;
import lombok.NoArgsConstructor;
import user.entity.User;
import user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding character entity.
 */
@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class AdService {

    /**
     * Repository for Ad entity.
     */
    private AdRepository adRepository;

    /**
     * Repository for User entity.
     */
    private UserRepository userRepository;

    /**
     * Repository for Category entity.
     */
    private CategoryRepository categoryRepository;

    /**
     * @param adRepository       repository for ad entity
     * @param userRepository     repository for user entity
     * @param categoryRepository repository for category entity
     */
    @Inject
    public AdService(AdRepository adRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates new ad.
     *
     * @param ad new ad
     */
    @Transactional
    public void create(Ad ad) {

        adRepository.create(ad);
        userRepository.find(ad.getUser().getLogin()).ifPresent(user -> user.getAds().add(ad));
    }

    @Transactional
    public void update(Ad ad){

        Ad original = adRepository.find(ad.getId()).orElseThrow();
            adRepository.detach(original);
            if (!original.getUser().getLogin().equals(ad.getUser().getLogin())) {
            original.getUser().getAds().removeIf(adToRemove -> adToRemove.getId().equals(ad.getId()));
            userRepository.find(ad.getUser().getLogin()).ifPresent(user -> user.getAds().add(ad));
        }
        adRepository.update(ad);
    }

    @Transactional
    public void delete(Long id) {

        Ad ad = adRepository.find(id).orElseThrow();
        userRepository.find(ad.getUser().getLogin()).get().getAds().remove(ad);
        adRepository.delete(adRepository.find(id).orElseThrow());
    }


    public List<Ad> findAll() {
        return adRepository.findAll();
    }

    public List<Ad> findAll(User user) {
        return adRepository.findAllByUser(user);
    }

    public Optional<Ad> find(Long id) {
        return adRepository.find(id);
    }

    public Optional<Ad> find(Long id, String login) {

        Optional<User> user = userRepository.find(login);
        if (user.isPresent()) {
            return adRepository.findByIdAndUser(id, user.get());
        } else {
            return Optional.empty();
        }

    }


    /**
     * Seeks for all ads in specify category by category's name.
     *
     * @param name category's name
     * @return list with ad's in this category.
     */
    public List<Ad> findAllByCategoryName(String name) {

        Optional<Category> category = categoryRepository.find(name);
        if (category.isPresent()) {
            return adRepository.findAllByCategory(category.get());
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public void createWithCategory(Ad ad, Category category){
        adRepository.createWithSpecifiedCategory(ad, category);
    }

}

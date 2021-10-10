package datastore;

import ad.entity.Ad;
import ad.entity.Category;
import lombok.extern.java.Log;
import user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@ApplicationScoped
public class DataStore {

    /**
     * Set of all registered users.
     */
    private Set<User> users = new HashSet<>();

    /**
     * Set of all categories in database.
     */
    private Set<Category> categories = new HashSet<>();

    /**
     * Set of all advertisement in database.
     */
    private Set<Ad> ads = new HashSet<>();

    /**
     * Looking for User with specific login.
     *
     * @param login user's login.
     * @return  container with user(can be empty).
     */
    public synchronized Optional<User> findUser(String login){
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    /**
     * Looking for User with specific email and login.
     *
     * @param email user's email.
     * @return  container with user(can be empty).
     */
    public synchronized Optional<User> findUserByLoginOrEmail(String login, String email){
        var usr = Optional.<User>empty();
        for (User user : users) {
            if (user.getEmail().equals(email) || user.getLogin().equals(login)) {
                usr = Optional.of(user);
                break;
            }
        }
        return usr;
    }

    /**
     * Looking for User with specific login and password.
     *
     * @param login user's login.
     * @param password user's login.
     * @return  container with user(can be empty).
     */
    public synchronized Optional<User> findUser(String login, String password){
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }

    /**
     * Looking for all users.
     *
     * @return list with all users in DB.
     */
    public synchronized List<User> findAllUsers(){  return new ArrayList<>(users); }


    /**
     * Looking for Category with specific name.
     *
     * @param name category name.
     * @return  container with category(can be empty).
     */
    public synchronized Optional<Category> findCategory(String name){
        return categories.stream()
                .filter(category -> category.getName().equals(name))
                .findFirst();
    }

    /**
     * Looking for all categories.
     *
     * @return list with all categories in DB.
     */
    public synchronized List<Category> findAllCategories(){  return new ArrayList<>(categories); }

    /**
     * Looking for Ad with specific Id.
     *
     * @param id ad id.
     * @return  container with ad(can be empty).
     */
    public synchronized Optional<Ad> findAd(Long id){
        return ads.stream()
                .filter(ad -> ad.getId().equals(id))
                .findFirst();
    }

    /**
     * Looking for all ads.
     *
     * @return list with all ads in DB.
     */
    public synchronized List<Ad> findAllAds(){  return new ArrayList<>(ads); }


    /**
     * Stores new user.
     *
     * @param user new User to store.
     * @throws IllegalArgumentException if user's login or email already exist in database.
     */
    public void createUser(User user) throws IllegalArgumentException {
        findUserByLoginOrEmail(user.getLogin(), user.getEmail()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException("User with this email or login already exist.");
                },
                () -> users.add(user));
    }

    /**
     * Stores new category.
     *
     * @param category new Category to store.
     * @throws IllegalArgumentException if category already exist in database.
     */
    public void createCategory(Category category) throws IllegalArgumentException {
        findCategory(category.getName()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException("That category already exist.");
                },
                () -> categories.add(category));
    }

    /**
     * Stores new ad.
     *
     * @param ad new Ad in store.
     */
    public void createAd(Ad ad){
        ad.setId(findAllAds().stream().mapToLong(Ad::getId).max().orElse(0)+1);
        ads.add(ad);
    }

    /**
     * Delete Ad from ads by id.
     *
     * @param id ad's id.
     * @throws IllegalArgumentException if ad with specific id doesn't exist.
     */
    public synchronized void deleteAd(Long id) throws IllegalArgumentException {
        findAd(id).ifPresentOrElse(
                original -> ads.remove(original),
                () -> {
                    throw new IllegalArgumentException("Ad with this id doesn't exist.");
                }
        );
    }

    /**
     * Delete Category from categories by name.
     *
     * @param name category's name.
     * @throws IllegalArgumentException if category with specific name doesn't exist.
     */
    public synchronized void deleteCategory(String name) throws IllegalArgumentException {
        findCategory(name).ifPresentOrElse(
                original -> categories.remove(original),
                () -> {
                    throw new IllegalArgumentException("Category with this name doesn't exist.");
                }
        );
    }

    /**
     * Delete User from users by login.
     *
     * @param login User's login.
     * @throws IllegalArgumentException if user with specific login doesn't exist.
     */
    public synchronized void deleteUser(String login) throws IllegalArgumentException {
        findUser(login).ifPresentOrElse(
                original -> users.remove(original),
                () -> {
                    throw new IllegalArgumentException("User with this login doesn't exist.");
                }
        );
    }

    /**
     * Geting all ads stream.
     *
     * @return ads stream.
     */
    public Stream<Ad> getAdsStream() {  return ads.stream(); }

}



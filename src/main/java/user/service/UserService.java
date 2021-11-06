package user.service;

import lombok.NoArgsConstructor;
import user.entity.User;
import user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.*;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding user entity.
 */
@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class UserService {

    /**
     * Repository for user entity.
     */
    private UserRepository repository;

    /**
     * @param repository repository for character entity
     */
    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * @param login existing username
     * @return container (can be empty) with user
     */
    public Optional<User> find(String login) {
        return repository.find(login);
    }

    /**
     *
     * @return list with all users
     */
    public List<User> findAll() {  return repository.findAll();  }

    /**
     * Seeks for single user using login and password. Can be use in authentication module.
     *
     * @param login    user's login
     * @param password user's password (hash)
     * @return container (can be empty) with user
     */
    public Optional<User> find(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }

    /**
     * Saves new user.
     *
     * @param user new user to be saved
     */
    @Transactional
    public void create(User user) {
        repository.create(user);
    }

    /**
     * Removes user by login.
     *
     * @param login of user to delete
     */
    @Transactional
    public void delete(String login) {
        repository.delete(repository.find(login).orElseThrow());
    }

    /**
     * Removes user's avatar by login.
     *
     * @param login of user to delete
     */
    @Transactional
    public void deleteAvatar(String login) { repository.deleteAvatar(repository.find(login).orElseThrow());}

    /**
     * Updates existing user.
     *
     * @param user user object to update
     */
    @Transactional
    public void update(User user) {
        repository.update(user);
    }

    @Transactional
    public void updateAvatar(String login, InputStream is) {
        repository.find(login).ifPresent(user -> {
            try {
                byte[] avatar = is.readAllBytes();
                user.setAvatar(avatar);
                repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

}

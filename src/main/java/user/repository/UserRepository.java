package user.repository;

import datastore.DataStore;
import repository.Repository;
import user.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Repository for User entity. Repositories should be used in business layer (e.g.: in services).
 */
@Dependent
public class UserRepository implements Repository<User, String> {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public UserRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<User> find(String id) {
        return store.findUser(id);
    }

    @Override
    public List<User> findAll() {
        return store.findAllUsers();
    }


    @Override
    public void create(User entity) {
        store.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }



    /**
     * Seeks for single user using login and password (can be empty)
     * @param login         user's login
     * @param password      user's password
     * @return              container with user (can be empty)
     */
    public Optional<User> findByLoginAndPassword(String login, String password)
    {
        return store.findAllUsers().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }


    /**
     * Seeks for single user by login.
     *
     * @param login user's login.
     * @return container with user (can be empty)
     */
    public Optional<User> findByLogin(String login) {
        throw new UnsupportedOperationException("Not implemented.");
    }

}

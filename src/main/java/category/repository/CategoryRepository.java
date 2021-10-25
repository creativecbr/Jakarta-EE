package category.repository;

import category.entity.Category;
import datastore.DataStore;
import repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Repository for profession entity. Repositories should be used in business layer (e.g.: in services).
 */
@Dependent
public class CategoryRepository implements Repository<Category, String> {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public CategoryRepository(DataStore store) {
        this.store = store;
    }


    @Override
    public Optional<Category> find(String name) {
        return store.findCategory(name);
    }

    public Optional<Category> findById(Long id) {
        return store.findCategory(id);
    }

    @Override
    public List<Category> findAll() {
        return store.findAllCategories();
    }

    @Override
    public void create(Category entity) {
        store.createCategory(entity);
    }

    @Override
    public void delete(Category entity) {
        store.deleteCategory(entity.getName());
    }

    @Override
    public void update(Category entity) {
        store.updateCategory(entity);
    }

}

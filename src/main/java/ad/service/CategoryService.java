package ad.service;

import ad.entity.Category;
import ad.repository.CategoryRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding character's profession entity.
 */
@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class CategoryService {

    /**
     * Repository for Category entity.
     */
    private CategoryRepository categoryRepository;

    /**
     * @param repository repository for category entity
     */
    @Inject
    public CategoryService(CategoryRepository repository){ this.categoryRepository = repository; }


    public Category create(Category category) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Not implemented");

        //
        // return categoryRepository.save(category);
         }


    public void delete(Long id) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Not implemented");
        //
        // categoryRepository.deleteById(id);
        }


    public void update(Category category) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Not implemented");
        //
        // categoryRepository.save(category);
        }


    public Optional<Category> find(Long id) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Not implemented");
       // return categoryRepository.findById(id);
    }

    public Optional<Category> find(String name) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Not implemented");
       // return categoryRepository.findByName(name);
    }

    public List<Category> findAll() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Not implemented");
       // return categoryRepository.findAll();
    }


}

package category.service;

import category.entity.Category;
import category.repository.CategoryRepository;
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


    public void create(Category category) {
            categoryRepository.create(category);
         }


    public void delete(String name)  {
           categoryRepository.delete(categoryRepository.find(name).orElseThrow());
        }


    public void update(Category category) {
         categoryRepository.update(category);
    }


    public Optional<Category> find(Long id)  {
        return categoryRepository.findById(id);
    }

    public Optional<Category> find(String name)  {
        return categoryRepository.find(name);
    }


    public List<Category> findAll()  {
       return categoryRepository.findAll();
    }


}

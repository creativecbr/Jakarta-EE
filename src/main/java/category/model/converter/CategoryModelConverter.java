package category.model.converter;

import category.entity.Category;
import category.model.CategoryModel;
import category.service.CategoryService;
import lombok.SneakyThrows;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Faces converter for {@link CategoryModel}. The managed attribute in {@link @FacesConverter} allows the converter
 * to be the CDI bean. In previous version of JSF converters were always created inside JSF lifecycle and where not
 * managed by container that is injection was not possible. As this bean is not annotated with scope the beans.xml
 * descriptor must be present.
 */
@FacesConverter(forClass = CategoryModel.class, managed = true)
public class CategoryModelConverter implements Converter<CategoryModel> {

    /**
     * Service for professions management.
     */
    private CategoryService service;

    @Inject
    public CategoryModelConverter(CategoryService service) {
        this.service = service;
    }

    @SneakyThrows
    @Override
    public CategoryModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Category> category = service.find(value);
        return category.isEmpty() ? null : CategoryModel.entityToModelMapper().apply(category.get());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, CategoryModel value) {
        return value == null ? "" : value.getName();
    }

}

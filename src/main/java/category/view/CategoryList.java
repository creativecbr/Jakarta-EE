package category.view;

import ad.model.AdsModel;
import ad.model.CategoryModel;
import ad.service.AdService;
import category.entity.Category;
import category.model.CategoriesModel;
import category.service.CategoryService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * View bean for rendering list of characters.
 */
@RequestScoped
@Named
public class CategoryList implements Serializable {

    /**
     * Service for managing ads.
     */
    private final AdService adService;

    /**
     * Service for managing categories.
     */
    private final CategoryService service;

    /**
     * Category name.
     */
    @Setter
    @Getter
    private String categoryName;


    @Inject
    public CategoryList(AdService adService, CategoryService service) {
        this.service = service;
        this.adService = adService;
    }

    /**
     * Available categories.
     */

    private CategoriesModel categories;


//    public void init() throws IOException {
//
//        categories =  CategoriesModel.entityToModelMapper().apply(service.findAll());
//
//    }

//
//    /**
//     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
//     * lazy getter.
//     *
//     * @return all ads
//     */

    public CategoriesModel getCategories() {
        if (categories== null) {
            this.categories = CategoriesModel.entityToModelMapper().apply(service.findAll());
        }
        return categories;
    }

    /**
     * Action for clicking delete action.
     *
     * @param category category to be removed
     * @return navigation case to list_ad
     */
    public String deleteAction(CategoriesModel.Category category) {
        service.delete(category.getName());
        return "category_list?faces-redirect=true";
    }

}

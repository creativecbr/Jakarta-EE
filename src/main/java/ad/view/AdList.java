package ad.view;

import ad.model.AdsModel;
import ad.service.AdService;
import category.entity.Category;
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
import java.util.Optional;

/**
 * View bean for rendering list of characters.
 */
@RequestScoped
@Named
public class AdList implements Serializable {

    /**
     * Service for managing ads.
     */
    private final AdService service;

    /**
     * Service for managing categories.
     */
    private final CategoryService categoryService;

    /**
     * Category name.
     */
    @Setter
    @Getter
    private String categoryName;

    /**
     * Ads list exposed to the view.
     */
    private AdsModel ads;

    @Inject
    public AdList(AdService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }


    public void init() throws IOException {
        Optional<Category> category = categoryService.find(categoryName);
        if (category.isPresent()) {
            this.ads = AdsModel.entityToModelMapper().apply(service.findAllByCategoryName(categoryName));
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
    }


    public AdsModel getAds() {
        if (ads == null) {
            this.ads = AdsModel.entityToModelMapper().apply(service.findAllByCategoryName(categoryName));
        }
        return ads;
    }


}

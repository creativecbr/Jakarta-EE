package category.view;

import ad.entity.Ad;
import ad.model.AdsModel;
import ad.service.AdService;
import category.entity.Category;
import category.model.CategoryModel;
import category.service.CategoryService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * View bean for rendering list of characters.
 */
@ViewScoped
@Named
public class CategoryView implements Serializable {

    /**
     * Service for managing ads.
     */
    private final AdService adService;

    /**
     * Service for managing categories.
     */
    private final CategoryService service;


    /**
     * Category's id.
     */
    @Setter
    @Getter
    private Long id;

    /**
     * Ads list exposed to the view.
     */
    @Getter
    private AdsModel ads;

    @Getter
    private CategoryModel category;


    @Inject
    public CategoryView(AdService adService, CategoryService service) {
        this.adService = adService;
        this.service = service;
    }


    public void init() throws IOException {
        Optional<Category> _category = service.find(id);


        if (_category.isPresent()) {
            this.ads = AdsModel.entityToModelMapper().apply(adService.findAllByCategoryName(_category.get().getName()));
            category = CategoryModel.entityToModelMapper().apply(_category.orElseThrow());
        } else {
           // this.ads = AdsModel.entityToModelMapper().apply(adService.findAll());

            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
        for (AdsModel.Ad ad : ads.getAds())
        {
            System.out.println(ad.toString());
        }

    }


    /**
     * Action for clicking delete action.
     *
     * @param id ad to be removed
     * @return navigation case to list_ad
     */
    public String deleteAction(Long id) {

      adService.delete(id);

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }



}

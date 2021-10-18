package ad.view;

import ad.entity.Ad;
import ad.model.AdEditModel;
import ad.model.CategoryModel;
import ad.service.AdService;
import category.service.CategoryService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * View bean for rendering single character edit form.
 */
@ViewScoped
@Named
public class AdEdit implements Serializable {

    /**
     * Service for managing ads.
     */
    private final AdService service;

    /**
     * Service for managing categories.
     */
    private CategoryService categoryService;

    /**
     * Ad id.
     */
    @Setter
    @Getter
    private Long id;

    /**
     * Ad exposed to the view.
     */
    @Getter
    private AdEditModel ad;

    /**
     * Available categories.
     */
    @Getter
    private List<CategoryModel> categories;


    @Inject
    public AdEdit(AdService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    public void init() throws IOException {

        categories = categoryService.findAll().stream()
                .map(CategoryModel.entityToModelMapper())
                .collect(Collectors.toList());

        Optional<Ad> ad = service.find(id);
        if (ad.isPresent()) {
            this.ad = AdEditModel.entityToModelMapper().apply(ad.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Ad not found");
        }
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {

        service.update(AdEditModel.modelToEntityUpdater(
                category -> categoryService.find(ad.getCategory()).orElseThrow())
                .apply(service.find(id).orElseThrow(), ad));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }


}

package ad.view;

import ad.entity.Ad;
import ad.model.AdModel;
import ad.model.AdsModel;
import ad.service.AdService;
import category.service.CategoryService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * View bean for rendering single characters.
 */
@RequestScoped
@Named
public class AdView implements Serializable {

    /**
     * Service for managing characters.
     */
    private final AdService service;

    private final CategoryService categoryService;

    /**
     * Ad id.
     */
    @Setter
    @Getter
    private Long id;

    /**
     * Character exposed to the view.
     */
    @Getter
    private AdModel ad;

    @Inject
    public AdView(AdService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Ad> ad = service.find(id);
        if (ad.isPresent()) {
            this.ad = AdModel.entityToModelMapper().apply(ad.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Ad not found");
        }
    }

//    /**
//     * Action for back to ad's category with other ads.
//     *
//     * @param  to get its category name
//     * @return navigation case to list_ad
//     */
//    public String backToCategory() {
//        System.out.println("WESZLO");
//        Optional<Ad> ad = null;//service.find(_ad.getId());
//        if(ad.isPresent())
//        {
//            Long id = categoryService.find(ad.get().getCategory().getName()).get().getId();
//            return "/category/category_list.xhtml?id="+id.toString()+"?faces-redirect=true";
//        }  else {
//            return "/category/category_list.xhtml?faces-redirect=true";
//        }
//
//    }

    
}

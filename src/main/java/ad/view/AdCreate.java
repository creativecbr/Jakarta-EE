package ad.view;

import ad.model.AdCreateModel;
import ad.model.CategoryModel;
import ad.model.UserModel;
import ad.service.AdService;
import category.model.CategoriesModel;
import category.service.CategoryService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import user.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Scope;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * View bean for rendering single character create form. Creating a character is divided into number of steps where
 * each step is separate JSF view. In order to use single bean, conversation scope is used.
 */
@RequestScoped
@Named
@NoArgsConstructor
public class AdCreate implements Serializable {

    /**
     * Service for managing ads.
     */
    private AdService service;

    /**
     * Service for managing categories.
     */
    private CategoryService categoryService;

    /**
     * Service for managing users.
     */
    private UserService userService;

    /**
     * Ad id.
     */
    @Setter
    @Getter
    private Long id;

    /**
     * Ad exposed to the view.
     */
    @Setter
    @Getter
    private AdCreateModel ad;

    /**
     * Available categories.
     */
    @Getter
    private List<CategoryModel> categories;

    /**
     * Available users.
     */
    @Getter
    private List<UserModel> users;

    /**
     * Injected conversation.
     */
  //  private Conversation conversation;


    @Inject
    public AdCreate(AdService service, CategoryService categoryService, UserService userService) {
        this.service = service;
        this.categoryService = categoryService;
        this.userService = userService;
       // this.conversation = conversation;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    @PostConstruct
    public void init() {


        categories = categoryService.findAll().stream()
                .map(CategoryModel.entityToModelMapper())
                .collect(Collectors.toList());

        users = userService.findAll().stream()
                .map(UserModel.entityToModelMapper())
                .collect(Collectors.toList());


        ad = new AdCreateModel();
        ad.setTitle("");

      //  conversation.begin();

    }

    public String cancelAction() {
        return "/ad/ad_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {


        // PROBLEM
        System.out.println(ad.getCategory());
        System.out.println(ad.getUser());
        System.out.println(ad.toString());

        service.create(AdCreateModel.modelToEntityMapper(
                category -> categoryService.find(ad.getCategory().getName()).orElseThrow(),
                user -> userService.find(ad.getUser().getLogin()).orElseThrow()).apply(ad));
       // conversation.end();
        return "/ad/ad_list.xhtml?faces-redirect=true";
    }

}

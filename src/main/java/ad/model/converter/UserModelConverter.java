package ad.model.converter;

import ad.model.CategoryModel;
import ad.model.UserModel;
import category.entity.Category;
import category.service.CategoryService;
import lombok.SneakyThrows;
import user.entity.User;
import user.service.UserService;

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
@FacesConverter(forClass = UserModel.class, managed = true)
public class UserModelConverter implements Converter<UserModel> {

    /**
     * Service for professions management.
     */
    private UserService service;

    @Inject
    public UserModelConverter(UserService service) {
        this.service = service;
    }

    @SneakyThrows
    @Override
    public UserModel getAsObject(FacesContext context, UIComponent component, String value) {


        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<User> user = service.find(value);

        return user.isEmpty() ? null : UserModel.entityToModelMapper().apply(user.get());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, UserModel value) {
        return value == null ? "" : value.getLogin();
    }

}

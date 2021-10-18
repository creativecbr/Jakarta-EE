package ad.model;

import ad.entity.Ad;
import category.entity.Category;
import lombok.*;
import user.entity.User;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * JSF view model class in order to not to use entity classes. Represents new character to be created. Includes oll
 * fields which can be use in character creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class AdCreateModel {


    /**
     * Advertisement's title.
     */
    private String title;

    /**
     * Advertisement's content.
     */
    private String description;

    /**
     * Advertisement's category.
     */

    private CategoryModel category;

    /**
     * Advertisement's owner.
     */
    private UserModel user;

    /**
     * @param categoryFunction function for converting category name to category entity object
     * @param userFunction function for converting user login to user entity object
     * @return mapper for convenient converting model object to entity object
     */
    public static Function<AdCreateModel, Ad> modelToEntityMapper(
            Function<String, Category> categoryFunction,
            Function<String, User> userFunction) {


        return model -> Ad.builder()
                .title(model.getTitle())
                .description(model.getDescription())
                .category(categoryFunction.apply(model.getCategory().getName()))
                .user(userFunction.apply(model.getUser().getLogin()))
                .build();
    }

}

package category.model;

import ad.entity.Ad;
import category.entity.Category;
import lombok.*;

import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents single character to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CategoryModel {

    /**
     * Name of category.
     */
    private String name;

    /**
     * Description of category.
     */
    private String description;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Category, CategoryModel> entityToModelMapper() {
        return category -> CategoryModel.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}

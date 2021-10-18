package ad.model;

import category.entity.Category;
import lombok.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * JSF view model class in order to not to use entity classes. Represents single user to be displayed or selected.
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
     * Name of the category.
     */
    private String name;


    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Category, CategoryModel> entityToModelMapper() {
        return category -> CategoryModel.builder()
                .name(category.getName())
                .build();
    }
}

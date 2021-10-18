package category.model;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents list of characters to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CategoriesModel implements Serializable {

    /**
     * Represents single character in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Category {

        /**
         * Name of the category.
         */
        private String name;


        /**
         * Category's id.
         */
        private Long id;

    }

    /**
     * Name of the selected ads.
     */
    @Singular
    private List<Category> categories;

    /**
     * @return mapper for convenient converting entity object to model object
     */
    public static Function<Collection<category.entity.Category>, CategoriesModel> entityToModelMapper() {
        return categories -> {
            CategoriesModelBuilder model = CategoriesModel.builder();
            categories.stream()
                    .map(category -> Category.builder()
                            .name(category.getName())
                            .id(category.getId())
                            .build())
                    .forEach(model::category);
            return model.build();
        };
    }

}

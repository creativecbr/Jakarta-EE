package ad.model;

import ad.entity.Ad;
import category.entity.Category;
import lombok.*;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents single character to be edited. Includes
 * only fields which can be edited after character creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class AdEditModel {

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

    private String category;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Ad, AdEditModel> entityToModelMapper() {
        return ad -> AdEditModel.builder()
                .title(ad.getTitle())
                .description(ad.getDescription())
                .category(ad.getCategory().getName())
                .build();
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Ad, AdEditModel, Ad> modelToEntityUpdater(
        Function<String, Category> categoryFunction){
        return (ad, request) -> {
            ad.setTitle(request.getTitle());
            ad.setDescription(request.getDescription());
            ad.setCategory(categoryFunction.apply(request.getCategory()));
            return ad;
        };
    }

}

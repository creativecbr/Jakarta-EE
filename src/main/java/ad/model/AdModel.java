package ad.model;

import ad.entity.Ad;
import category.entity.Category;
import lombok.*;
import user.entity.User;

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
public class AdModel {

    /**
     * Unique Ad's identification number.
     */
    private Long id;

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
     * Advertisement's owner.
     */
    private String user;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Ad, AdModel> entityToModelMapper() {
        return ad -> AdModel.builder()
                .title(ad.getTitle())
                .description(ad.getDescription())
                .category(ad.getCategory().getName())
                .user(ad.getUser().getLogin())
                .build();
    }
}

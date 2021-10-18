package ad.model;

import category.entity.Category;
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
public class AdsModel implements Serializable {

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
    public static class Ad {

        /**
         * Unique Ad's identification number.
         */
        private Long id;

        /**
         * Title of the ad.
         */
        @Getter
        private String title;


    }

    /**
     * Name of the selected ads.
     */
    @Singular
    private List<Ad> ads;

    /**
     * @return mapper for convenient converting entity object to model object
     */
    public static Function<Collection<ad.entity.Ad>, AdsModel> entityToModelMapper() {
        return ads -> {
            AdsModelBuilder model = AdsModel.builder();
            ads.stream()
                    .map(ad -> Ad.builder()
                            .id(ad.getId())
                            .title(ad.getTitle())
                            .build())
                    .forEach(model::ad);
            return model.build();
        };
    }

}

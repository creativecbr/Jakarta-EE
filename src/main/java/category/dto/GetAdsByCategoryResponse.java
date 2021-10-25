package category.dto;

import lombok.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetAdsByCategoryResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Ad {
        /**
         * Title of the ad.
         */
        private String title;

        /**
         * Description of ad.
         */
        private String description;

    }

    @Singular
    private List<Ad> ads;

    public static Function<Collection<ad.entity.Ad>, GetAdsByCategoryResponse> entityToDtoMapper() {
        return ads -> {
            GetAdsByCategoryResponseBuilder response = GetAdsByCategoryResponse.builder();
            ads.stream()
                    .map(ad -> Ad.builder()
                            .title(ad.getTitle())
                            .description(ad.getDescription())
                            .build())
                    .forEach(response::ad);
            return response.build();
        };
    }
}
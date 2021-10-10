package ad.dto;

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
public class GetAdsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Ad {

        /**
         * Id of the ad.
         */
        private Long id;
        /**
         * Title of the ad.
         */
        private String title;

        /**
         * Description of ad.
         */
        private String description;

        /**
         * Ad's category.
         */
        private String category;

    }

    @Singular
    private List<Ad> ads;

    public static Function<Collection<Ad>, GetAdsResponse> entityToDtoMapper()
    {
        return ads -> {
            GetAdsResponseBuilder response = GetAdsResponse.builder();
            ads.stream()
                    .map(ad -> Ad.builder()
                            .id(ad.getId())
                            .title(ad.getTitle())
                            .description(ad.getDescription())
                            .category(ad.getCategory())
                            .build())
                    .forEach(response::ad);
            return response.build();
        };
    }
}

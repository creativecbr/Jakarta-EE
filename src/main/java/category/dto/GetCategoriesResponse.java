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
public class GetCategoriesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Category {

        /**
         * Unique category's id.
         */
        private Long id;

        /**
         * Name of category.
         */
        private String name;
    }

    @Singular
    private List<Category> categories;

    public static Function<Collection<category.entity.Category>, GetCategoriesResponse> entityToDtoMapper()
    {
        return categories -> {
            GetCategoriesResponseBuilder response = GetCategoriesResponse.builder();
            categories.stream()
                    .map(category -> Category.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .build())
                    .forEach(response::category);
            return response.build();
        };
    }
}

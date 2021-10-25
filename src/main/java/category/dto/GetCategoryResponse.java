package category.dto;

import category.entity.Category;
import lombok.*;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCategoryResponse {

    /**
     * Unique id of category.
     */
    private Long id;

    /**
     * Name of category.
     */
    private String name;

    /**
     * Description of category.
     */
    private String description;

    public static Function<Category, GetCategoryResponse> entityToDtoMapper() {
        return category -> {
            GetCategoryResponse.GetCategoryResponseBuilder response = GetCategoryResponse.builder();
            response.name(category.getName());
            response.description(category.getDescription());
            response.id(category.getId());
            return response.build();
        };
    }
}

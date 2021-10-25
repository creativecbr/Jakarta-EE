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
public class CreateCategoryRequest {

    /**
     * Name of category.
     */
    private String name;

    /**
     * Description of category.
     */
    private String description;

    public static Function<CreateCategoryRequest, Category> dtoToEntityMapper() {
        return request -> Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
package ad.dto;

import ad.entity.Category;
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
     * Category's name.
     */
    private String name;


    public static Function<CreateCategoryRequest, Category> dtoToEntityMapper() {
        return request -> Category.builder()
                .name(request.getName())
                .build();
    }
}
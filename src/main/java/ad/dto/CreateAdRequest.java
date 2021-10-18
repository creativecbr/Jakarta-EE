package ad.dto;


import ad.entity.Ad;
import category.entity.Category;
import lombok.*;
import user.entity.User;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateAdRequest {

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
     * @param categoryFunction function for converting category name to category entity object
     * @return mapper for convenient converting dto object to entity object
     */
    public static Function<CreateAdRequest, Ad> dtoToEntityMapper(
            Function<String, Category> categoryFunction,
            Supplier<User> userSupplier) {
        return request -> Ad.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(userSupplier.get())
                .category(categoryFunction.apply(request.getCategory()))
                .build();
    }

}

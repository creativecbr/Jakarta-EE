package ad.model;

import category.entity.Category;
import lombok.*;
import user.entity.User;

import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents single user to be displayed or selected.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UserModel {

    /**
     * Name of the category.
     */
    private String login;


    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<User, UserModel> entityToModelMapper() {
        return user -> UserModel.builder()
                .login(user.getLogin())
                .build();
    }
}

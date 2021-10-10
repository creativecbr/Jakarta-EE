package user.dto;

import lombok.*;
import user.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * GET users response. Contains user names of users in the system. User name ios the same as login.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUsersResponse {

    /**
     * Represents single user in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {

        /**
         * Unique login identifying user.
         */
        private String login;

        /**
         * Name of the user.
         */
        private String name;


        /**
         * Surname of the user.
         */
        private String surname;

    }

    /**
     * List of all user names.
     */
    @Singular
    private List<User> users;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<user.entity.User>, GetUsersResponse> entityToDtoMapper() {
        return users -> {
            GetUsersResponse.GetUsersResponseBuilder response = GetUsersResponse.builder();
            users.stream()
                    .map(user -> User.builder()
                            .login(user.getLogin())
                            .name(user.getName())
                            .surname(user.getSurname())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }

}

package user.dto;

import lombok.*;
import user.entity.Role;
import user.entity.User;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * GET user response. Contains only field's which can be displayed on frontend. User is defined in
 * {@link user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {

    /**
     * User's username (login)
     */
    private String login;

    /**
     * User's name.
     */
    private String name;

    /**
     * User's surname.
     */
    private String surname;

    /**
     * User's birth date.
     */
    private LocalDate birthDate;

    /**
     * User's email.
     */
    private String email;

    /**
     * User's role.
     */
    private Role role;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }

}

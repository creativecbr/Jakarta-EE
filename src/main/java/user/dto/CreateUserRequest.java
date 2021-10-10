package user.dto;

import lombok.*;
import user.entity.User;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * PSOT user request. Contains only fields that can be set during user creation. User is defined in
 * {@link user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {

    /**
     * User's login.
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
     * User's password.
     */
    private String password;

    /**
     * User's email.
     */
    private String email;

    public static Function<CreateUserRequest, User> dtoToEntityMapper() {
        return request -> User.builder()
                .login(request.getLogin())
                .name(request.getName())
                .surname(request.getSurname())
                .birthDate(request.getBirthDate())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
    }

}

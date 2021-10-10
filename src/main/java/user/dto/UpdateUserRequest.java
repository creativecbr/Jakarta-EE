package user.dto;

import lombok.*;
import user.entity.User;

import java.time.LocalDate;
import java.util.function.BiFunction;

/**
 * PUT user request. Contains only fields which can be changed byt the user while updating its profile. User is defined
 * {@link user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateUserRequest {

    /**
     * User's name.
     */
    private String name;

    /**
     * User's surname.
     */
    private String surname;

    /**
     * User's birth day.
     */
    private LocalDate birthDate;

    /**
     * User's email.
     */
    private String email;

    /**
     * @return updater for convenient updating entity object using dto object
     */
    public static BiFunction<User, UpdateUserRequest, User> dtoToEntityUpdater() {
        return (user, request) -> {
            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setBirthDate(request.getBirthDate());
            user.setEmail(request.getEmail());
            return user;
        };
    }

}

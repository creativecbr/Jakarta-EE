package user.entity;

import ad.entity.Ad;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Entity for system user. Represents information about particular user.
 **/
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class User implements Serializable {

    /**
     * User's login.
     */
    private String login;

    /**
     * Users's given name.
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
    @ToString.Exclude
    private String password;

    /**
     * User's contact email.
     */
    private String email;

    /**
     * User's role.
     */
    private Role role;

    /**
     * User's list of ads.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Ad> ads;


    /**
     * User's avatar.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;

}

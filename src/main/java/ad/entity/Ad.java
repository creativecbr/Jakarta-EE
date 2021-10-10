package ad.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import user.entity.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity for advertisement added by user. Contains title and description. Ad belongs to one category.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode()
public class Ad implements Serializable {

    /**
     * Unique Ad's identification number.
     */
    private Long id;

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

    private Category category;

    /**
     * Advertisement's owner.
     */
    @ToString.Exclude
    private User user;

}

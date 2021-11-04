package ad.entity;

import category.entity.Category;
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
@Entity
@Table("ads")

public class Ad implements Serializable {

    /**
     * Unique Ad's identification number.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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
    @ManyToOne
    @JoinColumn(name = "categories")
    private Category category;

    /**
     * Advertisement's owner.
     */
    @ManyToOne
    @JoinColumn(name = "users")
    @ToString.Exclude
    private User user;

}

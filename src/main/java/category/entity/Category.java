package category.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Category entity describes one category. Every Ad should have one associated category.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "categories")
public class Category implements Serializable {

    /**
     * Unique id of category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Name of category.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Description of category.
      */
    private String description;


}

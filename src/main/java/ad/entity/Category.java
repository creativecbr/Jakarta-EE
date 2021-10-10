package ad.entity;

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
public class Category implements Serializable {

    /**
     * Unique id of category.
     */
    private Long id;

    /**
     * Name of category.
     */
    private String name;


}

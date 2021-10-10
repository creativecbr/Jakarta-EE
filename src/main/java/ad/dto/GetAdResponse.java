package ad.dto;

import ad.entity.Ad;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetAdResponse {

    /**
     * Unique advertisement's title.
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
    private String category;

    /**
     * Advertisement's owner.
     */
    private String user;


    public static Function<Ad, GetAdResponse> entityToDtoMapper()
    {
        return ad -> GetAdResponse.builder()
                .id(ad.getId())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .category(ad.getCategory().getName())
                .user(ad.getUser().getLogin())
                .build();

        }

}

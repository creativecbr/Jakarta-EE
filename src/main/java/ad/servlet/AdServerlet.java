package ad.servlet;

import ad.service.AdService;
import category.service.CategoryService;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet for handling HTTP requests considering characters operations. Servlet API does not allow named path
 * parameters so wildcard is used.
 */


// przerobic sciezki i metody fest

@WebServlet(urlPatterns = {
        AdServerlet.Paths.ADS + "/*",
        AdServerlet.Paths.USER_ADS + "/*"
})
public class AdServerlet extends HttpServlet {

    /**
     * Service for managing ads.
     */
    private AdService adService;

    /**
     * Service for managing categories.
     */
    private CategoryService categoryService;


    @Inject
    public AdServerlet(AdService adService, CategoryService categoryService) {
        this.adService = adService;
        this.categoryService = categoryService;
    }

    /**
     * JSON-B mapping object. According to open liberty documentation creating those is expensive. The JSON-B is only
     * one of many solutions. JSON strings can be build by hand {@link StringBuilder} or with JSON-P API. Both JSON-B
     * and JSON-P are part of Jakarta EE whereas JSON-B is newer standard.
     */
    private final Jsonb jsonb = JsonbBuilder.create();

    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static class Paths {

        /**
         * All ads or specified ad.
         */
        public static final String ADS = "/api/ads";

        /**
         * All ads belonging to the caller principal or specified ads of the caller principal.
         */
        public static final String USER_ADS = "/api/user/ads";

    }

    /**
     * Definition of regular expression patterns supported by this servlet. Separate inner class provides composition
     * for static fields. Whereas servlet activation path can be compared to {@link Paths} the path info (denoted by
     * wildcard in paths) can be compared using regular expressions.
     */
    public static class Patterns {

        /**
         * All ads.
         */
        public static final String ADS = "^/?$";

        /**
         * Specified ad.
         */
        public static final String AD = "^/[0-9]+/?$";

    }


}

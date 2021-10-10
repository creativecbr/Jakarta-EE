package user.servlet;

import user.context.UserContext;
import user.dto.CreateUserRequest;
import user.dto.GetUserResponse;
import user.dto.GetUsersResponse;
import user.dto.UpdateUserRequest;
import user.entity.User;
import user.service.UserService;
import utils.HttpHeaders;
import utils.MimeTypes;
import utils.ServletUtility;
import utils.UrlFactory;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Servlet for returning user's name from the active session if present.
 */
@WebServlet(urlPatterns = {UserServlet.Paths.USERS + "/*"}
)
public class UserServlet extends HttpServlet {

    /**
     * Service for user entity operations.
     */
    private UserService service;

    /**
     * Authorized user context.
     */
    private UserContext context;

    @Inject
    public UserServlet(UserService service, UserContext context) {
        this.service = service;
        this.context = context;
    }

    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static class Paths {
        /**
         * All users or specified user
         */
        public static final String USERS = "/api/users";
    }

    /**
     * Definition of regular expression patterns supported by this servlet. Separate inner class provides composition
     * for static fields. Whereas servlet activation path can be compared to {@link Paths} the path info (denoted by
     * wildcard in paths) can be compared using regular expressions.
     */
    public static class Patterns {

        /**
         * All users.
         */
        public static final String USERS = "^/?$";

        /**
         * Specified user.
         */
        public static final String USER = "^/[A-Z,a-z]+/?$";

    }


    /**
     * JSON-B mapping object. According to open liberty documentation creating those is expensive. The JSON-B is only
     * one of many solutions. JSON strings can be build by hand {@link StringBuilder} or with JSON-P API. Both JSON-B
     * and JSON-P are part of Jakarta EE whereas JSON-B is newer standard.
     */
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        System.out.println(request.toString());
        System.out.println(response.toString());
        System.out.println("Znalazl sie");
        System.out.println(servletPath + "\n\n\n" + path);
        if (Paths.USERS.equals(servletPath)) {

            if (path.matches(Patterns.USER)) {
                getUser(request, response);
                return;
            } else if (path.matches(Patterns.USERS)) {
                getUsers(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.USERS.equals(request.getServletPath())) {
            if (path.matches(Patterns.USER)) {
                postUser(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.USERS.equals(request.getServletPath())) {
            if (path.matches(Patterns.USER)) {
                putUser(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.USERS.equals(request.getServletPath())) {
            if (path.matches(Patterns.USER)) {
                deleteUser(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Sends single user or 404 error if not found.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException if any input or output exception occurred
     */
    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = service.find(login);

        if (user.isPresent()) {
            response.setContentType(MimeTypes.APPLICATION_JSON);
            response.getWriter()
                    .write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Sends all users as JSON.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException if an input or output exception occurred
     */
    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MimeTypes.APPLICATION_JSON);
        response.getWriter()
                .write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(service.findAll())));
    }

    /**
     * Deletes existing user denoted by path param.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException if an input or output exception occurred
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = service.find(login);

        if (user.isPresent()) {
            service.delete(user.get().getLogin());
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Decodes JSON request and updates existing user.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException if an input or output exception occurred
     */
    private void putUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = service.find(login);

        if (user.isPresent()) {
            UpdateUserRequest requestBody = jsonb.fromJson(request.getInputStream(),
                    UpdateUserRequest.class);

            UpdateUserRequest.dtoToEntityUpdater().apply(user.get(), requestBody);

            service.update(user.get());
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Decodes JSON request and stores new user.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException if an input or output exception occurred
     */
    private void postUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CreateUserRequest requestBody = jsonb.fromJson(request.getInputStream(), CreateUserRequest.class);

        User user = CreateUserRequest
                .dtoToEntityMapper()
                .apply(requestBody);

        try {
            service.create(user);
            //When creating new resource, its location should be returned.
            response.addHeader(HttpHeaders.LOCATION,
                    UrlFactory.createUrl(request, Paths.USERS, user.getLogin()));
            //When creating new resource, appropriate code should be set.
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}

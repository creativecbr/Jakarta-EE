package user.servlet;

import user.entity.User;
import user.service.UserService;
import utils.MimeTypes;
import utils.ServletUtility;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

/**
 * Servlet for serving and uploading characters' portraits i raster image format.
 */
@WebServlet(urlPatterns = AvatarServlet.Paths.AVATARS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class AvatarServlet extends HttpServlet {

    /**
     * Service for managing characters.
     */
    private UserService service;

    @Inject
    public AvatarServlet(UserService service) {
        this.service = service;
    }

    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static class Paths {

        /**
         * Specified portrait for download and upload.
         */
        public static final String AVATARS = "/api/avatars";

    }

    /**
     * Definition of regular expression patterns supported by this servlet. Separate inner class provides composition
     * for static fields. Whereas servlet activation path can be compared to {@link Paths} the path info (denoted by
     * wildcard in paths) can be compared using regular expressions.
     */
    public static class Patterns {

        /**
         * Specified portrait (for download).
         */
        public static final String AVATAR = "^/[0-9]+/?$";

    }

    /**
     * Request parameters (both query params and request parts) which can be sent by the client.
     */
    public static class Parameters {

        /**
         * Portrait image part.
         */
        public static final String AVATAR = "avatar";

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                getAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                putAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Updates character's portrait. Receives portrait bytes from request and stores them in the data storage.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException      if any input or output exception occurred
     * @throws ServletException if this request is not of type multipart/form-data
     */
    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = service.find(login);

        if (user.isPresent()) {
            Part avatar = request.getPart(Parameters.AVATAR);
            if (avatar != null) {
                service.updateAvatar(login, avatar.getInputStream());
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Fetches portrait as byte array from data storage and sends is through http protocol.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException if any input or output exception occurred
     */
    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = service.find(login);

        if (user.isPresent()) {
            //Type should be stored in the database but in this project we assume everything to be png.
            response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            response.setContentLength(user.get().getAvatar().length);
            response.getOutputStream().write(user.get().getAvatar());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

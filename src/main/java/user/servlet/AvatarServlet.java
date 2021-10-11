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
@WebServlet(urlPatterns = {AvatarServlet.Paths.AVATARS + "/*"})
@MultipartConfig(maxFileSize = 200 * 1024)
public class AvatarServlet extends HttpServlet {

    /**
     * Service for managing characters.
     */
    private final UserService service;

    @Inject
    public AvatarServlet(UserService service) {
        this.service = service;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                postAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.AVATARS.equals(request.getServletPath())) {
            if (path.matches(Patterns.AVATAR)) {
                deleteAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Deletes existing avatar denoted by path param.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException if an input or output exception occurred
     */
    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = service.find(login);

        if (user.isPresent()) {
            service.deleteAvatar(user.get().getLogin());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * @param request  http request
     * @param response http response
     * @throws IOException      if any input or output exception occurred
     * @throws ServletException if this request is not of type multipart/form-data
     */
    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = service.find(login);

        if (user.isPresent()) {
            Part avatar = request.getPart(Parameters.AVATAR);
            if (avatar != null) {
                service.updateAvatar(login, avatar.getInputStream());
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * @param request  http request
     * @param response http response
     * @throws IOException      if any input or output exception occurred
     * @throws ServletException if this request is not of type multipart/form-data
     */
    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = service.find(login);

        if (user.isPresent()) {
            if (user.get().getAvatar() == null) {
                Part avatar = request.getPart(Parameters.AVATAR);
                if (avatar != null) {
                    service.updateAvatar(login, avatar.getInputStream());
                }
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * @param request  http request
     * @param response http response
     * @throws IOException if any input or output exception occurred
     */
    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = service.find(login);

        if (user.isPresent() && user.get().getAvatar() != null) {

            response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            response.setContentLength(user.get().getAvatar().length);
            response.getOutputStream().write(user.get().getAvatar());

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
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
        public static final String AVATAR = "^/[A-Z,a-z]+/?$";

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

}

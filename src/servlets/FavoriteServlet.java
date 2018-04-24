package servlets;

import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite")
public class FavoriteServlet extends BaseServlet {
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("username") == null) response.sendRedirect("index.jsp");
        super.service(request, response, "FavoriteServlet");
    }

    public void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UserService userService = new UserServiceImpl();
            userService.addFavorite((String) request.getSession().getAttribute("username"), Integer.parseInt(request.getParameter("book")));
            response.setContentType("text/plain");
            response.getWriter().write("200 OK");
        } catch (NullPointerException e) {
            response.sendError(404);
        } catch (Exception e) {
            response.sendError(500);
        }
    }

    public void remove(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UserService userService = new UserServiceImpl();
            userService.cancelFavorite((String) request.getSession().getAttribute("username"), Integer.parseInt(request.getParameter("book")));
            response.setContentType("text/plain");
            response.getWriter().write("200 OK");
        } catch (Exception e) {
            response.sendError(404);
        }
    }
}

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
        UserService userService = new UserServiceImpl();
        userService.addFavorite((String) request.getSession().getAttribute("username"),
                Integer.parseInt(request.getParameter("book")));
        response.sendRedirect("/book?id=" + request.getParameter("book"));
    }

    public void remove(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        userService.cancelFavorite((String) request.getSession().getAttribute("username"),
                Integer.parseInt(request.getParameter("book")));
        response.sendRedirect("/book?id=" + request.getParameter("book"));
    }
}

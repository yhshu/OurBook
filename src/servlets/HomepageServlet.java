package servlets;

import model.Book;
import model.User;
import service.BookService;
import service.UserService;
import service.impl.BookServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomepageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        BookService bookService = new BookServiceImpl();
        UserService userService = new UserServiceImpl();
        if(req.getSession().getAttribute("username")==null) resp.sendRedirect("index.jsp");
        if (username == null) username = (String) req.getSession().getAttribute("username");
        if (username.equals(req.getSession().getAttribute("username"))) {
            Book[] books = bookService.findByEditor(username);
            User[] followers = userService.getFollowers(username);
            User[] followees = userService.getFollowees(username);
            req.setAttribute("books", books);
            req.setAttribute("followers", followers);
            req.setAttribute("followees", followees);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("homepage.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}

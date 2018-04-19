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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("user");
        BookService bookService = new BookServiceImpl();
        UserService userService = new UserServiceImpl();
        if(request.getSession().getAttribute("username")==null) response.sendRedirect("index.jsp");
        if (username == null) username = (String) request.getSession().getAttribute("username");
        if (username.equals(request.getSession().getAttribute("username"))) {
            Book[] books = bookService.findByEditor(username);
            User[] followers = userService.getFollowers(username);
            User[] followees = userService.getFollowees(username);
            request.setAttribute("books", books);
            request.setAttribute("followers", followers);
            request.setAttribute("followees", followees);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("homepage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
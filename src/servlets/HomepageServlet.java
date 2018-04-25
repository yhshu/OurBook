package servlets;

import model.Book;
import model.User;
import service.BookService;
import service.UserService;
import service.impl.BookServiceImpl;
import service.impl.FollowServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/home")
public class HomepageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String username = request.getParameter("user");
            BookService bookService = new BookServiceImpl();
            UserService userService = new UserServiceImpl();
            String currentUser = (String) session.getAttribute("username");
            String redirect = "homepage.jsp";
            if (currentUser == null) {
                redirect = "login.jsp";
            } else {
                if (username == null) username = currentUser;
                if (!currentUser.equals(username)) {
                    redirect = "othersHome.jsp";
                    request.setAttribute("isFollowing", new FollowServiceImpl().isFollowing(currentUser, username));
                }
                User user = userService.find(username);
                Book[] books = bookService.findByEditor(username);
                Book[] favorites = bookService.getFavorites(username);
                User[] followers = userService.getFollowers(username);
                User[] followees = userService.getFollowees(username);
                request.setAttribute("username", username);
                request.setAttribute("favorites", favorites);
                request.setAttribute("books", books);
                request.setAttribute("nickname", user.getNickname());
                request.setAttribute("description", user.getDescription());
                request.setAttribute("avatar", user.getAvatar());
                request.setAttribute("followers", followers);
                request.setAttribute("followees", followees);
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(redirect);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            response.sendError(404);
        }
    }
}
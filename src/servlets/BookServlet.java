package servlets;

import model.Book;
import service.BookService;
import service.FollowService;
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
import java.io.IOException;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dis = "book.jsp";
        if (req.getSession().getAttribute("username") == null) dis = "login.jsp";
        else {
            int bookID = Integer.parseInt(req.getParameter("id"));
            BookService bookService = new BookServiceImpl();
            UserService userService = new UserServiceImpl();
            FollowService followService = new FollowServiceImpl();
            Book book = bookService.find(bookID);
            String username = (String) req.getSession().getAttribute("username");
            boolean isFavorite = userService.isFavorite(username, bookID);
            boolean isFollowing = followService.isFollowing(username, book.getChiefEditor());
            req.setAttribute("editorNickname", userService.find(book.getChiefEditor()).getNickname());
            req.setAttribute("bookID", bookID);
            req.setAttribute("bookName", book.getName());
            req.setAttribute("editor", book.getChiefEditor());
            req.setAttribute("description", book.getDescription());
            req.setAttribute("cover", book.getCover());
            req.setAttribute("chapters", bookService.getChapters(bookID));
            req.setAttribute("isFavorite", isFavorite);
            req.setAttribute("isFollowing", isFollowing);
            // 重定向
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(dis);        dispatcher.forward(req, resp);
    }
}

package servlets.Book;

import model.Book;
import model.Comment;
import model.User;
import service.BookService;
import service.CommentService;
import service.FollowService;
import service.UserService;
import service.impl.BookServiceImpl;
import service.impl.CommentServiceImpl;
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

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String dis = "book.jsp";
            if (session.getAttribute("username") == null)
                dis = "login.jsp";
            else {
                int bookID = Integer.parseInt(request.getParameter("id"));
                BookService bookService = new BookServiceImpl();
                UserService userService = new UserServiceImpl();
                FollowService followService = new FollowServiceImpl();
                CommentService commentService = new CommentServiceImpl();

                Book book = bookService.find(bookID);
                String username = (String) session.getAttribute("username");
                User chiefEditor = userService.find(book.getChiefEditor());
                User[] collaborators = bookService.getCollaborators(bookID);
                boolean isCollaborator = false;
                if (bookService.authority(bookID, username) == 1)
                    isCollaborator = true;
                boolean isFavorite = userService.isFavorite(username, bookID);
                boolean isFollowing = followService.isFollowing(username, book.getChiefEditor());
                Comment[] comments = commentService.findByBookID(bookID);

                request.setAttribute("chiefEditor", chiefEditor);
                request.setAttribute("bookID", bookID);
                request.setAttribute("chapterNum", book.getChapterNum());
                request.setAttribute("bookName", book.getName());
                request.setAttribute("description", book.getDescription());
                request.setAttribute("cover", book.getCover());
                request.setAttribute("chapters", bookService.getChapters(bookID));
                request.setAttribute("isFavorite", isFavorite);
                request.setAttribute("isFollowing", isFollowing);
                request.setAttribute("collaborators", collaborators);
                request.setAttribute("isCollaborator", isCollaborator);
                request.setAttribute("comments", comments);
                bookService.click(username, bookID);
            }
            // 重定向
            RequestDispatcher dispatcher = request.getRequestDispatcher(dis);
            dispatcher.forward(request, response);
        } catch (NullPointerException e) {
            e.printStackTrace();
            response.sendError(404);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}

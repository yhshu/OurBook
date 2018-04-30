package servlets.Comment;

import service.CommentService;
import service.impl.CommentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommentService commentService = new CommentServiceImpl();
        String method = request.getParameter("method");
        String username = (String) request.getSession().getAttribute("username");
        try {
            switch (method) {
                case "add":
                    int bookID = Integer.parseInt(request.getParameter("bookID"));
                    String content = request.getParameter("content");
                    commentService.add(username, bookID, content);
                    response.getWriter().write("/book?id=" + bookID);
                    break;
                case "delete":
                    int commentID = Integer.parseInt(request.getParameter("commentID"));
                    if (username.equals(commentService.find(commentID).getUsername()))
                        commentService.delete(commentID);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
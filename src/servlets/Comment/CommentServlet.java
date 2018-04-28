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
        switch (method) {
            case "add":
                int bookID = Integer.parseInt(request.getParameter("bookID"));
                String username = request.getParameter("username");
                String content = request.getParameter("content");
                commentService.add(username, bookID, content);
                break;
            case "delete":
                int commentID = Integer.parseInt(request.getParameter("commentID"));
                commentService.delete(commentID);
                break;
            default:
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
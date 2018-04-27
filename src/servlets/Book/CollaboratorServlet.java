package servlets.Book;

import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/collaborator")
public class CollaboratorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        BookService bookService = new BookServiceImpl();
        String collaborator = request.getParameter("collaborator");
        int bookID = Integer.parseInt(request.getParameter("bookID"));
        String username = (String) session.getAttribute("username");
        System.out.println("CollaboratorServlet: " + username + " 正在添加协作者 " + collaborator);
        try {
            if (bookService.setCollaborators(bookID, collaborator, username)) {
                response.setContentType("text/plain");
                response.getWriter().write("/book?id=" + bookID);
            } else throw new Exception();
        } catch (Exception e) {
            response.sendError(500);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

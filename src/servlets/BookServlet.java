package servlets;

import model.Book;
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

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("username") == null) response.sendRedirect("index.jsp");
        int bookID = Integer.parseInt(request.getParameter("id"));
        BookService bookService = new BookServiceImpl();
        UserService userService = new UserServiceImpl();
        Book book = bookService.find(bookID);
        request.setAttribute("editorNickname", userService.find(book.getChiefEditor()).getNickname());
        request.setAttribute("bookID", bookID);
        request.setAttribute("bookName", book.getName());
        request.setAttribute("editor", book.getChiefEditor());
        request.setAttribute("description", book.getDescription());
        request.setAttribute("cover", book.getCover());
        request.setAttribute("chapters", bookService.getChapters(bookID));
        request.setAttribute("isFavorite",
                userService.isFavorite((String) request.getSession().getAttribute("username"), bookID));
        // 重定向
        RequestDispatcher dispatcher = request.getRequestDispatcher("book.jsp");
        dispatcher.forward(request, response);
    }
}

package servlets;

import model.Book;
import model.Chapter;
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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        UserService userService = new UserServiceImpl();
        String keywords = req.getParameter("keywords");
        String type = req.getParameter("type");
        Book[] books = bookService.findByKeywords(keywords);
        User[] editors = new User[books.length];
        for (int i = 0; i < books.length; i++) editors[i] = userService.find(books[i].getChiefEditor());
        req.setAttribute("keywords", keywords);
        req.setAttribute("type", type);
        req.setAttribute("books", books);
        req.setAttribute("editors", editors);
        RequestDispatcher dispatcher = req.getRequestDispatcher("search.jsp");
        dispatcher.forward(req, resp);
    }
}

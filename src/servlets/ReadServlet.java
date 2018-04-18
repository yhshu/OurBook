package servlets;

import model.Chapter;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@WebServlet("/read")
public class ReadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        Chapter chapter = bookService.findChapter(Integer.parseInt(request.getParameter("book")), Integer.parseInt(request.getParameter("sequence")));
        request.setAttribute("name", chapter.getName());
        request.setAttribute("reader", new BufferedReader(new FileReader(
                request.getServletContext().getRealPath(chapter.getContent()))));
        RequestDispatcher dispatcher = request.getRequestDispatcher("content.jsp");
        dispatcher.forward(request, response);
    }
}

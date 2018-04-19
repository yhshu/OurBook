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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/read")
public class ReadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        Chapter chapter = bookService.findChapter(Integer.parseInt(request.getParameter("book")), Integer.parseInt(request.getParameter("sequence")));
        request.setAttribute("name", chapter.getName());
        InputStreamReader isr = new InputStreamReader(new FileInputStream(request.getServletContext().getRealPath(chapter.getContent())), "UTF-8");
        BufferedReader read = new BufferedReader(isr);
        request.setAttribute("reader", read);
        RequestDispatcher dispatcher = request.getRequestDispatcher("content.jsp");
        dispatcher.forward(request, response);
    }
}

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
        if (request.getSession().getAttribute("username") == null) response.sendRedirect("index.jsp");
        BookService bookService = new BookServiceImpl();
        int bookID = Integer.parseInt(request.getParameter("book"));
        int sequence = Integer.parseInt(request.getParameter("sequence"));
        Chapter chapter = bookService.findChapter(bookID, sequence);
        request.setAttribute("name", chapter.getName());
        // 生成 reader
        InputStreamReader isr = new InputStreamReader(new FileInputStream(request.getServletContext().getRealPath(chapter.getContent())), "UTF-8");
        BufferedReader read = new BufferedReader(isr);
        request.setAttribute("reader", read);
        request.setAttribute("bookID", bookID);
        // 重定向
        RequestDispatcher dispatcher = request.getRequestDispatcher("content.jsp");
        dispatcher.forward(request, response);
    }
}

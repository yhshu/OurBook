package servlets.Chapter;

import model.Book;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        int bookID = Integer.parseInt(request.getParameter("book"));
        int sequence = Integer.parseInt(request.getParameter("sequence"));
        Chapter[] chapters = bookService.getChapters(bookID);
        Chapter chapter = bookService.findChapter(bookID, sequence);
        request.setAttribute("name", chapter.getName());
        // 生成 reader
        InputStreamReader isr = new InputStreamReader(new FileInputStream(request.getServletContext().getRealPath(chapter.getContent())), "UTF-8");
        BufferedReader read = new BufferedReader(isr);
        request.setAttribute("chapters", chapters);
        request.setAttribute("reader", read);
        request.setAttribute("bookID", bookID);
        request.setAttribute("sequence", sequence);
        // 重定向
        RequestDispatcher dispatcher = request.getRequestDispatcher("content.jsp");
        dispatcher.forward(request, response);
    }
}

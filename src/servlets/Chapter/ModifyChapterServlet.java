package servlets.Chapter;

import model.Chapter;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/modify")
public class ModifyChapterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BookService bookService = new BookServiceImpl();
            int bookID = Integer.parseInt(req.getParameter("bookID"));
            int sequence = Integer.parseInt(req.getParameter("sequence"));
            Chapter chapter = bookService.findChapter(bookID, sequence);
            String path = req.getServletContext().getRealPath(chapter.getContent());
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            String name = chapter.getName();
            String content = new String(encoded, "UTF-8");
            req.setAttribute("name", name);
            req.setAttribute("content", content);
            req.setAttribute("method", "modify");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/writeChapter.jsp");
            requestDispatcher.forward(req, resp);
        } catch (Exception e) {
            resp.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

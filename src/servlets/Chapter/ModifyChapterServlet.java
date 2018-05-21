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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/modify")
public class ModifyChapterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            BookService bookService = new BookServiceImpl();
            int bookID = Integer.parseInt(request.getParameter("book"));
            if (bookService.authority(bookID, (String) request.getSession().getAttribute("username")) > 0) {
                int sequence = Integer.parseInt(request.getParameter("sequence"));
                Chapter chapter = bookService.findChapter(bookID, sequence);
                String path = request.getServletContext().getRealPath(chapter.getContent());
                byte[] encoded = Files.readAllBytes(Paths.get(path));
                String name = chapter.getName();
                String content = new String(encoded, "UTF-8");
                request.setAttribute("name", name);
                request.setAttribute("content", content);
                request.setAttribute("method", "modify");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/writeChapter.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (Exception e) {
            response.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

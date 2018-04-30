package servlets.Chapter;

import com.google.gson.Gson;
import model.Edit;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        BookService bookService = new BookServiceImpl();
        try {
            int bookID = Integer.parseInt(request.getParameter("book_id"));
            int sequence = Integer.parseInt(request.getParameter("sequence"));
            Edit[] history = bookService.getHistory(bookID, sequence);
            String history_json = gson.toJson(history);
            response.getWriter().write(history_json);
        } catch (Exception e) {
            response.sendError(500);
            System.out.println("HistoryServlet: 获取章节历史记录失败");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

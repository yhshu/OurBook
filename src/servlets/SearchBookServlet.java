package servlets;

import model.Book;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SearchBookServlet")
public class SearchBookServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String keywords = request.getParameter("keywords");
        String type = request.getParameter("type");
        if (type == null) type = "book";
        BookService bookService = new BookServiceImpl();
        try {
            switch (type) {
                case "book":
                    request.setAttribute("books", bookService.findByKeywords(keywords));
                    break;
                case "chapter":
                    request.setAttribute("chapters", bookService.findChapterByKeywords(keywords));
                    break;
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("search.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

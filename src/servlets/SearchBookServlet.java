package servlets;

import model.Book;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        BookService bookService = new BookServiceImpl();
        try{
            request.setAttribute("books",bookService.findByKeywords(keywords));
            request.setAttribute("chapters",bookService.findChapterByKeywords(keywords));
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); // TODO dispatch request to jsp
            requestDispatcher.forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package servlets;

import model.Book;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChooseBookServlet extends BaseServlet{

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        try{
            Book book = bookService.find(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("chapters",bookService.getChapters(book.getID()));
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); // TODO dispatch request to jsp
            requestDispatcher.forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

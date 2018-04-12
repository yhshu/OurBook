package servlets;

import model.Book;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response, "BookServlet");
    }

    public void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        HttpSession session = request.getSession();
        String bookName = request.getParameter("bookName");
        String bookDescription = request.getParameter("bookDescription");
        //  String chiefEditor = (String) session.getAttribute("username"); // TODO test
        String keywords = request.getParameter("keywords");
        try {
            bookService.add(bookName, bookDescription, "1", keywords);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookServlet: 添加书目失败");
        }
        // TODO 添加完成后，请求重定向，查看本书
        response.sendRedirect("/homepage.jsp");
    }

    public void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keywords = request.getParameter("keywords");
        String searchType = request.getParameter("search_type");
        if (searchType == null) searchType = "book";
        BookService bookService = new BookServiceImpl();
        try {
            switch (searchType) {
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

    public void choose(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        try {
            super.doPost(request, response);
            Book book = bookService.find(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("chapters", bookService.getChapters(book.getID()));
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("");
            // TODO dispatch request to jsp
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

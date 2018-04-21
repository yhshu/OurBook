package servlets;

import model.Book;
import model.Chapter;
import model.User;
import service.BookService;
import service.UserService;
import service.impl.BookServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        UserService userService = new UserServiceImpl();
        String keywords = request.getParameter("keywords"); // 搜索关键词
        String type = request.getParameter("type"); // 搜索类型
        if (keywords == null) response.sendRedirect("index.jsp");
        switch (type) {
            case "book":
                Book[] books = bookService.findByKeywords(keywords);
                User[] editors = new User[books.length];
                for (int i = 0; i < books.length; i++)
                    editors[i] = userService.find(books[i].getChiefEditor());
                request.setAttribute("books", books);
                request.setAttribute("editors", editors);
                break;
            case "article":
                Chapter[] chapters = bookService.findChapterByKeywords(keywords);
                request.setAttribute("chapters", chapters);
                break;
            case "user":
                User[] users = userService.search(keywords);
                request.setAttribute("users", users);
                break;
            default:
                break;
        }
        request.setAttribute("keywords", keywords);
        request.setAttribute("type", type);
        // nav.jsp 请求 SearchServlet，本Servlet 处理后通过 search.jsp 渲染
        RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp");
        dispatcher.forward(request, response);
    }
}

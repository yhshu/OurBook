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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        BookService bookService = new BookServiceImpl();
        UserService userService = new UserServiceImpl();
        String keywords = request.getParameter("keywords"); // 搜索关键词
        String type = request.getParameter("type"); // 搜索类型
        if (keywords == null) {
            response.sendError(404);
            return;
        }
        switch (type) {
            case "book":
                String sort = request.getParameter("sort"); // 排序类型
                String range = request.getParameter("range"); // 排序时间范围
                if (sort == null) sort = "last_updated";
                Book[] books = bookService.findByKeywords(keywords, sort, range);
                Book[] fav = bookService.getFavorites((String) session.getAttribute("username"));
                request.setAttribute("books", books);
                request.setAttribute("favorites", fav);
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

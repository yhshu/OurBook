package servlets;

import dao.BookDao;
import dao.impl.BookDaoImpl;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response, "BookServlet");
    }

    public void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        BookDao bookDao = new BookDaoImpl();
        String bookName = request.getParameter("bookName");
        String bookDescription = request.getParameter("bookDescription");
        String editor = request.getParameter("editor");
        String keywords = request.getParameter("keywords");
        String cover = request.getParameter("cover");
        try {
            bookService.addBook(bookName, bookDescription, editor, keywords, null);
            // TODO cover 的逻辑
            System.out.println("BookServlet: 添加书目成功");
            // 添加成功后，请求重定向，查看本书
            response.sendRedirect("/book.jsp?id=" + bookDao.maxID());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookServlet: 添加书目失败");
        }
    }
}

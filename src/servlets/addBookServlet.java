package servlets;

import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends BaseServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        String bookName = request.getParameter("bookName");
        String bookDescription = request.getParameter("bookDescription");
        String keywords = request.getParameter("keywords");
        String chiefEditorName = null; // TODO 获取当前用户的用户名
        BookService bookService = new BookServiceImpl();
        try {
            bookService.add(bookName, bookDescription, chiefEditorName, keywords);
        } catch (Exception e) {
            request.setAttribute("message", "创建失败");
        }
        // TODO 添加完成后，请求重定向，查看本书
    }
}

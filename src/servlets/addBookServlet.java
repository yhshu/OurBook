package servlets;

import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


/**
 * 添加书目的 Servlet
 * 当用户访问 AddBookServlet 时，跳转到 newbook.jsp 页面
 */
@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String bookname = request.getParameter("bookName");
        String bookDescription = request.getParameter("bookDescription");
        String chiefEditorID = ""; // TODO 获取当前用户的ID
        BookService bookService = new BookServiceImpl();
        try {
            bookService.add(bookname, bookDescription, chiefEditorID);
        } catch (Exception e) {
            request.setAttribute("message", "创建失败");
        }
        // TODO 添加完成后，请求重定向，查看本书
    }
}

package servlets;

import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends BaseServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        HttpSession session = request.getSession();
        String bookName = request.getParameter("bookName");
        String bookDescription = request.getParameter("bookDescription");
        String chiefEditorName = (String) session.getAttribute("username"); // TODO test
        String keywords = request.getParameter("keywords");
        try {
            bookService.add(bookName, bookDescription, chiefEditorName, keywords);
        } catch (Exception e) {
            System.out.println("AddBookServlet: 添加书目失败");
        }
        // TODO 添加完成后，请求重定向，查看本书
        response.sendRedirect("/homepage.jsp");
    }
}

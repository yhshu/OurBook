package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;


/**
 * 添加书目的 Servlet
 * 当用户访问 addBookServlet 时，跳转到 newbook.jsp 页面
 */
public class addBookServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/newbook.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

package servlets;
// 项目引入lib servlet-api.jar

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 登录界面的 Servlet
 * 当用户访问 LoginServlet 时，跳转到 login.jsp 页面
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
    }
}

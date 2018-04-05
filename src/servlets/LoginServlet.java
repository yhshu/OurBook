package servlets;
// 项目引入lib servlet-api.jar

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends BaseServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        LoginServlet loginServlet = new LoginServlet();
        // TODO 完善登录逻辑
        // 第一次登录成功后，将用户名保存到 session 中
        HttpSession session = request.getSession();
        session.setAttribute("nickname", nickname);
        session.setMaxInactiveInterval(7 * 24 * 60 * 60);
    }
}

package servlets;
// 项目引入lib servlet-api.jar

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
        // 首次登录成功后，将用户名保存到 session 中
        HttpSession session = request.getSession();
        session.setAttribute("nickname", nickname);
        final int maxInactiveInterval = 7 * 24 * 60 * 60;
        session.setMaxInactiveInterval(maxInactiveInterval);
        // 将 JSESSIONID 持久化
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(maxInactiveInterval);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}

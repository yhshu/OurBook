package servlets;
// 项目引入lib servlet-api.jar

import service.UserService;
import service.impl.UserServiceImpl;

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserService userService = new UserServiceImpl();
        // TODO 完善登录逻辑
        userService.login(username, password);
        final int maxAge = 7 * 24 * 60 * 60;
        Cookie c_username = new Cookie("username", username);
        c_username.setMaxAge(maxAge);
        c_username.setPath("/");
        response.addCookie(c_username);
        Cookie c_nickname = new Cookie("nickname", userService.getNickname(username));
        c_nickname.setMaxAge(maxAge);
        c_nickname.setPath("/");
        response.addCookie(c_nickname);
        // 首次登录成功后，将用户名保存到 session 中
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        final int maxInactiveInterval = 7 * 24 * 60 * 60;
        session.setMaxInactiveInterval(maxInactiveInterval);
        // 将 JSESSIONID 持久化
        Cookie c_JSESSIONID = new Cookie("JSESSIONID", session.getId());
        c_JSESSIONID.setMaxAge(maxInactiveInterval);
        c_JSESSIONID.setPath("/");
        response.addCookie(c_JSESSIONID);
        // 登录成功后，跳转到个人主页
        response.sendRedirect("/homepage.jsp");
    }
}

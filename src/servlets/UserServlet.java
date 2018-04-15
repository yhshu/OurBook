package servlets;

import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response, "UserServlet");
    }

    public void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        System.out.println("【用户注册】用户名：" + username + "，昵称：" + nickname + "，密码：" + password);
        UserService userService = new UserServiceImpl();
        try {
            userService.register(username, nickname, password);
            // 注册成功后，请求重定向，跳转到登录界面
            response.sendRedirect("/login.jsp");
        } catch (Exception e) {
            request.setAttribute("message", "注册失败");
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserService userService = new UserServiceImpl();
        if (userService.login(username, password)) { // 用户名密码匹配
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
            final int maxInactiveInterval = 7 * 24 * 60 * 60;
            session.setMaxInactiveInterval(maxInactiveInterval);
            session.setAttribute("username", username);
            // 将 JSESSIONID 持久化
            Cookie c_JSESSIONID = new Cookie("JSESSIONID", session.getId());
            c_JSESSIONID.setMaxAge(maxInactiveInterval);
            c_JSESSIONID.setPath("/");
            response.addCookie(c_JSESSIONID);
            // 登录成功后，跳转到个人主页
            response.sendRedirect("/homepage.jsp");
        } else { // 用户名或密码错误
            // TODO 用户名密码验证失败的反馈
        }
    }
}
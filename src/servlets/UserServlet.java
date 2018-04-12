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

    public void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO 重写servlet后，退出登录尚未测试
        // 删除所有 cookie，关闭自动登录
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0); // 立即删除 cookie
            response.addCookie(cookie);
            System.out.println("UserServlet: 退出成功，跳转到登录页");
        }
        response.flushBuffer();
        response.sendRedirect("/login.jsp");
    }
}

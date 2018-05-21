package servlets.User;

import model.User;
import service.NotificationService;
import service.UserService;
import service.impl.NotificaServiceImpl;
import service.impl.UserServiceImpl;
import servlets.BaseServlet;

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

    public void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String originalPassword = request.getParameter("original");
        String newPassword = request.getParameter("new");
        if (newPassword.length() < 6) response.sendError(403);
        UserService userService = new UserServiceImpl();
        User user = userService.find(username);
        if (!user.getPassword().equals(originalPassword)) response.sendError(400);
        if (userService.modifyPassword(username, newPassword)) {
            response.setStatus(200);
            response.getWriter().write("SUCCESS");
        } else {
            response.sendError(520);
        }
    }

    public void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");

        if (username.length() < 6 || password.length() < 6 || username.length() > 20 || password.length() > 20)
            response.sendError(400);

        System.out.println("【用户注册】用户名：" + username + "，昵称：" + nickname + "，密码：" + password);

        UserService userService = new UserServiceImpl();
        NotificationService notificationService = new NotificaServiceImpl();

        try {
            if (userService.register(username, nickname, password)) {
                notificationService.add(username, "欢迎来到OurBook，" + nickname + "！", "<a href='/index'>OurBook</a>" + "是免费的在线创作社区，你可以在这里与他人共同编写书籍，你们的作品将被分享给所有人。开始你的<a href='/create'>创作</a>之旅吧！");
                // 注册成功后，请求重定向，跳转到登录界面
                response.setContentType("text/plain");
                response.getWriter().write("/login");
            } else {
                response.sendError(403);
            }
        } catch (Exception e) {
            response.sendError(520);
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String certCode_input = request.getParameter("checkcode");
        String certCode_ans = (String) session.getAttribute("certCode");

        if (!certCode_ans.equals(certCode_input)) {
            response.setContentType("text/plain");
            response.getWriter().write("verification failed");
            return;
        }

        UserService userService = new UserServiceImpl();
        User user = userService.login(username, password);
        if (user != null) { // 用户名密码匹配
            final int maxAge = 7 * 24 * 60 * 60;
            Cookie c_username = new Cookie("username", username);
            c_username.setMaxAge(maxAge);
            c_username.setPath("/");
            response.addCookie(c_username);
            Cookie c_nickname = new Cookie("nickname", user.getNickname());
            c_nickname.setMaxAge(maxAge);
            c_nickname.setPath("/");
            response.addCookie(c_nickname);
            // 首次登录成功后，将用户名保存到 session 中
            final int maxInactiveInterval = 7 * 24 * 60 * 60;
            session.setMaxInactiveInterval(maxInactiveInterval);
            session.setAttribute("username", username);
            session.setAttribute("nickname", user.getNickname());
            session.setAttribute("avatar", user.getAvatar());
            // 将 JSESSIONID 持久化
            Cookie c_JSESSIONID = new Cookie("JSESSIONID", session.getId());
            c_JSESSIONID.setMaxAge(maxInactiveInterval);
            c_JSESSIONID.setPath("/");
            response.addCookie(c_JSESSIONID);
            // 登录成功后，请求 HomepageServlet 以跳转到个人主页
            response.setContentType("text/plain");
            response.getWriter().write("/index");
        } else { // 用户名或密码错误
            response.sendError(403);
        }
    }
}

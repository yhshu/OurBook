package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends BaseServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        // 删除所有 cookie，关闭自动登录
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0); // 立即删除 cookie
            response.addCookie(cookie);
            System.out.println("LogoutServlet: 退出成功，跳转到登录页");
        }
        response.sendRedirect("/login.jsp");
    }
}


package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class BaseServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("nickname")) {
                if (session.getAttribute("nickname") != null) {
                    // TODO 登录成功，跳转到主页
                    response.sendRedirect("/homepage.jsp");
                    System.out.println("BaseServlet: 自动登录成功，跳转到个人主页");
                } else {
                    // TODO 跳转登录界面
                    response.sendRedirect("/login.jsp");
                    System.out.println("BaseServlet: 自动登录失败，跳转到登录页");
                }
                break;
            }
        }
    }
}

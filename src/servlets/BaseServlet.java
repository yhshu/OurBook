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
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(nickname)) {
                if (session.getAttribute("nickname") != null) {
                    // TODO 登陆成功，跳转到主页
                    break;
                }
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute();
    }
}

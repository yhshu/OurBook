package servlets;

import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends BaseServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
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
}

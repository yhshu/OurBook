package servlets;

import dao.FollowDao;
import dao.impl.FollowDaoImpl;
import service.FollowService;
import service.impl.FollowServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 关注用户、取消关注用户
 */
@WebServlet("/follow")
public class FollowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            FollowService followservice = new FollowServiceImpl();
            String followee = request.getParameter("followee");
            String follower = (String) request.getSession().getAttribute("username");
            String method = request.getParameter("method");
            // 进行关注
            if (method.equals("add")) {
                followservice.addFollow(followee, follower);
                System.out.println("followee: " + followee + "; " + "follower: " + follower);
                System.out.println("FollowServlet: 关注成功");
                // 关注成功后，返回主页
                response.sendRedirect("/home?user=" + followee);
            }
            // 取消关注
            else if (method.equals("remove")) {
                followservice.delFollow(followee, follower);
                System.out.println("followee: " + followee + "; " + "follower: " + follower);
                System.out.println("FollowServlet: 取消关注成功");
                // 取消成功后，返回主页
                response.sendRedirect("/home?user=" + followee);
            }
        } catch (NullPointerException e) {
            response.sendError(404);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}

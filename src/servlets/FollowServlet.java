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
@WebServlet("/FollowServlet")
public class FollowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        FollowService followservice = new FollowServiceImpl();
        FollowDao followdao = new FollowDaoImpl();
        HttpSession session = request.getSession();
        String followee = request.getParameter("followee");
        String follower = request.getParameter("follower");
        String book_id = request.getParameter("book_id");
        String method = request.getParameter("method");
        if (method.equals("addfollowee")) {
            try {
                followservice.addFollow(followee, follower);
                System.out.println("followee:" + followee + "    " + "follower:" + follower);
                System.out.println("FollowServlet: 关注成功");
                // 关注成功后，返回主页
                response.sendRedirect("/home");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("FollowServlet: 添加失败");
            }
        } else if (method.equals("delfollowee")) {
            try {
                followservice.delFollow(followee, follower);
                System.out.println("followee:" + followee + "    " + "follower" + follower);
                System.out.println("FollowServlet: 关注成功");
                // 取消成功后，返回主页
                response.sendRedirect("/home");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("FollowServlet: 取消关注失败");
            }
        }
    }
}

package servlets;

import dao.FollowDao;
import dao.impl.FollowDaoImpl;
import service.FollowService;
import service.impl.FollowServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program:OurBook
 * @description: 关注用户、取消关注用户
 * @create: 04-18-20
 */
public class FollowServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FollowService followservice = new FollowServiceImpl();
        FollowDao followdao = new FollowDaoImpl();
        String followee = request.getParameter("followee");
        String follower = request.getParameter("follower");
        try{
            followservice.addFollow(followee,follower);
            System.out.println("BookServlet: 关注书目成功");
            // 添加成功后，请求重定向，查看本书
           //response.sendRedirect("/homepage.jsp?id=" + bookDao.maxID());
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookServlet: 添加书目失败");
        }

    }
}

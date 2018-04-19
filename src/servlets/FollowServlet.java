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
 * @program:OurBook
 * @description: 关注用户、取消关注用户
 * @create: 04-18-20
 */
@WebServlet("/FollowServlet")
public class FollowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response .setContentType("text/html");
        FollowService followservice = new FollowServiceImpl();
        FollowDao followdao = new FollowDaoImpl();
        HttpSession session = request.getSession();
/*      String followee = (String)session.getAttribute("followee");
        String follower = (String)session.getAttribute("follower");*/
        String followee = request.getParameter("followee");
        System.out.println("****");
        String follower = request.getParameter("follower");
        String book_id=request.getParameter("book_id");
        try{
            followservice.addFollow(followee,follower);
            System.out.println("BookServlet: 关注成功");
            // 关注成功后，返回主页
            response.sendRedirect("/book.jsp?id="+book_id);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookServlet: 添加失败");
        }

    }
}

package servlets;

import dao.MessageDao;
import dao.NotificationDao;
import dao.impl.MessageDaoImpl;
import dao.impl.NotificationDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/PollingServlet")
public class PollingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder json = new StringBuilder("{");
        String username = (String) req.getSession().getAttribute("username");
        MessageDao messageDao = new MessageDaoImpl();
        NotificationDao notificationDao = new NotificationDaoImpl();
        int messageNum = messageDao.getUnreadNumber(username);
        int notificationNum = notificationDao.getByUsername(username, false).length;
        json.append("'messages':'").append(messageNum).append("','notifications':'").append(notificationNum).append("'}");
        try {
            PrintWriter out = resp.getWriter();
            out.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

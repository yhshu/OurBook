package servlets.User;

import model.Notification;
import service.NotificationService;
import service.impl.NotificaServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/notifications")
public class NotificationCenterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotificationService notificationService = new NotificaServiceImpl();
        String username = (String) req.getSession().getAttribute("username");
        Notification[] read = notificationService.getRead(username);
        Notification[] unread = notificationService.getUnread(username);
        req.setAttribute("read", read);
        req.setAttribute("unread", unread);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/notifications.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}

package servlets.User;

import service.NotificationService;
import service.impl.NotificaServiceImpl;
import servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/notification")
public class NotificationServlet extends BaseServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("username") == null) response.sendRedirect("index.jsp");
        super.service(request, response, "NotificationServlet");
    }

    public void read(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NotificationService notificationService = new NotificaServiceImpl();
        notificationService.read(Integer.parseInt(request.getParameter("ID")));
    }

    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            NotificationService notificationService = new NotificaServiceImpl();
            notificationService.delete(Integer.parseInt(request.getParameter("ID")));
            response.setContentType("text/plain");
            response.getWriter().write("200 OK");
        } catch (NullPointerException e) {
            response.sendError(404);
        } catch (Exception e) {
            response.sendError(500);
        }
    }

    public void clearRead(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            NotificationService notificationService = new NotificaServiceImpl();
            notificationService.clearRead((String) request.getSession().getAttribute("username"));
            response.setContentType("text/plain");
            response.getWriter().write("200 OK");
        } catch (NullPointerException e) {
            response.sendError(404);
        } catch (Exception e) {
            response.sendError(500);
        }
    }

}

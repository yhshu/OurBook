package servlets.User;

import service.MessageService;
import service.impl.MessageServiceImpl;
import servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/message")
public class MessageServlet extends BaseServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("username") == null) response.sendRedirect("index.jsp");
        super.service(request, response, "MessageServlet");
    }

    public void send(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MessageService messageService = new MessageServiceImpl();
            String username = (String) request.getSession().getAttribute("username");
            String to = request.getParameter("to");
            String content = request.getParameter("content");
            messageService.send(to, username, content);
            response.setContentType("text/plain");
            response.getWriter().write("200 OK");
        } catch (NullPointerException e) {
            response.sendError(404);
        } catch (Exception e) {
            response.sendError(500);
        }
    }

    public void read(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MessageService messageService = new MessageServiceImpl();
        String username = (String) request.getSession().getAttribute("username");
        String from = request.getParameter("from");
        messageService.read(username, from);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MessageService messageService = new MessageServiceImpl();
            messageService.delete(Integer.parseInt(request.getParameter("ID")));
            response.setContentType("text/plain");
            response.getWriter().write("200 OK");
        } catch (NullPointerException e) {
            response.sendError(404);
        } catch (Exception e) {
            response.sendError(500);
        }
    }

    public void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            MessageService messageService = new MessageServiceImpl();
            String username = (String) request.getSession().getAttribute("username");
            String target = request.getParameter("target");
            messageService.clear(username, target);
            response.setContentType("text/plain");
            response.getWriter().write("200 OK");
        } catch (NullPointerException e) {
            response.sendError(404);
        } catch (Exception e) {
            response.sendError(500);
        }
    }

}

package Util;

import service.impl.MessageServiceImpl;
import service.impl.NotificaServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户访问权限的过滤器
 *
 * @author viano
 */
public class UserFilter implements Filter {

    public void destroy() {
        // TODO Auto-generated method stub
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 设置请求的字符编码
        request.setCharacterEncoding("UTF-8");
        // 设置返回请求的字符编码
        response.setCharacterEncoding("UTF-8");
        // 转换ServletRequest为 HttpServletRequest
        HttpServletRequest req = (HttpServletRequest) request;
        // 转换ServletResponse为HttpServletRequest
        HttpServletResponse res = (HttpServletResponse) response;
        // 获取Session
        HttpSession session = req.getSession();
        // 获取Session中存储的对象
        String username = (String) session.getAttribute("username");
        // 获取当前请求的URI
        String url = req.getRequestURI();
        // 判断请求的URI是否为不允许过滤的URI
        if (url.contains("/resources/book")) {
            res.sendRedirect(req.getContextPath() + "/index");
            return;
        }
        // 自动获取通知数
        if (!(url.startsWith("/css") || url.startsWith("/img") ||
                url.startsWith("/js") || url.startsWith("/resources"))) {
            if (username != null) {
                req.getSession().setAttribute("unreadNotifications", new NotificaServiceImpl().getUnread(username).length);
                req.getSession().setAttribute("unreadMessages", new MessageServiceImpl().getUnreadNumber(username));
            } else if (!(url.startsWith("/register") || (url.startsWith("/login") || (url.startsWith("/UserServlet"))))) {
                res.sendRedirect("/register");
                return;
            }
        }
        chain.doFilter(request, response);
        res.setHeader("Cache-Control", "no-store");
        res.setDateHeader("Expires", 0);
        res.setHeader("Pragma", "no-cache");
        res.flushBuffer();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
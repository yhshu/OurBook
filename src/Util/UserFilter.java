package Util;

import service.impl.MessageServiceImpl;
import service.impl.NotificaServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        String url = req.getRequestURI();  // 获取当前请求的URI
        if (url.contains("/resources/book")) {  // 判断请求的URI是否为不允许过滤的URI
            res.sendRedirect(req.getContextPath() + "/index");
            return;
        }
        ArrayList<String> noAccessList = new ArrayList<>();
        noAccessList.add("/css");
        noAccessList.add("/img");
        noAccessList.add("/js");
        noAccessList.add("/resources");
        ArrayList<String> hasAccessList = new ArrayList<>();
        hasAccessList.add("/register");
        hasAccessList.add("/login");
        hasAccessList.add("/UserServlet");
        hasAccessList.add("/checkCode");

        if (!URLStartsWith(noAccessList, url)) { // 限制访问
            if (username != null) { // 已登录，获取通知
                req.getSession().setAttribute("unreadNotifications", new NotificaServiceImpl().getUnread(username).length);
                req.getSession().setAttribute("unreadMessages", new MessageServiceImpl().getUnreadNumber(username));
            } else if (!URLStartsWith(hasAccessList, url)) { // 未登录且访问特定页面以外的页面，跳转登录页
                res.sendRedirect("/login");
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

    private boolean URLStartsWith(ArrayList<String> urlList, String url) {
        for (String aurl : urlList) {
            if (url.startsWith(aurl))
                return true;
        }
        return false;
    }
}
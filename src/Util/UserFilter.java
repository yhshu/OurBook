package Util;

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
        Object o = session.getAttribute("username");
        // 获取当前请求的URI
        String url = req.getRequestURI();
        // 判断请求的URI是否为不允许过滤的URI
        if (url.contains("/resources/book"))
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        else {
            chain.doFilter(request, response);
            res.setHeader("Cache-Control", "no-store");
            res.setDateHeader("Expires", 0);
            res.setHeader("Pragma", "no-cache");
            res.flushBuffer();
        }

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
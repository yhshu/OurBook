package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response, String subClassName) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            // 1. 获得方法名称
            String methodName = request.getParameter("method");

            // 2. 通过方法名和方法所需要的参数获得Method对象
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            // 3. 通过Method对象调用方法
            String result = (String) method.invoke(this, request, response);
            if (result != null && result.trim().length() > 0) {// 如果返回的result不为空
                int index = result.indexOf(":");// 获得第一个冒号的位置
                if (index == -1) {// 如果没有冒号，就使用转发
                    request.getRequestDispatcher(result).forward(request, response);
                } else {// 如果有冒号
                    String start = result.substring(0, index);// 截取前缀
                    String path = result.substring(index + 1);// 截取路径
                    if (start.equalsIgnoreCase("f")) {// 前缀为f表示使用转发
                        request.getRequestDispatcher(path).forward(request, response);
                    } else if (start.equalsIgnoreCase("r")) {// 前缀为r表示使用重定向
                        response.sendRedirect(request.getContextPath() + path);
                    }
                }
            }
        } catch (Exception e) {
            response.sendError(404, e.getMessage());
        }
    }
}

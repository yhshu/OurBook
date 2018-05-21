package servlets.User;

import Util.FileUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/modifyUser")
public class ModifyUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String nickname = "", description = "", filename = null;
        UserService userService = new UserServiceImpl();
        try {
            if (ServletFileUpload.isMultipartContent(request)) {  // 判断获取的是不是文件
                DiskFileItemFactory disk = new DiskFileItemFactory();
                disk.setSizeThreshold(20 * 1024);      // 设置内存可存字节数
                disk.setRepository(disk.getRepository());  // 设置临时文件目录
                ServletFileUpload up = new ServletFileUpload(disk);
                List list = up.parseRequest(request);   // 获取上传列表
                for (Object aList : list) {
                    FileItem fm = (FileItem) aList; // 遍历列表
                    if (!fm.isFormField()) {
                        if (fm.getName() == null || fm.getName().equals("")) break;
                        File oldAvatar = new File(this.getServletContext().getRealPath((String) request.getSession().getAttribute("avatar")));
                        if (oldAvatar.delete()) System.out.println("ModifyUserServlet: 原头像删除成功");
                        else System.out.println("ModifyUserServlet: 原头像删除失败");
                        String serverPath = this.getServletContext().getRealPath("resources/avatar/");  // 获取文件全路径名
                        String extension = fm.getName().substring(fm.getName().lastIndexOf("."));
                        String filePath = serverPath + session.getAttribute("username") + extension;
                        filename = "resources/avatar/" + session.getAttribute("username") + extension;
                        int status = FileUtil.uploadImage(fm, filePath);
                        if (status != 200) {
                            response.sendError(status);
                            return;
                        }
                    } else {
                        String foename = fm.getFieldName(); // 获取表单元素名
                        String con = fm.getString("UTF-8");
                        //表单元素
                        switch (foename) {
                            case "new_nickname":
                                if (con == null || con.trim().equals("")) {
                                    response.sendError(400);
                                    return;
                                }
                                nickname = con;
                                break;
                            case "new_description":
                                description = con;
                                break;
                        }
                    }
                }
                if (filename == null) filename = (String) request.getSession().getAttribute("avatar");
                userService.modify(username, nickname, description, filename);
                session.setAttribute("avatar", filename);
                session.setAttribute("nickname", nickname);
            }
            System.out.println("ModifyUserServlet: 修改用户信息成功");
            response.setContentType("text/plain");
            response.getWriter().write("/home");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ModifyUserServlet: 修改用户信息失败");
            response.sendError(520);
        }
    }
}

package servlets.User;

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
        String nickname = "", description = "", message = "", filename = "";
        UserService userService = new UserServiceImpl();
        try {
            if (ServletFileUpload.isMultipartContent(request)) {  // 判断获取的是不是文件
                DiskFileItemFactory disk = new DiskFileItemFactory();
                disk.setSizeThreshold(20 * 1024);      // 设置内存可存字节数
                disk.setRepository(disk.getRepository());  // 设置临时文件目录
                ServletFileUpload up = new ServletFileUpload(disk);
                int maxsize = 2 * 1024 * 1024;
                List list = up.parseRequest(request);   // 获取上传列表
                for (Object aList : list) {
                    FileItem fm = (FileItem) aList; // 遍历列表
                    if (!fm.isFormField()) {
                        String filePath = fm.getName();  // 获取文件全路径名
                        if (filePath.equals("")) break;
                        if (fm.getSize() > maxsize) {
                            message = "文件太大了，不要超过2MB";
                            break;
                        }
                        String extension = filePath.substring(filePath.lastIndexOf("."));
                        filename = "/resources/avatar/" + session.getAttribute("username") + extension;
                        File saveFile = new File(this.getServletContext().getRealPath(filename));
                        fm.write(saveFile); // 向文件中写入数据
                        message = "文件上传成功！";
                    } else {
                        String foename = fm.getFieldName(); // 获取表单元素名
                        String con = fm.getString("UTF-8");
                        //表单元素
                        switch (foename) {
                            case "new_nickname":
                                nickname = con;
                                break;
                            case "new_description":
                                description = con;
                                break;
                        }
                    }
                }
                if (filename.equals("")) filename = (String) session.getAttribute("avatar");
                userService.modify(username, nickname, description, filename);
                session.setAttribute("avatar", filename);
                session.setAttribute("nickname", nickname);
            }
            System.out.println("ModifyUserServlet: 修改用户信息成功");
            request.setAttribute("result", message);
            response.sendRedirect("/home");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookServlet: 添加书目失败");
        }
    }
}

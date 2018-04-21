package servlets;

import dao.BookDao;
import dao.impl.BookDaoImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/addBook")
public class AddBookServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("username")==null) response.sendRedirect("index.jsp");
        BookService bookService = new BookServiceImpl();
        BookDao bookDao = new BookDaoImpl();
        String editor = (String) request.getSession().getAttribute("username");
        String bookName = "";
        String bookDescription = "";
        String keywords = "";
        String message = null;
        String filename = null;
        try {
            if (ServletFileUpload.isMultipartContent(request)) {  // 判断获取的是不是文件
                DiskFileItemFactory disk = new DiskFileItemFactory();
                disk.setSizeThreshold(20 * 1024);      // 设置内存可存字节数
                disk.setRepository(disk.getRepository());  // 设置临时文件目录
                ServletFileUpload up = new ServletFileUpload(disk);
                int maxsize = 2 * 1024 * 1024;
                List list = up.parseRequest(request); // 获取上传列表
                for (Object aList : list) {
                    FileItem fm = (FileItem) aList; // 遍历列表
                    if (!fm.isFormField()) { // 是文件
                        String filePath = fm.getName();  // 获取文件全路径名
                        if (filePath.equals("")) break;
                        if (fm.getSize() > maxsize) {
                            message = "文件太大了，不要超过2MB";
                            break;
                        }
                        String extension = filePath.substring(filePath.lastIndexOf("."));
                        filename = "/resources/cover/" + (bookDao.maxID() + 1) + extension;
                        File saveFile = new File(this.getServletContext().getRealPath(filename));
                        fm.write(saveFile); // 向文件中写入数据
                        message = "文件上传成功！";
                    } else { // 是表单元素
                        String foename = fm.getFieldName(); // 获取表单元素名
                        String con = fm.getString("UTF-8");
                        switch (foename) {
                            case "bookName":
                                bookName = con;
                                break;
                            case "keywords":
                                keywords = con;
                                break;
                            case "bookDescription":
                                bookDescription = con;
                                break;
                        }
                    }
                }
            }
            bookService.addBook(bookName, bookDescription, editor, keywords, filename);
            System.out.println("BookServlet: 添加书目成功");
            // 添加成功后，请求重定向，查看本书
            request.setAttribute("result", message);
            response.sendRedirect("/book.jsp?id=" + bookDao.maxID());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookServlet: 添加书目失败");
        }
    }
}

package servlets;

import dao.BookDao;
import dao.impl.BookDaoImpl;
import model.Book;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUploadException;
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
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        BookDao bookDao = new BookDaoImpl();
        String editor = "";
        String bookName = "";
        String bookDescription = "";
        String keywords = "";
        String message = null;
        String filename = "";
        try {
            if (ServletFileUpload.isMultipartContent(request)) {  //判断获取的是不是文件
                DiskFileItemFactory disk = new DiskFileItemFactory();
                disk.setSizeThreshold(20 * 1024);      //设置内存可存字节数
                disk.setRepository(disk.getRepository());  //设置临时文件目录
                ServletFileUpload up = new ServletFileUpload(disk);
                int maxsize = 2 * 1024 * 1024;
                List list = up.parseRequest(request);   //获取上传列表
                for (Object aList : list) {
                    FileItem fm = (FileItem) aList;//遍历列表
                    if (!fm.isFormField()) {
                        String filePath = fm.getName();  //获取文件全路径名
                        if (fm.getSize() > maxsize) {
                            message = "文件太大了，不要超过2MB";
                            break;
                        }
                        String extension = filePath.substring(filePath.lastIndexOf("."));
                        filename = (bookDao.maxID() + 1) + extension;
                        File saveFile = new File(this.getServletContext().getRealPath("/resources/cover/")
                                + filename);
                        fm.write(saveFile);//向文件中写入数据
                        message = "文件上传成功！";
                    } else {
                        String foename = fm.getFieldName();//获取表单元素名
                        String con = fm.getString("GBK");
                        //表单元素
                        if (foename.equals("bookName")) {
                            bookName = con;
                        } else if (foename.equals("keywords")) {
                            keywords = con;
                        } else if (foename.equals("bookDescription")) {
                            bookDescription = con;
                        } else if (foename.equals("editor")) {
                            editor = con;
                        }
                    }
                }
            }
            bookService.addBook(bookName, bookDescription, editor, keywords, "/resources/cover/" + filename); // TODO cover 的逻辑
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

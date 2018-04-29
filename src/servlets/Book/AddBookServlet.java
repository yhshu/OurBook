package servlets.Book;

import Util.FileUtil;
import dao.BookDao;
import dao.impl.BookDaoImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
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
        HttpSession session = request.getSession();
        BookService bookService = new BookServiceImpl();
        BookDao bookDao = new BookDaoImpl();
        String editor = (String) session.getAttribute("username");
        String nickname = (String) session.getAttribute("nickname");
        String bookName = "";
        String bookDescription = "";
        String keywords = "";
        String filename = null;
        String[] allowedExt = new String[]{"jpg", "jpeg", "gif", "png"}; //图片格式
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
                        boolean isImage = false;
                        for (String ext : allowedExt) {
                            if (fm.getContentType().contains(ext)) {
                                isImage = true;
                                break;
                            }
                        }
                        if (!isImage) {
                            response.sendError(415);
                            return;
                        } else if (fm.getSize() > maxsize) {
                            response.sendError(403, "12");
                            return;
                        }
                        String extension = filePath.substring(filePath.lastIndexOf("."));
                        filename = "/resources/cover/" + (bookDao.maxID() + 1) + extension;
                        File saveFile = new File(this.getServletContext().getRealPath(filename));
                        fm.write(saveFile); // 向文件中写入数据

                        // 如果文件不是图片 立即删除
                        try {
                            Image img = ImageIO.read(saveFile);
                            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                                response.sendError(415);
                                return;
                            }
                        } catch (Exception e) {
                            saveFile.delete();
                            response.sendError(415);
                            return;
                        }
                    } else { // 是表单元素
                        String foename = fm.getFieldName(); // 获取表单元素名
                        String con = fm.getString("UTF-8");
                        switch (foename) {
                            case "bookName":
                                if (con == null || con.trim().equals("") || !FileUtil.isLetterDigitOrChinese(con.trim())) {
                                    response.sendError(400);
                                    return;
                                }
                                bookName = con.trim();
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
            if (bookService.addBook(bookName, bookDescription, editor, nickname, keywords, filename))
                System.out.println("BookServlet: 添加书目成功");
            else {
                System.out.println("BookServlet: 添加书目失败");
                response.sendError(520);
                return;
            }
            // 添加成功后，请求重定向，查看本书
            response.setContentType("text/plain");
            response.getWriter().write("/book?id=" + bookDao.maxID());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookServlet: 添加书目失败");
            response.sendError(520);
        }
    }
}
package servlets.Book;

import Util.FileUtil;
import model.Book;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BookService bookService = new BookServiceImpl();
            // 数据库删除
            int bookID = Integer.parseInt(request.getParameter("bookID"));
            Book book = bookService.find(bookID);
            if (!request.getSession().getAttribute("username").equals(book.getChiefEditor())) {
                throw new Exception("用户不是作者");
            }
            bookService.delete(bookID, (String) request.getSession().getAttribute("username"));
            // 删除文件
            File cover = new File(this.getServletContext().getRealPath("/resources/cover/" + bookID + ".jpg")); // cover 是 jpg 文件
            File bookFolder = new File(this.getServletContext().getRealPath("/resources/book/" + bookID)); // book 是目录
            if (cover.exists() && cover.isFile())
                cover.delete();
            if (bookFolder.exists() && bookFolder.isDirectory()) // TODO 当前无法删除该文件夹
            {
                if (FileUtil.deleteDir(bookFolder))
                    System.out.println("DeleteBookServlet: 删除目录成功 " + bookFolder.getPath());
            }
            // 删除本书后，重定向回首页
            response.sendRedirect("/home");
        } catch (NullPointerException e) {
            response.sendError(404);
        } catch (Exception e) {
            System.out.println("DeleteBookServlet: 删除书目文件失败");
            e.printStackTrace();
            response.sendError(500);
        }
    }
}
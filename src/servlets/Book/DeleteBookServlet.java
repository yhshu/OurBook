package servlets.Book;

import Util.FileUtil;
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
        BookService bookService = new BookServiceImpl();
        int bookID = Integer.parseInt(request.getParameter("bookID"));
        bookService.delete(bookID, (String) request.getSession().getAttribute("username"));
        try {
            File cover = new File(this.getServletContext().getRealPath("/resources/cover/" + bookID + ".jpg")); // cover 是 jpg 文件
            File book = new File(this.getServletContext().getRealPath("/resources/book/" + bookID)); // book 是目录
            if (cover.exists() && cover.isFile())
                cover.delete();
            if (book.existsn'n() && book.isDirectory()) // TODO 当前无法删除该文件夹
                FileUtil.deleteDir(book);
            System.out.println(book.getPath());
        } catch (Exception e) {
            System.out.println("DeleteBookServlet: 删除书目文件失败");
            e.printStackTrace();
        }
        // 删除本书后，重定向回首页
        response.sendRedirect("/home");
    }
}
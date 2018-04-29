package servlets.Book;

import model.Book;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            BookService bookService = new BookServiceImpl();
            // 数据库删除
            int bookID = Integer.parseInt(request.getParameter("book"));
            Book book = bookService.find(bookID);
            String rootDir = this.getServletContext().getRealPath("/");
            if (!session.getAttribute("username").equals(book.getChiefEditor())) {
                // 验证删除本书的是否是主编
                throw new Exception("用户不是作者");
            }
            if (!bookService.delete(bookID, (String) session.getAttribute("username"), rootDir))
                throw new Exception("删除书籍失败");
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
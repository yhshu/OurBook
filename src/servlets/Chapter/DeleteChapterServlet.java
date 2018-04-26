package servlets.Chapter;

import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/deleteChapter")
public class DeleteChapterServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        // 数据库删除章节
        int bookID = Integer.parseInt(request.getParameter("book"));
        int sequence = Integer.parseInt(request.getParameter("sequence"));
        String chapterName = request.getParameter("chapterName");
        bookService.delete_chapter(bookID, sequence);
        // 删除章节文件
        try {
            File chapter = new File(this.getServletContext().getRealPath("/resources/book/" + bookID + "/" + chapterName + ".txt"));
            if (chapter.exists() && chapter.isFile())
                if (!chapter.delete()) throw new Exception();
        } catch (Exception e) {
            System.out.println("DeleteChapterServlet: 删除章节文件失败");
            e.printStackTrace();
        }
        // 删除后，重定向到本书介绍页
        response.sendRedirect("/book?id=" + bookID);
    }
}

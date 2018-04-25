package servlets.Chapter;

import service.BookService;
import service.impl.BookServiceImpl;
import servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ChapterServlet")
public class ChapterServlet extends BaseServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response, "ChapterServlet");
    }

    public void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        String chapterName = request.getParameter("chapterName");
        String chapterContent = request.getParameter("chapterContent");
        // 由 book.jsp 获取 bookID
        int bookID = Integer.parseInt(request.getParameter("bookID"));
        int sequence = 1;
        try {
            String path = this.getServletContext().getRealPath("/resources/book/");
            sequence = bookService.addChapter(chapterName, bookID, chapterContent, path);
            System.out.println("ChapterServlet: 添加章节成功");
            // 添加章节完成后，请求重定向，查看本章节
            response.setContentType("text/plain");
            response.getWriter().write("/read?book=" + bookID + "&sequence=" + sequence);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterServlet: 添加章节失败");
            response.sendError(500);
        }

    }
}

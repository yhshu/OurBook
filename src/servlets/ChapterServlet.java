package servlets;

import model.Chapter;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

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
        int bookID = (int) request.getAttribute("bookID");
        try {
            bookService.addChapter(chapterName, bookID, chapterContent);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterServlet: 添加章节失败");
        }
        // TODO 添加完成后，请求重定向，查看本章节
        response.sendRedirect("/content.jsp");
    }

    public void read(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        try {
            Chapter chapter = bookService.findChapter(Integer.parseInt(request.getParameter("bookID")),
                    Integer.parseInt(request.getParameter("sequence")));
            request.setAttribute("chapter", chapter);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("");
            // TODO dispatch request to jsp
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

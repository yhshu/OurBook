package servlets.Chapter;

import service.BookService;
import service.impl.BookServiceImpl;
import servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ChapterServlet")
public class ChapterServlet extends BaseServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response, "ChapterServlet");
    }

    public void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        BookService bookService = new BookServiceImpl();
        String username = (String) request.getSession().getAttribute("username");
        String nickname = (String) request.getSession().getAttribute("nickname");
        String chapterName = request.getParameter("chapterName");
        String chapterContent = request.getParameter("chapterContent");
        int sequence = Integer.parseInt(request.getParameter("sequence"));
        // 由 book.jsp 获取 bookID
        int bookID = Integer.parseInt(request.getParameter("book"));
        String bookName = bookService.find(bookID).getName();
        if (bookService.authority(bookID, (String) session.getAttribute("username")) > 0) {
            String rootDir = this.getServletContext().getRealPath("/");
            if (bookService.addChapter(username, nickname, chapterName, bookID, chapterContent, rootDir, sequence)) {
                System.out.println("ChapterServlet: 添加章节成功");
                // 添加章节完成后，请求重定向，查看本书目录
                response.setContentType("text/plain");
                response.getWriter().write("/book?id=" + bookID);
                return;
            }
        }
        System.out.println("ChapterServlet: 添加章节失败");
        response.sendError(500);
    }

    public void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        BookService bookService = new BookServiceImpl();
        String username = (String) request.getSession().getAttribute("username");
        String nickname = (String) request.getSession().getAttribute("nickname");
        String chapterName = request.getParameter("chapterName");
        String chapterContent = request.getParameter("chapterContent");
        // 由 book.jsp 获取 bookID
        int bookID = Integer.parseInt(request.getParameter("book"));
        String bookName = bookService.find(bookID).getName();
        int sequence = Integer.parseInt(request.getParameter("sequence"));
        if (bookService.authority(bookID, (String) session.getAttribute("username")) > 0) {
            String rootDir = this.getServletContext().getRealPath("/");
            if (bookService.modifyChapter(username, nickname, chapterName, bookID, chapterContent, rootDir, sequence)) {
                System.out.println("ChapterServlet: 修改章节成功");
                // 添加章节完成后，请求重定向，查看本书目录
                response.setContentType("text/plain");
                response.getWriter().write("/book?id=" + bookID);
                return;
            }
        }
        System.out.println("ChapterServlet: 修改章节失败");
        response.sendError(500);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        BookService bookService = new BookServiceImpl();
        // 数据库删除章节
        int bookID = Integer.parseInt(request.getParameter("book"));
        int sequence = Integer.parseInt(request.getParameter("sequence"));
        String rootDir = this.getServletContext().getRealPath("/");
        if (bookService.authority(bookID, (String) session.getAttribute("username")) > 0) {
            if (bookService.deleteChapter(bookID, sequence, rootDir)) System.out.println("ChapterServlet: 删除章节成功");
            else System.out.println("ChapterServlet: 删除章节失败");
        }
        // 删除后，重定向到本书介绍页
        response.sendRedirect("/book?id=" + bookID);
    }
}

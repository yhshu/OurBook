package servlets.Chapter;

import model.Book;
import model.Edit;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/old")
public class ReadHistoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        BookService bookService = new BookServiceImpl();
        int ID = Integer.parseInt(request.getParameter("id"));
        Edit edit = bookService.getEdit(ID);
        int bookID = edit.getBookID();
        int sequence = edit.getSequence();
        Edit[] edits = bookService.getHistory(bookID, sequence);
        Book book = bookService.find(bookID);
        // 生成 reader
        InputStreamReader isr = new InputStreamReader(new FileInputStream(request.getServletContext().getRealPath(edit.getContent())), "UTF-8");
        BufferedReader read = new BufferedReader(isr);
        boolean isCollaborator = false;
        if (bookService.authority(bookID, (String) session.getAttribute("username")) == 1)
            isCollaborator = true;
        request.setAttribute("isCollaborator", isCollaborator);
        request.setAttribute("edits", edits);
        request.setAttribute("reader", read);
        request.setAttribute("bookID", bookID);
        request.setAttribute("name", edit.getName());
        request.setAttribute("sequence", sequence);
        request.setAttribute("time", edit.getModifiedTime());
        request.setAttribute("chiefEditor", book.getChiefEditor());
        request.setAttribute("editorUsername", edit.getEditorUsername());
        request.setAttribute("editorNickname", edit.getEditorNickname());
        // 重定向
        RequestDispatcher dispatcher = request.getRequestDispatcher("history.jsp");
        dispatcher.forward(request, response);
    }
}

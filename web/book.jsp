<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Book" %>
<%@ page import="service.BookService" %>
<%@ page import="service.impl.BookServiceImpl" %>
<%@ page import="model.Chapter" %>
<%
    BookService bookService = new BookServiceImpl();
    Book book = bookService.find(Integer.parseInt(request.getParameter("id")));
    Chapter[] chapters = bookService.getChapters(book.getID());
%>
<%--
  Created by IntelliJ IDEA.
  User: Radiance
  Date: 4/12/18
  Time: 9:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title><%=book.getName()%> - OurBook</title>
</head>
<body>
<jsp:include page="nav.jsp"/>
<div class="container row" style="margin-top: 50px">
    <h4 style="display: inline; margin-right: 10px;"><%=book.getName()%>
    </h4>
    <h6 style="display: inline; margin-right: 30px;">由 <%=book.getChiefEditor()%> 创建</h6>
    <a href="newChapter.jsp" class="btn blue" style="display: inline;margin-right: 10px">添加章节</a>
    <a href="" class="btn orange" style="display: inline">删除本书</a>
    <div class="collection">
        <%for (Chapter chapter : chapters) {%>
        <div>
            <a href="${pageContext.request.contextPath}/content.jsp?book=
<%=chapter.getBookID()%>&sequence=<%=chapter.getSequence()%>" class="collection-item black-text"><%=chapter.getName()%>
            </a></div>
        <%}%>
    </div>
</div>
</body>
</html>

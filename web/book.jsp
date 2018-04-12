<%@ page import="model.Book" %>
<%@ page import="service.BookService" %>
<%@ page import="service.impl.BookServiceImpl" %>
<%@ page import="model.Chapter" %>
<%BookService bookService = new BookServiceImpl();%>
<%Book book = bookService.find(Integer.parseInt(request.getParameter("id")));%>
<%Chapter[] chapters = bookService.getChapters(book.getID());%>
<%--
  Created by IntelliJ IDEA.
  User: Radiance
  Date: 4/12/18
  Time: 9:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title><%=book.getName()%> - OurBook</title>
</head>
<body>
<jsp:include page="nav.jsp"/>
<%for (Chapter chapter : chapters) {%>
<div><a><%=chapter.getName()%>
</a></div>
<%}%>
</body>
</html>

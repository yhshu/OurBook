<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Book" %>
<%@ page import="model.Chapter" %>
<%@ page import="model.Article" %>
<%@ page import="service.BookService" %>
<%@ page import="service.impl.BookServiceImpl" %>
<%
    BookService bookService = new BookServiceImpl();
    String keywords = request.getParameter("keywords");
    String searchType = request.getParameter("search_type");
%>
<%--
  Created by IntelliJ IDEA.
  User: Radiance
  Date: 4/11/18
  Time: 3:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title><%=request.getParameter("keywords")%> - 搜索结果 - OurBook</title>
</head>
<body>
<jsp:include page="nav.jsp"/>
<div class="container row" style="margin-top: 100px">
    <%
        if (searchType == null || searchType.equals("book")) {
            for (Book book : bookService.findByKeywords(keywords)) {
    %>
    <div><a href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>"><%=book.getName()%>
    </a>
    </div>
    <%
            }
        }%>
    <div style="height: 10000px; width: 100px"></div>
</div>
</body>
</html>

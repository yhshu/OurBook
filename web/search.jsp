<%@ page import="model.Book" %>
<%@ page import="model.Chapter" %><%--
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
<%
    switch (request.getParameter("type")) {
        case "book":
            for (Book book : (Book[]) request.getAttribute("books")) {
%>
<div>a</div>
<% }
    break;
    case "chapter":
        for (Chapter chapter : (Chapter[]) request.getAttribute("chapters")) {%>
<div></div>
<% }
    break;
}%>
</body>
</html>
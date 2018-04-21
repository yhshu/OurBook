<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Book" %>
<%@ page import="model.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Radiance
  Date: 4/11/18
  Time: 3:15 PM
  To change this template use File | Settings | File Templates.
--%>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title><%=request.getAttribute("keywords")%> - 搜索结果 - OurBook</title>
</head>
<body>
<jsp:include page="nav.jsp"/>
<div class="container row" style="margin-top: 100px">
    <%
        String type = (String) request.getAttribute("type");
        User[] editors = (User[]) request.getAttribute("editors");
        if (type == null || type.equals("book")) {
            int i = 0;
            Book[] books = (Book[]) request.getAttribute("books");
            if (books.length == 0) {%>
    <h4 class="grey-text" style="text-align: center;margin-top:250px">
        未找到含有关键字<%=" " + request.getAttribute("keywords") + " "%>的书籍</h4>
    <%
        }
        for (Book book : books) {
    %>
    <div style="margin: 20px auto;display: grid;grid-template-columns: 192px auto;width: 800px" class="card">
        <%if (book.getCover() == null || book.getCover().equals("")) {%>
        <a href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>" style="border-radius: 2px 0 0 2px">
            <div style="width: 192px;height: 256px;background-color: #0D47A1; border-radius: 2px 0 0 2px">
                <h4 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                    <%=book.getName()%>
                </h4>
            </div>
        </a>
        <%} else {%>
        <a href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
            <img style="width: 192px;height: 256px;border-radius: 2px 0 0 2px;object-fit: cover"
                 src="<%=book.getCover()%>">
        </a>
        <%}%>
        <div style="display: grid;grid-template-rows: 66px 40px 1px 130px">
            <h5 style="margin: 25px 0 0 25px">
                <a style="color: black" href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
                    <%=book.getName()%>
                </a>
            </h5>
            <p style="color: gray;margin: 0 0 0 25px"><%=editors[i].getNickname()%>
            </p>
            <hr style="width: 100%;margin: 0;border-top: 1px gray"/>
            <p style="margin: 25px 0 0 25px">
                <%=book.getDescription()%>
            </p>
        </div>
        <a class="btn-large btn-floating halfway-fab waves-effect waves-light red"
           style="position: relative;margin-bottom: -100px;left:772px;bottom:176px">
            <i class="material-icons">add</i>
        </a>
    </div>
    <%
                i++;
            }
        }%>
</div>
</body>
</html>

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

    <div style="margin: 20px auto;display: grid;grid-template-columns: 210px auto;border-radius: 2px;
    box-shadow: 0 2px 2px 0 rgba(0,0,0,0.14), 0 3px 1px -2px rgba(0,0,0,0.12), 0 1px 5px 0 rgba(0,0,0,0.2);">

        <div style="width: 210px;height: 257px;background-color: #0D47A1">
            <h4 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                <%=book.getName()%>
            </h4>
        </div>

        <div style="display: grid;grid-template-rows: 66px 40px 1px 130px">
            <h5 style="margin: 25px 0 0 25px">
                <a style="color: black" href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
                    <%=book.getName()%>
                </a>
            </h5>
            <p style="color: gray;margin: 0 0 0 25px"><%=book.getChiefEditor()%>
            </p>
            <hr style="width: 100%;margin: 0;border-top: 1px gray"/>
            <p style="margin: 25px 0 0 25px">
                <%=book.getDescription()%>
            </p>
        </div>
    </div>

    <!-- TODO 点击本书作者用户名跳转到其主页-->

    <h6 style="display: inline; margin-right: 30px;">章节目录</h6>
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

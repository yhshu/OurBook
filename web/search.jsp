<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Book" %>
<%@ page import="model.Chapter" %>
<%@ page import="model.User" %>
<%@ page import="service.BookService" %>
<%@ page import="service.impl.BookServiceImpl" %>
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
        BookService bookService= new BookServiceImpl();
        String type = (String) request.getAttribute("type"); // 获取搜索类型
        if (type == null || type.equals("book")) { // 如果搜索书籍
            User[] editors = (User[]) request.getAttribute("editors");
            int i = 0;
            Book[] books = (Book[]) request.getAttribute("books");
            if (books.length == 0) {%>
    <h4 class="grey-text" style="text-align: center;margin-top:250px">
        未找到含有关键字<%=" " + request.getAttribute("keywords") + " "%>的书籍</h4>
        <%
    } else for (Book book : books) {
    %>
    <div style="margin: 20px auto;display: grid;grid-template-columns: 192px auto;width: 800px" class="card">
        <%if (book.getCover() == null || book.getCover().equals("")) {%>
        <a href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>" style="border-radius: 2px 0 0 2px">
            <div style="width: 192px;height: 256px;background-color: #0D47A1; border-radius: 2px 0 0 2px">
                <h4 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                    <%=book.getName()%>
                </h4>
            </div>
        </a>
        <%} else {%>
        <a href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>">
            <img style="width: 192px;height: 256px;border-radius: 2px 0 0 2px;object-fit: cover"
                 src="<%=book.getCover()%>">
        </a>
        <%}%>
        <div style="display: grid;grid-template-rows: 66px 40px 1px 130px">
            <div>
                <h5 style="margin: 25px 0 0 25px;display: inline-block">
                    <a style="color: black" href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>">
                        <%=book.getName()%>
                    </a>
                </h5>
            </div>
            <a href="${pageContext.request.contextPath}/home?user=<%=book.getChiefEditor()%>"
               style="color: gray;margin: 0 0 0 25px"><%=editors[i].getNickname()%>
            </a>
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
    }else if(type.equals("article")) // 如果搜索文章
        {
            Chapter[] chapters = (Chapter[]) request.getAttribute("chapters");
            if(chapters.length == 0){
                %>
    <h4 class="grey-text" style="text-align: center;margin-top:250px">
        未找到含有关键字<%=" " + request.getAttribute("keywords") + " "%>的文章</h4>
        <%}else for(Chapter chapter:chapters) {
                Book book = bookService.find(chapter.getBookID());
%>
    <div class="row card" style="padding: 1px 20px 7px;">
        <a href="${pageContext.request.contextPath}/read?book=<%=chapter.getBookID()%>&sequence=<%=chapter.getSequence()%>"
           class="black-text">
            <h5><%=chapter.getName()%>
            </h5>
        </a>
        <a class="grey-text" href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>"><h6><%=book.getName()%>
        </h6>
        </a>
    </div>
        <%}
        }
    else if (type.equals("user")) { // 如果搜索用户
        User[] users = (User[]) request.getAttribute("users");
        if (users.length == 0) {%>
    <h4 class="grey-text" style="text-align: center;margin-top:250px">
        未找到含有关键字<%=" " + request.getAttribute("keywords") + " "%>的用户</h4>
        <%} else for (User user : users) {%>
    <div class="row card">
        <img src="<%=user.getAvatar()%>"
             style="width:80px;height: 80px;border-radius: 5%;float: left;object-fit: cover;margin-right: 20px">
        <div style="float:left;width:700px; margin:10px;">
            <!--用户的用户名与昵称-->
            <h6 style="margin:0;float: left"><a href=""><%=user.getNickname()%>
            </a>
            </h6>
            <h6 class="grey-text" style="margin: 0 10px;float: left"><%=user.getUsername()%>
            </h6>
        </div>
        <h6 style="float: left;margin-left: 10px;margin-top: 5px;"><!--用户的一句话描述--><%
            String description = user.getDescription();
            if (description != null) {
        %>
            <%=description%>
            <%
                }
            %>
        </h6>
    </div>
        <%
                    }
            }
        %>
</body>
</html>

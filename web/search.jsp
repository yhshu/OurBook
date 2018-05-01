<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Book" %>
<%@ page import="model.Chapter" %>
<%@ page import="model.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
    <%SimpleDateFormat sdf = new SimpleDateFormat("yyyy.M.d");%>
    <title><%=request.getAttribute("keywords")%> - 搜索结果 - OurBook</title>
</head>
<body>
<jsp:include page="nav.jsp"/>
<main>
    <div class="container row" style="margin-top: 140px">
        <%
            String type = (String) request.getAttribute("type"); // 获取搜索类型
            if (type == null || type.equals("book")) { // 如果搜索书籍
                Book[] books = (Book[]) request.getAttribute("books");
                Book[] favorites = (Book[]) request.getAttribute("favorites");
                String keywords = request.getParameter("keywords");
                String strNumber = request.getParameter("page");
                String sort = request.getParameter("sort");
                if (sort == null) sort = "last_updated";
                String range = request.getParameter("range");
                if (range == null) range = "all";
                int count = 5;//每页显示的条数 
                int maxPage = (int) Math.ceil((double) books.length / count);//最大页数
                int number;//当前页码
                try {
                    number = Integer.parseInt(strNumber);
                } catch (Exception e) {
                    number = 1;
                }
                if (number < 1) number = 1;
                if (books.length == 0) {%>
        <h4 class="grey-text" style="text-align: center;margin-top:250px">
            未找到含有关键字<%=" \"" + request.getAttribute("keywords") + "\" "%>的书籍</h4>
        <%
        } else {
            int start = (number - 1) * count;//开始记录数
            int end = number * count;//结束记录数
            if (end > books.length - 1) {
                end = books.length;//防止越界
            }
        %>
        <p class="grey-text" style="text-align: center;">
            共找到<%=" " + books.length + " " %>本相关书籍&nbsp;
        </p>
        <%
            for (int m = start; m < end; m++) {
                boolean favorite = false;
                for (Book book1 : favorites)
                    if (book1.getID() == books[m].getID()) {
                        favorite = true;
                        break;
                    }
        %>
        <div style="margin: 20px auto;display: grid;grid-template-columns: 192px auto;width: 800px" class="card">
            <%if (books[m].getCover() == null || books[m].getCover().equals("")) {%>
            <a href="${pageContext.request.contextPath}/book?id=<%=books[m].getID()%>"
               style="border-radius: 2px 0 0 2px">
                <div style="width: 192px;height: 256px;float:left;background-color: #0D47A1; border-radius: 2px 0 0 2px">
                    <h4 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                        <%=books[m].getName()%>
                    </h4>
                </div>
            </a>
            <%} else {%>
            <a href="${pageContext.request.contextPath}/book?id=<%=books[m].getID()%>">
                <img style="width: 192px;height: 256px;border-radius: 2px 0 0 2px;object-fit: cover"
                     src="<%=books[m].getCover()%>">
            </a>
            <%}%>
            <div style="display: grid;grid-template-rows: 66px 40px 1px auto">
                <div>
                    <h5 style="margin: 25px 0 0 25px;display: inline-block">
                        <a style="color: black" href="${pageContext.request.contextPath}/book?id=<%=books[m].getID()%>">
                            <%=books[m].getName()%>
                        </a>
                    </h5>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/home?user=<%=books[m].getChiefEditor()%>"
                       style="color: gray;margin: 0 0 0 25px;float: left"><%=books[m].getChiefEditorNickname()%>
                    </a>
                    <p class="grey-text" style="margin: 0 25px; float: left;">
                        <i class="material-icons">remove_red_eye </i> <%=books[m].getClicks()%>
                        <i class="material-icons" style="margin-left: 10px">favorite </i> <%=books[m].getFavorites()%>
                    </p>
                    <p style="color: gray;margin: 0;float: left">
                        最后更新： <%=books[m].getLastModified() != null ? sdf.format(books[m].getLastModified()) : "暂无"%>
                    </p>
                </div>
                <hr style="width: 100%;margin: 0;border-top: 1px gray"/>
                <p style="margin: 25px;overflow: auto">
                    <%=books[m].getDescription()%>
                </p>
            </div>
            <a href="#"
               class="btn-large btn-floating halfway-fab waves-effect waves-light pink favorite_submit"
               style="position: relative;margin-bottom: -100px;left:772px;bottom:176px"
               data-method="<%=favorite ? "remove" : "add"%>" data-book="<%=books[m].getID()%>">
                <i class="material-icons" id="book_icon_<%=books[m].getID()%>"
                   style="margin-top:3px"><%=favorite ? "favorite" : "favorite_border"%>
                </i>
            </a>

        </div>
        <%
            }
        %>
        <div style="text-align: center;margin-top: 20px">
            <ul class="pagination">
                <li class="<%=number!=1?"":"disabled"%>">
                    <a href="search?keywords=<%=keywords%>&type=<%=type%>&sort=<%=sort%>&range=<%=range%>&page=<%=number-1%>"><i
                            class="material-icons">chevron_left</i></a>
                </li>
                <%for (int i = 1; i <= maxPage; i++) {%>
                <li class="<%=i==number?"active":"waves-effect"%>">
                    <a href="search?keywords=<%=keywords%>&type=<%=type%>&sort=<%=sort%>&range=<%=range%>&page=<%=i%>"><%=i%>
                    </a></li>
                <%}%>
                <li class="<%=number!=maxPage?"":"disabled"%>">
                    <a href="search?keywords=<%=keywords%>&type=<%=type%>&sort=<%=sort%>&range=<%=range%>&page=<%=number+1%>"><i
                            class="material-icons">chevron_right</i></a>
                </li>
            </ul>
        </div>
        <%
            }
        } else if (type.equals("article")) // 如果搜索文章
        {
            Chapter[] chapters = (Chapter[]) request.getAttribute("chapters");
            if (chapters.length == 0) {
        %>
        <h4 class="grey-text" style="text-align: center;margin-top:250px">
            未找到含有关键字<%=" \"" + request.getAttribute("keywords") + "\" "%>的文章</h4>
        <%} else {%>
        <div style="display: grid;grid-template-columns: 480px 480px">
            <%for (Chapter chapter : chapters) {%>
            <div class="row card" style="width: 450px;height:96px;margin:10px auto;display: grid;
        grid-template-columns: 72px 10px auto">
                <a href="${pageContext.request.contextPath}/book?id=<%=chapter.getBookID()%>">
                    <%
                        if (chapter.getBookCover() == null || chapter.getBookCover().equals("")) {
                    %>
                    <div style="width: 72px;height: 96px;background-color: #0D47A1;border-radius: 2px 0 0 2px;float: left">
                        <p style="color: white;display: block;position: relative;top: 20%;text-align: center">
                            <%=chapter.getBookName()%>
                        </p>
                    </div>
                    <%} else {%>
                    <img src="<%=chapter.getBookCover()%>"
                         style="border-radius: 2px 0 0 2px;width:72px;height: 96px;float:left;object-fit: cover">
                    <%}%>
                </a>
                <div></div>
                <div style="display: grid; grid-template-rows: 65px auto">
                    <a style="margin: 5px 10px" class="black-text"
                       href="${pageContext.request.contextPath}/read?book=<%=chapter.getBookID()%>&sequence=<%=chapter.getSequence()%>">
                        <h6 style="line-height: 23px;margin-top: 10px"><%=chapter.getBookName()%>&nbsp;&nbsp;&nbsp;&nbsp;<%=chapter.getName()%>
                        </h6>
                    </a>
                    <a href="${pageContext.request.contextPath}/home?user=<%=chapter.getEditorUsername()%>">
                        <p style="float: left;margin:0 10px 0 10px"><%=chapter.getEditorNickname()%>
                        </p>
                        <p class="grey-text" style="float: left;margin:0 20px 0 0">@<%=chapter.getEditorUsername()%>
                        </p>
                        <p class="grey-text" style="float: left;margin:0">最后更新：
                            <%=chapter.getLastModified() != null ? chapter.getLastModified() : "暂无"%>
                        </p>
                    </a>
                </div>
            </div>
            <%}%></div>
        <%
            }
        } else if (type.equals("user")) { // 如果搜索用户
            User[] users = (User[]) request.getAttribute("users");
            if (users.length == 0) {
        %>
        <h4 class="grey-text" style="text-align: center;margin-top:250px">
            未找到含有关键字<%=" \"" + request.getAttribute("keywords") + "\" "%>的用户</h4>
        <%} else {%>
        <div style="display: grid;grid-template-columns: 480px 480px;width:960px;margin:auto">
            <%for (User user : users) {%>
            <div class="row card" style="width: 450px;height:96px;margin:10px auto;display: grid;
        grid-template-columns: 96px 10px auto">
                <a href="home?user=<%=user.getUsername()%>">
                    <img src="<%=user.getAvatar()%>" style="width:96px;height: 96px;border-radius: 5%;
            float: left;object-fit: cover;margin-right: 20px">
                </a>
                <div></div>
                <div>
                    <div style="padding:10px;height:40px">
                        <!--用户的用户名与昵称-->
                        <h6 style="margin:0;float: left">
                            <a href="home?user=<%=user.getUsername()%>">
                                <%=user.getNickname()%>
                            </a>
                        </h6>
                        <h6 class="grey-text" style="margin: 0 10px;float: left">@<%=user.getUsername()%>
                        </h6>
                    </div>
                    <p class="grey-text" style="margin: 0 10px">
                        <i class="material-icons">remove_red_eye </i> <%=user.getClicks()%>
                        <i class="material-icons" style="margin-left: 10px">favorite </i> <%=user.getFavorites()%>
                    </p>
                    <p style="float: left;margin-left: 10px;margin-top: 5px;"><!--用户的一句话描述--><%
                        String description = user.getDescription();
                        if (description != null) {
                    %>
                        <%=description%>
                        <%
                            }
                        %>
                    </p>
                </div>
            </div>
            <% }%></div>
        <%
                }
            }
        %>
    </div>
</main>
<script>
    $(document).ready(function () {
        <%if(request.getAttribute("keywords")!=null){%>
        document.getElementById("search").value = "<%=request.getAttribute("keywords")%>";
        <%}%>
        $('.disabled a').removeAttr('href');
    });

    $('.favorite_submit').click(function (event) {
        event.preventDefault();
        var submit_book = $(this);
        var icon_book = $('#book_icon_' + submit_book.data("book"));
        $.get('${pageContext.request.contextPath}/favorite', {
                method: submit_book.data("method"),
                book: submit_book.data("book")
            },
            function () {
                if (submit_book.data("method") === "remove") {
                    toast('取消收藏成功');
                    icon_book.html("favorite_border");
                    submit_book.data("method", "add");
                    icon_book.attr("data-tooltip", '收藏');
                    icon_book.tooltip();
                }
                else if (submit_book.data("method") === "add") {
                    toast('收藏成功');
                    icon_book.html("favorite");
                    submit_book.data("method", "remove");
                    icon_book.attr("data-tooltip", '取消收藏');
                    icon_book.tooltip();
                }
            }
        ).fail(function () { // 服务器响应错误信息
            toast("操作异常");
        })
    });
</script>
<%@ include file="footer.html" %>
</body>
</html>

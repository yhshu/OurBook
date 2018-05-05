<%@ page import="model.Book" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title>首页 - OurBook</title>
    <style>
        .nav-item {
            display: inline-block;
            margin: 10px;
            font-size: 16px;
            color: #9e9e9e !important;
        }
    </style>
</head>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("/register");
    } else {
        User[] users = (User[]) request.getAttribute("user_recommend");
        Book[] books = (Book[]) request.getAttribute("book_recommend");
        String visit = (String) request.getAttribute("visit");
        if (users == null && books == null && visit == null) response.sendRedirect("/index");
%>
<body>
<jsp:include page="nav.jsp"/>
<main>
    <div class="container row" style="margin-top: 20px; margin-bottom: 0; width: 1000px">
        <div class="card" style="padding:  1px 10px">

            <a href="${pageContext.request.contextPath}/home" class="nav-item waves-effect">
                <i class="material-icons">home</i>个人主页</a>
            <a href="${pageContext.request.contextPath}/create" class="nav-item waves-effect">
                <i class="material-icons">mode_edit</i>创作新书</a>
            <a href="${pageContext.request.contextPath}/notifications" class="nav-item waves-effect">
                <i class="material-icons"><%=((int) session.getAttribute("unreadNotifications") > 0) ? "notifications_active" : "notifications_none"%>
                </i>通知中心
                <%if ((int) session.getAttribute("unreadNotifications") > 0) {%>
                <span class="new badge right" style="margin-top:2px"><%=session.getAttribute("unreadNotifications")%>
                </span><%}%></a>
            <a href="${pageContext.request.contextPath}/notifications#area3" class="nav-item waves-effect">
                <i class="material-icons">chat</i>我的私信
                <%if ((int) session.getAttribute("unreadMessages") > 0) {%>
                <span class="new badge right" style="margin-top:2px"><%=session.getAttribute("unreadMessages")%>
                </span><%}%></a>
        </div>

    </div>
    <div class="container row" style="width: 1000px"><!-- 页面中部 -->

        <div class="col card" style="width: 713px; display: inline;"><!--热门书籍-->
            <h5 class="center-align">热门书籍</h5>
            <%
                if (books != null) {
            %>
            <div style="display: grid;grid-template-columns: 50% 50%"><%
                for (Book book : books) {
            %>
                <div style="margin: 15px auto;display: grid;grid-template-columns: 96px 249px;height: 128px;overflow: hidden">
                    <a href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>">
                        <%
                            if (book.getCover() == null || book.getCover().equals("")) {
                                // 如果无封面
                        %>
                        <div style="width: 96px;height: 128px;background-color: #0D47A1;border-radius: 2px 0 0 2px">
                            <h6 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                                <%=book.getName()%>
                            </h6>
                        </div>
                        <%} else { // 如果有封面%>
                        <img style="width: 96px;height: 128px;object-fit: cover"
                             src="<%=book.getCover()%>">
                        <%}%>
                    </a>
                    <div style="display: grid;grid-template-rows: 32px 18px auto">
                        <div style="margin: 5px 0 0 15px"><!--书名-->
                            <p style="float: left;margin: 0">
                                <a style="color: black"
                                   href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>"><%=book.getName()%>
                                </a>
                            </p>
                        </div>
                        <div><!--作者-->
                            <a href="${pageContext.request.contextPath}/home?user=<%=book.getChiefEditor()%>"
                               style="color: gray;margin: 0 0 0 15px; display: inline-block;">
                                <%=book.getChiefEditorNickname()%>
                            </a>
                            <p class="grey-text" style="margin: 0 15px; display: inline-block">
                                <i class="material-icons">remove_red_eye </i> <%=book.getClicks()%>
                                <i class="material-icons"
                                   style="margin-left: 10px">favorite </i> <%=book.getFavorites()%>
                            </p>
                        </div>
                        <p style="margin: 15px;font-size: 14px;text-overflow: ellipsis;height: 48px"><!--简介-->
                            <%=book.getDescription()%>
                        </p>
                    </div>
                </div>
                <%
                    }%></div>
            <%
                }
            %>
        </div>

        <div class="col card" style="width: 262px; margin-left: 25px;"><!--推荐作者-->
            <h5 class="center-align">推荐作者</h5>
            <%
                if (users != null) {
                    for (User user : users) {%>
            <div class="row" style="margin: 25px 5px 10px;">
                <a href="home?user=<%=user.getUsername()%>"><!--用户头像-->
                    <img src="<%=user.getAvatar()%>"
                         style="width:40px;height: 40px;border-radius: 5%;float: left;margin-right:10px">
                </a>
                <div>
                    <!--用户名与昵称-->
                    <a href="home?user=<%=user.getUsername()%>"><p style="margin:0;float: left">
                        <%=user.getNickname()%>
                    </p></a>
                    <p class="grey-text" style="margin: 0;float: right">@<%=user.getUsername()%>
                    </p>
                    <BR>
                    <p class="grey-text" style="margin: 0;float: left">
                        <i class="material-icons">perm_identity</i> <%=user.getFollowers()%>
                    </p>
                </div>
            </div>
            <%
                        }
                    }
                }
            %>
        </div>
        <span style="font-size:20px;margin-left: 25px;">为您推荐</span>
        <a href="https://www.amazon.cn/dp/B00QJDOLIO/459-3755434-3588139?_encoding=UTF8&pf_rd_i=desktop&pf_rd_m=A1AJ19PSB66TGU&pf_rd_p=ce9fc27b-a4fd-4da7-bb9a-95ad1b6617a0&pf_rd_r=ZHRCQCYMBYSFT98K4WE5&pf_rd_s=Tcg-slide-1&pf_rd_t=36701&ref_=p-Tcg-slide-1--2efc1934-96f8-455f-bc30-b0debca468f9"
           target="_blank">
            <img src="img/ads/kindle.png" style=" width: 262px; margin-left: 25px"/>
        </a>
    </div>
</main>
<%@ include file="footer.html" %>
</body>
</html>
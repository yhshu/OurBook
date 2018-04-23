<%@ page import="model.Book" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title>首页 - OurBook</title>
</head>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("/login.jsp");
    }
    User[] users = (User[]) request.getAttribute("user_recommend");
    Book[] books = (Book[]) request.getAttribute("book_recommend");
%>
<body>
<%@ include file="nav.jsp" %>
<div class="container row" style="margin-top: 20px"><!-- 页面中部 -->
    <div class="col card" style="width: 623px;"><!--热门书籍-->
        <h5 class="center-align">热门书籍</h5>


    </div>

    <div class="col card" style="width: 262px; margin-left: 25px;"><!--推荐作者-->
        <h5 class="center-align">推荐作者</h5>
        <%
            if (users != null) {
                for (User user : users) {%>
        <div class="row" style="margin: 25px 5px;">
            <a href="home?user=<%=user.getUsername()%>"><!--用户头像-->
                <img src="<%=user.getAvatar()%>" style="width:40px;height: 40px;border-radius: 5%;
            float: left;object-fit: cover;margin-right: 5px">
            </a>
            <div style="float:left;">
                <!--用户名与昵称-->
                <h6 style="margin:0;float: left">
                    <a href="home?user=<%=user.getUsername()%>">
                        <%=user.getNickname()%>
                    </a>
                </h6>
                <h6 class="grey-text" style="margin: 0 10px;float: left">@<%=user.getUsername()%>
                </h6>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
</div>

</body>
</html>
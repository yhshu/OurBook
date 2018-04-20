<%@ page import="model.Book" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <%
        Book[] books = (Book[]) request.getAttribute("books");
        String[] followees = (String[]) request.getAttribute("followees");
    %>
    <title>个人主页 - OurBook</title>
</head>

<body class="grey lighten-4">
<%@ include file="nav.jsp" %>
<div class="container">
    <div class="row">
        <h4 id="homepage_username"><%// TODO %>
        </h4><!--用户的用户名与昵称-->
        <h6 id="homepage_userDescription"><%// TODO %>
        </h6><!--用户的一句话描述-->
        <a class="modal-trigger" data-target="personalInfo" style="display: inline;"><i
                class="material-icons">settings</i></a>
        <!--TODO 修改个人信息 模态框 的后端逻辑-->
        <div id="personalInfo" class="modal" style="min-width:300px"> <!--修改个人信息 模态框-->
            <form action="${pageContext.request.contextPath}/modifyUser" method="post" enctype="multipart/form-data">
                <input type="hidden" name="username" id="username" value="<%=session.getAttribute("username")%>">
                <div class="modal-content">
                    <h4>修改个人信息</h4>
                    <label for="new_nickname">昵称</label>
                    <input type="text" name="new_nickname" id="new_nickname"/>
                    <label for="new_description">一句话简介</label>
                    <input type="text" name="new_description" id="new_description"/>
                    <input id="avatar" type='file' name="avatar" onchange="readURL(this);" style="display: none"/>
                    <div style="margin: 20px auto; width: 300px;height: 128px">
                        <img id="preview" src="<%=session.getAttribute("avatar")%>" alt="your image"
                             style="height: 128px;width:128px;background-color: #f6f6f6;
                             border-radius: 50%;float:left"/>
                        <label for="avatar" class="blue btn" style="float: right;margin-top: 46px">上传头像</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">取消
                    </a>
                    <button class="modal-action modal-close waves-effect waves-green btn-flat" id="submit_personal_info"
                            onclick="document.getElementById('personalInfo').submit();">
                        提交
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col card"> <!--文章、书目或动态-->
            <h5 style="text-align: center">我写的书</h5>
            <div style="margin-top: 20px"> <!-- 我写的书 目录-->
                <%
                    if (books.length == 0) {%>
                <h6 class="grey-text" style="text-align: center; margin-top: 100px;width: 600px"> 你还没有写任何书 </h6>
                <%
                    }
                    for (Book book : books) {
                %>
                <div style="padding: 10px;margin-bottom:10px;display: grid;grid-template-columns: 90px auto;width: 600px;
border-bottom: 1px solid lightgray">
                    <%if (book.getCover() == null || book.getCover().equals("")) {%>
                    <a href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
                        <div style="width: 90px;height: 120px;background-color: #0D47A1">
                            <h6 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                                <%=book.getName()%>
                            </h6>
                        </div>
                    </a>
                    <%} else {%>
                    <a href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
                        <img style="width: 90px;height: 120px"
                             src="<%=book.getCover()%>">
                    </a>
                    <%}%>
                    <div style="display: grid;grid-template-rows: 44px 24px 52px">
                        <a style="color: black;margin: 8px 24px;font-size: 16px"
                           href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
                            <%=book.getName()%>
                        </a>
                    </div>
                </div>
                <%}%>
            </div>
        </div>

        <div class="col s2">
            <!--不要随便删除它，除非需要改变排版-->
        </div>

        <form action="${pageContext.request.contextPath}/FollowServlet" accept-charset="UTF-8" method="post"
              id="delfollowee">
            <div class="col card"> <!--关注列表-->
                <h5 style="text-align: center">我的关注</h5>
                <div style="margin-top: 20px">
                    <% if (followees.length == 0) {%>
                    <h6 style="text-align: center;margin-top: 100px;width: 200px" class="grey-text">你还没有关注任何人</h6>
                    <%
                        }
                        for (String user : followees) {
                    %>
                    <div style="margin: auto;width: 200px">
                        <input type="hidden" name="method" value="delfollowee">
                        <input type="hidden" name="followee" value="<%=session.getAttribute("username")%>">
                        <input type="hidden" name="follower" value="<%=user%>">

                        <a href="home?user=<%=user%>" style="text-align: center">
                            <%=user%>
                        </a>
                        <a type="submit" class="btn blue"
                           class="btn blue"
                           style="margin-right: 10px; display: inline; -webkit-appearance:none; -moz-appearance:none;"
                           onclick="document .getElementById ('delfollowee').submit();">取消关注</a>
                    </div>
                    <%}%>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    $(document).ready(function () {
        // 通过 cookie 修改 username_display
        var nickname = getCookie('nickname');
        var username = getCookie('username');
        if (nickname !== "" && username !== "")
            document.getElementById("homepage_username").innerText = nickname + " (" + username + ")";

        $('.modal').modal(); // 模态框
        $("#username").val(username); // 设置表单中的input
    });
</script>
</body>
</html>

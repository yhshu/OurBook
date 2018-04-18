<%@ page import="model.Book" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>个人主页 - OurBook</title>
</head>

<body>
<%@ include file="nav.jsp" %>
<div class="container">
    <div class="row">
        <h4 id="homepage_username"></h4><!--用户的用户名与昵称-->
        <h6 id="homepage_userDescription"></h6><!--用户的一句话描述-->
        <a class="modal-trigger" data-target="personalInfo" style="display: inline;"><i
                class="material-icons">settings</i></a>
        <!--TODO 修改个人信息 模态框 的后端逻辑-->
        <div id="personalInfo" class="modal"> <!--修改个人信息 模态框-->
            <div class="modal-content">
                <h4>修改个人信息</h4>
                <label for="new_nickname">昵称</label>
                <input type="text" name="new_nickname" id="new_nickname"/>
                <label for="new_description">一句话简介</label>
                <input type="text" name="new_description" id="new_description"/>
            </div>
            <div class="modal-footer">
                <button class="modal-action modal-close waves-effect waves-green btn-flat">取消
                </button>
                <button class="modal-action modal-close waves-effect waves-green btn-flat" id="submit_personal_info">
                    提交
                </button>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col s6"> <!--文章、书目或动态-->
            <h5 style=" margin-right: 30px;">我写的书</h5>
            <div> <!-- 我写的书 目录-->
                <%
                    for (Book book : (Book[]) request.getAttribute("books")) {
                %>
                <div style="margin: 20px auto;display: grid;grid-template-columns: 210px auto;border-radius: 2px;width: 800px;
    box-shadow: 0 2px 2px 0 rgba(0,0,0,0.14), 0 3px 1px -2px rgba(0,0,0,0.12), 0 1px 5px 0 rgba(0,0,0,0.2);">
                    <%if (book.getCover() == null || book.getCover().equals("")) {%>
                    <a href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>"
                       style="border-radius: 2px 0 0 2px">
                        <div style="width: 210px;height: 257px;background-color: #0D47A1; border-radius: 2px 0 0 2px">
                            <h4 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                                <%=book.getName()%>
                            </h4>
                        </div>
                    </a>
                    <%} else {%>
                    <a href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
                        <img style="width: 210px;height: 257px;border-radius: 2px 0 0 2px"
                             src="<%=book.getCover()%>">
                    </a>
                    <%}%>
                    <div style="display: grid;grid-template-rows: 66px 40px 1px 130px">
                        <h5 style="margin: 25px 0 0 25px">
                            <a style="color: black"
                               href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
                                <%=book.getName()%>
                            </a>
                        </h5>
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
                <%}%>
            </div>
        </div>

        <div class="col s2"> <!--关注列表-->
            <h5>关注列表</h5>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        // 通过 cookie 修改 username_display
        var nickname = getCookie('nickname');
        var username = getCookie('username');
        if (nickname !== "" && username !== "")
            document.getElementById("homepage_username").innerText = nickname + " (" + username + ")";

        // 模态框
        $(document).ready(function () {
            $('.modal').modal();
        });
    });
</script>
</body>
</html>

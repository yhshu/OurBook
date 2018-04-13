<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>个人主页 - OurBook</title>
</head>

<body>
<%@ include file="nav.jsp" %>
<div class="container">
    <div class="row"> <!--个人信息-->
        <h4 id="homepage_username"></h4>
        <h6 id="homepage_userDescription"></h6>
        <!--TODO 修改个人信息 模态框-->
    </div>

    <div class="row">
        <div class="col s6"> <!--文章、书目或动态-->
            <h5 style=" margin-right: 30px;">我写的书</h5>
            <div> <!-- 我写的书 目录-->
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
    });
</script>
</body>
</html>

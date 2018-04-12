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
    </div>

    <div class="row">
        <div class="col"> <!--文章、书目或动态-->
            <h5 style="display: inline; margin-right: 30px;">我写的书</h5>
            <a id="newbook" class="btn blue" href="/newbook.jsp" style="display: inline;">创作新书</a>
            <div> <!-- 我写的书 目录-->
            </div>
        </div>

        <div class="col"> <!--关注列表-->
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

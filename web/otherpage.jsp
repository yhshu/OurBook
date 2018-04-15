<%--
  Created by IntelliJ IDEA.
  User: kk
  Date: 2018/4/13
  Time: 1:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>他人主页 - OurBook</title>
</head>

<body>
<!--跳转自nav搜索界面-->
<%@ include file="nav.jsp" %>
    <div class="row"> <!--他人信息-->
        <h4 id="otherpage_username"></h4>
        <h6 id="otherpage_userDescription"></h6>
    </div>

    <div class="row">
        <div class="col"> <!--文章、书目或动态-->
            <h5 style="display: inline; margin-right: 30px;">他写的书</h5> <!--隐藏or not？-->
            <a id="newbook" class="btn blue" href="/newbook.jsp" style="display: inline;">创作新书</a>
            <div> <!-- 我写的书 目录-->

            </div>
        </div>

        <div class="col"> <!--关注列表-->
        </div>
    </div>
</div>
<!--
<script>
    $(document).ready(function () {
        // 通过 cookie 修改 username_display
        var username = getCookie('follow');
        if ( followee !== "")
            document.getElementById("homepage_username").innerText = nickname + " (" + followee + ")";
    });
</script>
-->
</body>
</html>

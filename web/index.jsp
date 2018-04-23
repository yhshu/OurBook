<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title>首页 - OurBook</title>
</head>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("/login");
    }
%>
<body>
<%@ include file="nav.jsp" %>
<div class="container row" style="margin-top: 20px"><!-- 页面中部 -->
    <div class="col s6 card">1</div>
    <div class="col s2 card"><!--活跃用户-->

    </div>
</div>

</body>
</html>
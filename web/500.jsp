<%--
  Created by IntelliJ IDEA.
  User: 舒意恒
  Date: 2018/4/22
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>Internal Server Error - OurBook</title>
</head>
<body>
<nav> <!-- 顶部栏 -->
    <div class="nav-wrapper blue row" style="margin: 0">
        <!-- Logo -->
        <div class="col s3">
            <a href="${pageContext.request.contextPath}/home" class="brand-logo"><i class="material-icons">book</i>OurBook</a>
        </div>
        <div class="col s8">
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/index.jsp">返回 OurBook</a>
        </div>
    </div>
</nav>
<main>
    <div class="container">
        <h1 class="center-align">500</h1>
        <h3 class="center-align">Internal Server Error</h3>
        <br><br>
        <div class="center-align"><a class="grey-text center-align" href="index.jsp">返回首页</a></div>
    </div>
</main>
<%@ include file="footer.html" %>
</body>
</html>

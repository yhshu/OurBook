<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>添加章节 - OurBook</title>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">
    <form action="${pageContext.request.contextPath}/AddBookServlet" method="post">
        <input id="chapterName" name="chapterName">
        <input type="submit" class="blue btn" value="确认"/>
    </form>
</div>
</body>
</html>

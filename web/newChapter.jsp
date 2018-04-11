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
        <h4><i class="material-icons">bookmark</i>添加章节</h4>
        <div class="input-field col s12">
            <input id="chapterName" name="chapterName" type="text" class="validate" data-length="40"/>
            <label for="chapterName">章节标题</label>
        </div>
        <div class="input-field col s12">
                <textarea id="chapterContent" name="chapterContent" type="text" class="materialize-textarea"
                          data-length="100"></textarea>
            <label for="chapterContent">内容</label>
        </div>
        <div class="input-field col s12">
            <input id="keywords" name="keywords" type="text" class="validate" data-length="40">
            <label for="keywords">关键词</label>
        </div>
        <input type="submit" class="blue btn" value="确认"/>
    </form>
</div>
</body>
</html>

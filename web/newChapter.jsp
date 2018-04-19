<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>添加章节 - OurBook</title>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">
    <form action="${pageContext.request.contextPath}/ChapterServlet" method="post">
<<<<<<< HEAD
        <input name="bookID" type="hidden" value="<%=request.getParameter("bookID")%>"/>
=======
>>>>>>> 7cf50e8fdc77e1e0378bbade2934443190b92f7b
        <h4><i class="material-icons">bookmark</i>添加章节</h4>
        <div class="input-field col s12">
            <input id="chapterName" name="chapterName" type="text" class="validate" data-length="40"/>
            <label for="chapterName">章节标题</label>
        </div>
        <div class="input-field col s12">
                <textarea id="chapterContent" name="chapterContent" type="text" class="materialize-textarea"
                          data-length="5000"></textarea>
            <label for="chapterContent">内容</label>
        </div>
        <input type="submit" class="blue btn" value="确认"/>
    </form>
    <script>
        $(document).ready(function () {
            $('#chapterContent').characterCounter();
        });
    </script>
</div>
</body>
</html>

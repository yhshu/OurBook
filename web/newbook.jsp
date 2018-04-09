<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title>创建书籍 - OurBook</title>
</head>

<body>
<%@ include file="nav.jsp" %>
<div class="container" style="margin-top: 20px">
    <div class="col s5">
        <form action="${pageContext.request.contextPath}/AddBookServlet" method="post">
            <h4><i class="material-icons">book</i>创建一本书</h4>
            <div class="input-field col s12">
                <input id="bookname" name="bookname" type="text" class="validate" data-length="40"/>
                <label for="bookname">书名</label>
            </div>
            <div class="input-field col s12">
                <textarea id="bookDescription" name="bookDescription" type="text" class="materialize-textarea"
                          data-length="100"></textarea>
                <label for="bookDescription">简介（可选）</label>
            </div>
            <div class="input-field col s12">
                <input id="keywords" name="keywords" type="text" class="validate" data-length="40">
                <label for="keywords">关键词</label>
            </div>
            <input type="submit" class="blue btn" value="确认"/>
        </form>
        <script>
            $(document).ready(function () {
                $('#bookDescription').characterCounter();
            });
        </script>
    </div>
</div>
</body>
</html>
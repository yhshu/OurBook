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
    <div class="col card" style="width: 600px; padding: 20px">
        <form action="${pageContext.request.contextPath}/BookServlet" method="post">
            <input type="hidden" name="method" value="add"/>
            <input type="hidden" name="editor" value="<%=session.getAttribute("username")%>">
            <div style="border-bottom: 1px solid lightgray">
                <h4><i class="material-icons">book</i>创建一本书</h4>
            </div>
            <div style="padding: 0 20px">
                <div class="input-field col s12">
                    <input id="bookName" name="bookName" type="text" class="validate" data-length="40"/>
                    <label for="bookName">书名</label>
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
            </div>
            <input type="submit" class="blue btn" value="确认" style="float: right"/>
        </form>
        <script>
            $(document).ready(function () {
                $('#bookDescription').characterCounter(); // 文本框记数
            });
        </script>
    </div>
</div>
</body>
</html>
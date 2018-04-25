<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>添加章节 - OurBook</title>
</head>
<body class="white">
<%@ include file="nav.jsp" %>
<div class="container">

    <form action="${pageContext.request.contextPath}/ChapterServlet" method="post" onsubmit="return check_input();"
          id="form">
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
            $('#chapterContent').characterCounter(); // 文本框计数器
        });

        function check_input() {
            if (document.getElementById('chapterName').value === "" || document.getElementById('chapterContent').value === "") {
                toast({html: '请输入章节名称和内容', classes: 'rounded'});
                return false;
            }
            else return true;
        }

        $('#form').submit(function (event) {
            event.preventDefault();
            if (check_input()) {
                $.post('${pageContext.request.contextPath}/ChapterServlet', {
                        method: 'add',
                        chapterName: document.getElementById("chapterName").value,
                        chapterContent: document.getElementById("chapterContent").value,
                        bookID: <%=request.getParameter("bookID")%>
                    },
                    function (respondText) {
                        toast("添加章节成功");
                        window.location.href = respondText;
                    }
                ).fail(function (jqXHR) {
                    toast('未知错误');
                });
            }
        })
    </script>
</div>

</body>
</html>
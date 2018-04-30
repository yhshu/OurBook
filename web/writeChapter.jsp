<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title><%=request.getAttribute("method").equals("add") ? "添加" : "编辑"%>章节 - OurBook</title>
    <script src="${pageContext.request.contextPath}/js/tinymce/tinymce.min.js"></script>
    <script>
        tinymce.init({
            selector: 'textarea',
            height: 300
        });
    </script>
    <style>
        .mce-notification {
            display: none;
        }
    </style>
</head>
<body class="white">
<%@ include file="nav.jsp" %>
<div class="container">

    <form action="${pageContext.request.contextPath}/ChapterServlet" id="form" method="post">
        <h4>
            <i class="material-icons blue-text">bookmark</i>
            <%=request.getAttribute("method").equals("add") ? "添加" : "编辑"%>章节
        </h4>
        <div class="input-field col s12">
            <input id="chapterName" name="chapterName" type="text" class="validate" data-length="40"
                   value="<%=request.getAttribute("name")%>"/>
            <label for="chapterName">章节标题</label>
        </div>
        <textarea id="chapterContent" name="chapterContent"><%=request.getAttribute("content")%></textarea>
        <input type="submit" class="blue btn right" value="确认" style="margin-top: 20px"/>
    </form>
    <script>
        $(document).ready(function () {
            $('#chapterContent').characterCounter(); // 文本框计数器

            $('#form').submit(function (event) {
                event.preventDefault();
                if (check_input()) {
                    $.post('${pageContext.request.contextPath}/ChapterServlet', {
                        method: '<%=request.getAttribute("method")%>',
                        book: '<%=request.getParameter("book")%>',
                        sequence: '<%=request.getParameter("sequence")%>',
                        chapterName: $('#chapterName').val(),
                        chapterContent: $('#chapterContent').val()
                    }, function (respondText) {
                        toast('<%=request.getAttribute("method").equals("add") ? "添加" : "编辑"%>章节成功');
                        window.location.href = respondText;
                    }).fail(function (jqXHR) {
                        toast('<%=request.getAttribute("method").equals("add") ? "添加" : "编辑"%>章节失败');
                    });
                }
            })
        });

        function check_input() {
            if (!$.trim($('#chapterName').val()).length) {
                toast('请输入章节标题');
                return false;
            }
            if ($('#chapterContent').characterCounter().val === 0) {
                toast('请输入章节内容');
                return false;
            }
            else return true;
        }
    </script>
</div>

</body>
</html>
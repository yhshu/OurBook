<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>添加章节 - OurBook</title>
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
        <input id='bookID' type="hidden" name="bookID" value="<%=request.getAttribute("bookID")%>"/>
        <h4><i class="material-icons">bookmark</i>添加章节</h4>
        <div class="input-field col s12">
            <input id="chapterName" name="chapterName" type="text" class="validate" data-length="40"/>
            <label for="chapterName">章节标题</label>
        </div>
        <textarea id="chapterContent" name="chapterContent">在这里输入内容</textarea>
        <input type="submit" class="blue btn right" value="确认" style="margin-top: 20px"/>
    </form>
    <script>
        $(document).ready(function () {
            $('#chapterContent').characterCounter(); // 文本框计数器

            $('#form').submit(function (event) {
                event.preventDefault();
                if (check_input()) {
                    $.post('${pageContext.request.contextPath}/ChapterServlet', {
                        method: 'add',
                        bookID: $('#bookID').val(),
                        chapterName: $('#chapterName').val(),
                        chapterContent: $('#chapterContent').val()
                    }, function (respondText) {
                        toast("添加章节成功");
                        window.location.href = respondText;
                    }).fail(function (jqXHR) {
                        toast('添加章节失败');
                    });
                }
            })
        })
        ;

        function check_input() {
            if (!$.trim($('#chapterName').val()).length) {
                toast('请输入章节标题');
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
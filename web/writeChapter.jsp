<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title><%=request.getAttribute("method").equals("add") ? "添加" : "编辑"%>章节 - OurBook</title>
    <script src="${pageContext.request.contextPath}/js/tinymce/tinymce.min.js"></script>
    <script>
        tinymce.init({
            selector: 'textarea',
            height: 300,
            theme: 'modern',
            plugins: 'print preview fullpage powerpaste searchreplace autolink directionality advcode visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists textcolor wordcount tinymcespellchecker a11ychecker imagetools mediaembed  linkchecker contextmenu colorpicker textpattern help',
            toolbar1: 'formatselect | bold italic strikethrough forecolor backcolor | link | alignleft aligncenter alignright alignjustify  | numlist bullist outdent indent  | removeformat',
            image_advtab: true,
            templates: [
                { title: 'Test template 1', content: 'Test 1' },
                { title: 'Test template 2', content: 'Test 2' }
            ],
            content_css: [
                '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
                '//www.tinymce.com/css/codepen.min.css'
            ]
        });
    </script>
    <style>
        .mce-notification {
            display: none;
        }
    </style>
</head>
<body class="white">
<jsp:include page="nav.jsp"/>
<main>
    <div class="container" style="margin:20px auto;height:574px">
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
            <div class="input-field col s12">
                <input id="description" name="description" type="text" class="validate" data-length="100"/>
                <label for="description">提交说明</label>
            </div>
            <textarea id="chapterContent" name="chapterContent"><%=request.getAttribute("content")%></textarea>
            <input type="submit" class="blue btn right" value="提交" style="margin-top: 20px"/>
        </form>
    </div>
</main>
<%@ include file="footer.html" %>
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
                    chapterContent: $('#chapterContent').val(),
                    description: $('#description').val()
                }, function (respondText) {
                    toast('<%=request.getAttribute("method").equals("add") ? "添加" : "编辑"%>章节成功');
                    window.location.href = respondText;
                }).fail(function () {
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
        if (!$.trim($('#chapterContent').val()).length) {
            toast('请输入章节内容');
            return false;
        }
        else return true;
    }
</script>
</body>
</html>
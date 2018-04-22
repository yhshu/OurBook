<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="dao.ChapterDao" %>
<%@ page import="dao.impl.ChapterDaoImpl" %>
<%@ page import="model.Chapter" %>
<%@ page import="java.io.BufferedReader" %>
<html>
<head>
    <%@ include file="header.jsp" %>
</head>
<body class="white">
<jsp:include page="nav.jsp"/>
<div class="row" style=" overflow: hidden;">
    <div class="col s3 grey lighten-5" id="side" style="padding-bottom: 10000px;
  margin-bottom: -10000px;">
        <div style="margin-left: 4px; margin-top: 10px;">
            <a href="${pageContext.request.contextPath}/book?id=<%=request.getAttribute("bookID")%>"><i
                    class="material-icons">arrow_back</i>返回</a>
            <a style="margin-left: 72px;"><i class="material-icons">edit</i>编辑</a>
            <a style="margin-left: 72px;" class="modal-trigger" href="#delete_confirm"><i
                    class="material-icons">delete_forever</i>删除</a>
        </div>
        <ul style="margin-top: 2px;">
            <!-- 本书的所有章节目录-->
            <%
                ChapterDao chapterDao = new ChapterDaoImpl();
                Chapter[] chapters = chapterDao.findByBookID((int) request.getAttribute("bookID"));
                for (Chapter chapter : chapters) {
            %>
            <li style="padding: 5px">
                <a class="black-text"
                   href="${pageContext.request.contextPath}/read?book=<%=chapter.getBookID()%>&sequence=<%=chapter.getSequence()%>"><%=chapter.getName()%>
                </a>
            </li>
            <%
                }%>
        </ul>
    </div>
    <div class="col s9 white" id="main" style=" padding-bottom: 10000px;
  margin-bottom: -10000px;">
        <h4 style="padding: 5px 20px;"><%=request.getAttribute("name")%>
        </h4>
        <%
            try {
                BufferedReader br = (BufferedReader) request.getAttribute("reader");
                String line;
                while ((line = br.readLine()) != null) {%>
        <p style="padding: 0 10px;"><%=line%>
        </p>
        <%
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </div>
    <script>
        $(document).ready(function () {
            $('.modal').modal();
        });
    </script>
    <form id="delete_chapter" action="${pageContext.request.contextPath}/DeleteChapterServlet" method="post">
        <input type="hidden" name="bookID" value="<%=(int)request.getAttribute("bookID")%>"/>
        <input type="hidden" name="sequence" value="<%=(int)request.getAttribute("sequence")%>">
        <input type="hidden" name="chapterName" value="<%=request.getAttribute("name")%>">
    </form>
    <div id="delete_confirm" class="modal">
        <div class="modal-content">
            <h5>确认删除本章？</h5>
        </div>
        <div class="modal-footer">
            <a class="modal-action modal-close waves-effect waves-green btn-flat">取消</a>
            <a class="modal-action modal-close waves-effect waves-green btn-flat"
               onclick="document.getElementById('delete_chapter').submit();">确认</a>
        </div>
    </div>
</div>
</body>
</html>
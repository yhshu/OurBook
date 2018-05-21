<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="model.Edit" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% SimpleDateFormat sdf = new SimpleDateFormat("yyyy.M.d");%>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title><%=request.getAttribute("name")%> - OurBook</title>
</head>
<body class="lighten-5" style="background-color: #F9F3E9">
<jsp:include page="nav.jsp"/>
<main>
    <div id="side" style=" margin-top: 3px; padding-left: 7px; float: left; width: 300px;">
        <div style="margin-left: 4px; margin-top: 10px;">
            <a href="${pageContext.request.contextPath}/book?id=<%=request.getAttribute("bookID")%>"><i
                    class="material-icons">arrow_back</i>返回</a>
            <% if (session.getAttribute("username").equals(request.getAttribute("chiefEditor")) || (boolean) request.getAttribute("isCollaborator")) {
                // 主编或协作者均拥有编辑权限
            %>
            <a style="margin-right: 20px; float: right"
               href="${pageContext.request.contextPath}/modify?book=<%=request.getAttribute("bookID")%>&sequence=<%=request.getAttribute("sequence")%>">
                <i class="material-icons">edit</i>编辑</a>
            <%}%>
        </div>
        <ul style="margin-top: 2px;">
            <!-- 本章节的所有历史-->
            <%
                Edit[] edits = (Edit[]) request.getAttribute("edits");
                for (Edit edit : edits) {
            %>
            <li style="padding: 10px">
                <a class="black-text" style="display:inline-block;width: 35%"
                   href="${pageContext.request.contextPath}/old?id=<%=edit.getID()%>">
                    <%=edit.getName()%>
                </a>
                <a class="grey-text" href="${pageContext.request.contextPath}/home?user=<%=edit.getEditorUsername()%>"
                   style="display:inline-block;width: 25%;text-align: center">
                    <%=edit.getEditorNickname()%>
                </a>
                <p class="grey-text" style="display: inline-block;margin: 0;width: 35%;text-align: right">
                    <%=sdf.format(edit.getModifiedTime())%>
                </p>
                <p class="grey-text" style="margin: 10px 0 ;word-wrap:break-word;">
                    <%=edit.getDescription() != null ? edit.getDescription() : ""%>
                </p>
            </li>
            <%
                }%>
        </ul>
    </div>
    <div id="main" style=" margin-top: 3px; padding-left: 5px; float:left; width: 74.6%; ">
        <div>
            <h4 style="padding: 5px 20px;display: inline-block"><%=request.getAttribute("name")%>
            </h4>
            <h6 class="grey-text"
                style="display: inline-block;margin: 0 10px"><%=request.getAttribute("editorNickname")%>
            </h6>
            <h6 class="grey-text"
                style="display: inline-block;margin: 0 10px"><%=sdf.format(request.getAttribute("time"))%>
            </h6>
        </div>
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
</main>
<script>
    $(document).ready(function () {
        $('.modal').modal();
    });
</script>
<form id="delete_chapter" action="${pageContext.request.contextPath}/ChapterServlet" method="post">
    <input type="hidden" name="method" value="delete"/>
    <input type="hidden" name="book" value="<%=(int)request.getAttribute("bookID")%>"/>
    <input type="hidden" name="sequence" value="<%=(int)request.getAttribute("sequence")%>">
    <input type="hidden" name="chapterName" value="<%=request.getAttribute("name")%>">
</form>
<!--删除章节模态框-->
<div id="delete_confirm" class="modal">
    <div class="modal-content">
        <h5><i class="material-icons">warning</i>确认删除本章？</h5>
        <h6>本章的所有编辑记录将被同时永久删除。</h6>
    </div>
    <div class="modal-footer">
        <a class="modal-action modal-close waves-effect waves-green btn-flat">取消</a>
        <a class="modal-action modal-close waves-effect waves-green btn-flat"
           onclick="document.getElementById('delete_chapter').submit();">确认</a>
    </div>
</div>
</body>
</html>
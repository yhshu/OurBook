<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Chapter" %>
<%

%>
<%--
  Created by IntelliJ IDEA.
  User: Radiance
  Date: 4/12/18
  Time: 9:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title><%=request.getAttribute("bookName")%> - OurBook</title>

    <script type="text/javascript">
        function change() {
            var btn = document.getElementById("btn");
            btn.value = "已关注";
            btn.disabled = true;
        }

        $(document).ready(function () {
            $('.modal').modal();
        });
    </script>

</head>
<body>
<jsp:include page="nav.jsp"/>
<div class="container row" style="margin-top: 50px">

    <div style="display: inline">
        <div style="margin: 20px auto;display: grid;grid-template-columns: 192px auto" class="card">
            <%
                if (request.getAttribute("cover") == null || request.getAttribute("cover").equals("")) {
                    // 如果无封面
            %>
            <div style="width: 192px;height: 256px;background-color: #0D47A1;border-radius: 2px 0 0 2px">
                <h4 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                    <%=request.getAttribute("bookName")%>
                </h4>
            </div>
            <%} else { // 如果有封面%>
            <img style="width: 192px;height: 256px;object-fit: cover"
                 src="<%=request.getAttribute("cover")%>">
            <%}%>
            <div style="display: grid;grid-template-rows: 66px 40px 1px 130px">
                <div style="margin: 25px 0 0 25px">
                    <h5 style="float: left;margin: 0">
                        <a style="color: black"
                           href="${pageContext.request.contextPath}/book.jsp?id=<%=request.getAttribute("bookID")%>">
                            <%=request.getAttribute("bookName")%>
                        </a>
                    </h5>
                    <%if (!request.getAttribute("editor").equals(session.getAttribute("username"))) {%>
                    <a href="favorite?method=<%=(boolean) request.getAttribute("isFavorite") ?
                     "remove" : "add"%>&book=<%=request.getAttribute("bookID")%>"
                       class="pink-text"
                       style="float: left;font-size: 27px;line-height: 32px;margin-left: 20px">
                        <i class="material-icons">
                            <%=(boolean) request.getAttribute("isFavorite") ? "favorite" : "favorite_border"%>
                        </i>
                    </a>
                    <%}%>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/home?user=<%=request.getAttribute("editor")%>"
                       style="color: gray;margin: 0 0 0 25px; display: inline;">
                        <%=request.getAttribute("editorNickname")%>
                    </a>
                    <%
                        //如果不是本人
                        if (!request.getAttribute("editor").equals(session.getAttribute("username"))) {%>
                    <a type="submit" class="pink btn-small"
                       style="margin-left: 10px; display: inline; -webkit-appearance:none; -moz-appearance:none;"
                       href="follow?followee=<%=request.getAttribute("editor")%>&method=
<%=(boolean)request.getAttribute("isFollowing")?"remove":"add"%>">
                        <%=(boolean) request.getAttribute("isFollowing") ? "取消关注" : "关注"%></a>
                    <%}%>
                </div>

                <hr style="width: 100%;margin: 0;border-top: 1px gray"/>
                <p style="margin: 25px 0 0 25px">
                    <%=request.getAttribute("description")%>
                </p>
            </div>
        </div>
    </div>

    <!-- TODO 点击本书作者用户名跳转到其主页-->

    <div class="card" style="padding: 20px">
        <h5 style="display: inline; margin-left:20px; margin-right: 30px;">章节目录</h5>
        <style>form {
            margin: 0;
        }</style>

        <form action="${pageContext.request.contextPath}/newChapter.jsp" accept-charset="UTF-8" method="post"
              style="display: inline; " id="newChapterForm">
            <!-- 添加章节 表单 -->
            <input name="bookID" type="hidden" value="<%=request.getAttribute("bookID")%>"/>
            <button class="btn blue"
                    style="display: inline;margin-right: 10px; -webkit-appearance:none ; -moz-appearance:none;"
                    onclick="document.getElementById('newChapterForm').submit();">添加章节
            </button>
        </form>

        <%if (request.getAttribute("editor").equals(session.getAttribute("username"))) {%>
        <a href='#delete_modal' class="btn red modal-trigger" style="float: right">删除本书</a>

        <div id="delete_modal" class="modal"><!-- 删除本书 模态框 -->
            <div class="modal-content">
                <h4>确认删除吗？</h4>
                <h5>忽略警告，糟糕的事情可能会发生。</h5>
                <p>该操作是不可撤销的。这将永久地删除与<b><%=' ' + (String) request.getAttribute("bookName") + ' '%>
                </b>相关的所有信息。</p>
                <p>请输入本书名称以确认操作。</p>
                <input id="bookname_confirm" type="text" oninput="change_delete_link()">
                <label for="bookname_confirm"></label>
                <script>
                    function change_delete_link() {
                        var val = document.getElementById('bookname_confirm').value;
                        var link = $('#delete_link');
                        var disabled = ' disabled';
                        if (val === '<%=request.getAttribute("bookName")%>') { // 使链接有效
                            link.removeClass(disabled);
                        } else { // 使链接失效
                            if (!link.hasClass(disabled))
                                link.addClass(disabled);
                        }
                    }
                </script>
            </div>
            <div class="modal-footer">
                <a href="#!" id="delete_link"
                   class="modal-action modal-close waves-effect waves-green btn-flat  red-text disabled ">我理解后果，删除本书</a>
                <!--TODO 请求删除本书-->
            </div>
        </div>
        <%}%>

        <div class="collection">
            <%for (Chapter chapter : (Chapter[]) request.getAttribute("chapters")) {%>
            <div>
                <a href="${pageContext.request.contextPath}/read?book=
<%=chapter.getBookID()%>&sequence=<%=chapter.getSequence()%>" class="collection-item black-text"><%=chapter.getName()%>
                </a></div>
            <%}%>
        </div>
    </div>
</div>
</body>
</html>
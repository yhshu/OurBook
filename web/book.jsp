<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Chapter" %>
<%@ page import="model.User" %>
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
            $('select').material_select();
            $('#select_sequence').change(function () {
                $('#sequence').val($('#select_sequence').val());
            })
        });
    </script>
    <% User[] collaborators = (User[]) request.getAttribute("collaborators");%>
</head>
<body>
<jsp:include page="nav.jsp"/>
<div class="container row" style="margin-top: 50px">
    <div style="width:1000px">
        <div style="margin: 20px auto;display: grid;grid-template-columns: 192px auto" class="card">
            <%
                if (request.getAttribute("cover") == null || request.getAttribute("cover").equals("")) { // 如果无封面
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
                        <a style="color: black">
                            <%=request.getAttribute("bookName")%>
                        </a>
                    </h5>
                    <a href="" class="pink-text"
                       style="float: left;font-size: 27px;line-height: 32px;margin-left: 20px" id="favorite_submit"
                       data-method="<%=(boolean) request.getAttribute("isFavorite") ? "remove" : "add"%>">
                        <i class="material-icons"
                           id="favorite_icon"><%=(boolean) request.getAttribute("isFavorite") ? "favorite" : "favorite_border"%>
                        </i>
                    </a>
                    <%if (request.getAttribute("editor").equals(session.getAttribute("username"))) {// 如果该书作者是当前用户%>
                    <!--设置协作者 按钮-->
                    <a href="#set_collaborators_modal" class="modal-trigger waves-effect waves-light"
                       data-target="" style="display: inline; font-size: 32px;float: right; margin-right: 20px;"
                       id="set_collaborators">
                        <i class="material-icons small grey-text">settings</i></a>
                    <%}%>
                    <!--设置协作者 模态框-->
                    <div id="set_collaborators_modal" class="modal">
                        <div class="modal-content">
                            <h4>设置协作者</h4>
                            <p>授予协作者编辑本书的权限。</p>
                            <%
                                if (collaborators == null) {// 如果无协作者 %>
                            <div class="chips input-field chips-placeholder">
                            </div>
                            <script>
                                // chips 脚本
                                $('.chips').material_chip();
                                $('.chips-placeholder').material_chip({
                                    placeholder: '键入并按回车',
                                    secondaryPlaceholder: '键入并按回车'
                                });
                            </script>
                            <%} else { // 如果有协作者 %>
                            <div class="chips input-field chips-initial">
                            </div>
                            <script>
                                $('.chips').material_chip();
                                $('.chips-initial').material_chip({
                                    data: [
                                            <%for(User collaborator:collaborators){%>{
                                            tag: '<%=collaborator.getUsername()%>',
                                        }, <%}%>],
                                });
                            </script>
                            <%}%>
                        </div>
                        <div class="modal-footer">
                            <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">取消</a>
                            <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat"
                               id="collaborator_submit">确认</a>
                        </div>
                    </div>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/home?user=<%=request.getAttribute("editor")%>"
                       style="color: gray;margin: 0 0 0 25px; display: inline;">
                        <%=request.getAttribute("editorNickname")%>
                    </a>
                    <% // 如果不是作者不是当前用户
                        if (!request.getAttribute("editor").equals(session.getAttribute("username"))) {%>
                    <a class="pink btn-small"
                       style="margin-left: 10px; display: inline; -webkit-appearance:none; -moz-appearance:none;"
                       id="follow_submit"
                       data-method='<%=(boolean)request.getAttribute("isFollowing")?"remove":"add"%>'
                       data-followee='<%=request.getAttribute("editor")%>'>
                        <%=(boolean) request.getAttribute("isFollowing") ? "取消关注" : "关注"%>
                    </a>
                    <%}%>
                </div>

                <hr style="width: 100%;margin: 0;border-top: 1px gray"/>
                <p style="margin: 25px 0 0 25px">
                    <%=request.getAttribute("description")%>
                </p>
            </div>
        </div>
    </div>

    <div class="card" style="padding: 20px;width:1000px"><!-- 章节目录 -->
        <h5 style="margin-left:20px; margin-right: 30px;">章节目录</h5>
        <style>form {
            margin: 0;
        }
        </style>
        <%if (session.getAttribute("username").equals(request.getAttribute("editor"))) {%>
        <div style="width: 960px">
            <form action="${pageContext.request.contextPath}/write" accept-charset="UTF-8" method="get"
                  id="newChapterForm" style="display: inline-block;width: 800px">
                <!-- 添加章节 表单 -->
                <input name="book" type="hidden" value="<%=request.getAttribute("bookID")%>"/>
                <input id="sequence" name="sequence" type="hidden"
                       value="<%=(int)request.getAttribute("chapterNum")+1%>"/>
                <button class="btn blue"
                        style="display: inline;margin-right: 10px; -webkit-appearance:none ; -moz-appearance:none;">添加章节
                </button>
                <div class="input-field"
                     style="width: 300px;display: inline-block;left: 50px;margin: 0;height: 36px">
                    <select id="select_sequence">
                        <%for (int i = 1; i <= (int) request.getAttribute("chapterNum"); i++) {%>
                        <option value="<%=i%>">第<%=i%>章</option>
                        <%}%>
                        <option value="<%=(int) request.getAttribute("chapterNum") + 1%>" selected>
                            第<%=(int) request.getAttribute("chapterNum") + 1%>章
                        </option>
                    </select>
                    <label>选择章节插入位置</label>
                </div>
            </form>
            <a href='#delete_modal' class="btn red modal-trigger right">删除本书</a>
        </div>

        <div id="delete_modal" class="modal"><!-- 删除本书 模态框 -->
            <div class="modal-content">
                <h4>确认删除吗？</h4>
                <h5>忽略警告，糟糕的事情可能会发生。</h5>
                <p>该操作是不可撤销的。这将永久地删除与<b><%=' ' + (String) request.getAttribute("bookName") + ' '%>
                </b>相关的所有信息。</p>
                <p>请输入本书名称以确认操作。</p>
                <input id="bookname_confirm" type="text" oninput="change_delete_link()">
                <label for="bookname_confirm"></label>
            </div>
            <div class="modal-footer">
                <a class="modal-action modal-close waves-effect waves-green btn-flat">取消</a>
                <a id="delete_link"
                   class="modal-action modal-close waves-effect waves-green btn-flat red-text disabled"
                   onclick="document.getElementById('deleteBookForm').submit();">我理解后果，删除本书</a>
                <!-- 请求删除本书-->
                <form action="${pageContext.request.contextPath}/deleteBook" method="get" id="deleteBookForm">
                    <input type="hidden" name="book" value="<%=request.getAttribute("bookID")%>"/>
                </form>
            </div>
        </div>
        <%}%>

        <div class="collection">
            <%
                for (Chapter chapter : (Chapter[]) request.getAttribute("chapters")) {
            %>
            <div>
                <a href="${pageContext.request.contextPath}/read?book=
<%=chapter.getBookID()%>&sequence=<%=chapter.getSequence()%>" class="collection-item black-text"><%=chapter.getName()%>
                </a>
                <%
                    if (request.getAttribute("editor").equals(session.getAttribute("username"))) {
                %>
                <a href="modify?book=<%=chapter.getBookID()%>&sequence=<%=chapter.getSequence()%>"
                   class="right modify_chapter_icon"
                   style="position:relative;top:-43px;right:10px;font-size: 24px;line-height: 43px;height: 0">
                    <i class="material-icons">mode_edit</i>
                </a>
                <%}%>
            </div>
            <%}%>
        </div>
    </div>
</div>

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

    $('#follow_submit').click(function (event) {
        event.preventDefault();
        $.get('follow?'
            , {
                followee: $('#follow_submit').data("followee"),
                method: $('#follow_submit').data("method")
            }, function (respondText) {
                if ($('#follow_submit').data("method") === "remove") {
                    toast('取消关注成功');
                    $('#follow_submit').html("关注");
                    $('#follow_submit').data("method", "add");
                }
                else if ($('#follow_submit').data("method") === "add") {
                    toast('关注成功');
                    $('#follow_submit').html("取消关注");
                    $('#follow_submit').data("method", "remove");
                }
            }).fail(function () { // 服务器响应错误信息
            toast("操作异常");
        })
    });

    $('#favorite_submit').click(function (event) {
        event.preventDefault();
        $.get('${pageContext.request.contextPath}/favorite', {
            method: $('#favorite_submit').data("method"),
            book:<%=request.getAttribute("bookID")%>
        }, function () {
            if ($('#favorite_submit').data("method") === "remove") {
                toast('取消收藏成功');
                $('#favorite_icon').html("favorite_border");
                $('#favorite_submit').data("method", "add");
                $('#favorite_icon').attr("data-tooltip", '收藏');
                $('#favorite_icon').tooltip();
            }
            else if ($('#favorite_submit').data("method") === "add") {
                toast('收藏成功');
                $('#favorite_icon').html("favorite");
                $('#favorite_submit').data("method", "remove");
                $('#favorite_icon').attr("data-tooltip", '取消收藏');
                $('#favorite_icon').tooltip();
            }
        }).fail(function () { // 服务器响应错误信息
            toast("操作异常");
        })
    });

    $('#collaborator_submit').click(function (event) {
        var Collaborator = $('.chip').text().replace(/close/g, " ");
        Collaborator = Collaborator.substring(0, Collaborator.length - 1);
        $.post('${pageContext.request.contextPath}/collaborator', {
            bookID:<%=request.getAttribute("bookID")%>,
            collaborator: Collaborator
        }, function (responseText) {
            toast("设置协作者成功");
            window.location.href = responseText;
        }).fail(function () {
            toast("操作异常，请重试");
        })
    });
</script>
</body>
</html>
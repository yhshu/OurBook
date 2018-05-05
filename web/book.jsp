<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Chapter" %>
<%@ page import="model.Comment" %>
<%@ page import="model.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yy.M.d  HH:mm");
    User[] collaborators = (User[]) request.getAttribute("collaborators");
    User chiefEditor = (User) request.getAttribute("chiefEditor");
    Comment[] comments = (Comment[]) request.getAttribute("comments");
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
</head>
<body>
<jsp:include page="nav.jsp"/>
<main>
    <div class="container row" style="margin: 50px 176px;">
        <div style="width:1000px"><!-- 书籍信息 -->
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
                        <a href="#" class="pink-text"
                           style="float: left;font-size: 27px;line-height: 32px;margin-left: 20px" id="favorite_submit"
                           data-method="<%=(boolean) request.getAttribute("isFavorite") ? "remove" : "add"%>">
                            <i class="material-icons"
                               id="favorite_icon"><%=(boolean) request.getAttribute("isFavorite") ? "favorite" : "favorite_border"%>
                            </i>
                        </a>
                        <%if (chiefEditor.getUsername().equals(session.getAttribute("username"))) { // 如果该书作者是当前用户%>
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
                                <p>每输入一个用户名，按下 Enter 确认。</p>
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
                                                tag: '<%=collaborator.getUsername()%>'
                                            }, <%}%>]
                                    });
                                </script>
                                <%}%>
                            </div>
                            <div class="modal-footer">
                                <a class="modal-action modal-close waves-effect waves-green btn-flat">取消</a>
                                <a class="modal-action modal-close waves-effect waves-green btn-flat"
                                   id="collaborator_submit">确认</a>
                            </div>
                        </div>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}/home?user=<%=chiefEditor.getUsername()%>"
                           style="color: gray;margin: 0 0 0 25px; display: inline;">
                            <%=chiefEditor.getNickname()%>
                        </a>
                        <% // 如果不是作者不是当前用户
                            if (!chiefEditor.getUsername().equals(session.getAttribute("username"))) {%>
                        <a class="pink btn-small"
                           style="margin-left: 10px; display: inline; -webkit-appearance:none; -moz-appearance:none;"
                           id="follow_submit"
                           data-method='<%=(boolean)request.getAttribute("isFollowing")?"remove":"add"%>'
                           data-followee='<%=chiefEditor.getUsername()%>'>
                            <%=(boolean) request.getAttribute("isFollowing") ? "取消关注" : "关注"%>
                        </a>
                        <%}%>
                    </div>
                    <hr style="width: 100%;margin: 0;border-top: 1px gray"/>
                    <p style="margin: 25px 25px 0 25px">
                        <%=request.getAttribute("description")%>
                    </p>
                </div>
            </div>
        </div>
        <div class="row" style="width: 1000px"><!--章节目录和编辑者-->
            <div class="col card" style="padding: 0 20px;width:723px; margin-right:23px;"><!-- 章节目录 -->
                <h5 style="text-align: center; margin-bottom: 30px;">章节目录</h5>
                <style>form {
                    margin: 0;
                }
                </style>
                <%
                    // 主编拥有所有权限（添加、编辑、删除），协作者可以添加或编辑
                    if (session.getAttribute("username").equals(chiefEditor.getUsername()) ||
                            ((boolean) request.getAttribute("isCollaborator"))) {
                %>
                <div style="width: 685px;">
                    <form action="${pageContext.request.contextPath}/write" accept-charset="UTF-8" method="get"
                          id="newChapterForm" style="display: inline-block;width: 460px">
                        <!-- 添加章节 表单 -->
                        <input name="book" type="hidden" value="<%=request.getAttribute("bookID")%>"/>
                        <input id="sequence" name="sequence" type="hidden"
                               value="<%=(int)request.getAttribute("chapterNum")+1%>"/>
                        <button class="btn blue"
                                style="display: inline;margin-right: 10px; -webkit-appearance:none ; -moz-appearance:none;">
                            添加章节
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
                            <label>新增序号</label>
                        </div>
                    </form>
                    <%if (session.getAttribute("username").equals(chiefEditor.getUsername())) { // 如果当前用户是主编，可删除本书%>
                    <a href='#delete_modal' class="btn red modal-trigger"
                       style="display: inline-block; margin-left:128px;">删除本书</a>
                    <div id="delete_modal" class="modal"><!-- 删除本书 模态框 -->
                        <div class="modal-content">
                            <h4><i class="material-icons">warning</i>确认删除吗？</h4>
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
                            <form action="${pageContext.request.contextPath}/deleteBook" method="get"
                                  id="deleteBookForm">
                                <input type="hidden" name="book" value="<%=request.getAttribute("bookID")%>"/>
                            </form>
                        </div>
                    </div>
                    <%}%>
                </div>
                <%}%>

                <div class="collection" style="margin-top: 20px;"><!--章节目录中的章节链接-->
                    <%
                        for (Chapter chapter : (Chapter[]) request.getAttribute("chapters")) {
                    %>
                    <div style="height: 43px; overflow: hidden">
                        <a href="${pageContext.request.contextPath}/read?book=
<%=chapter.getBookID()%>&sequence=<%=chapter.getSequence()%>" class="collection-item black-text"><%=chapter.getName()%>
                            <span class="grey-text right"
                                  style="display: inline-block;margin-right: 45px"><%=sdf.format(chapter.getLastModified())%></span>
                        </a>
                        <%
                            if (chiefEditor.getUsername().equals(session.getAttribute("username")) || ((boolean) request.getAttribute("isCollaborator"))) { // 主编或协作者
                        %>
                        <!--历史记录 按钮-->
                        <a href="#history_modal" class="right modal-trigger history_request"
                           style="position: relative; top: -40px; right: 10px; font-size: 20px;line-height: 40px"
                           data-sequence="<%=chapter.getSequence()%>">
                            <i class="material-icons">history</i></a>
                        <!--编辑章节 按钮-->
                        <a href="modify?book=<%=chapter.getBookID()%>&sequence=<%=chapter.getSequence()%>"
                           class="right modify_chapter_icon"
                           style="position: relative; top: -40px; right: 10px; font-size: 20px;line-height: 40px">
                            <i class="material-icons">mode_edit</i>
                        </a>
                        <!--历史记录 模态框-->
                        <div id="history_modal" class="modal bottom-sheet">
                            <div class="modal-content">
                                <h5 id="history_title">历史记录</h5>
                                <div id="history_content">
                                    正在加载...
                                </div>
                            </div>
                        </div>
                        <%}%>
                    </div>
                    <%}%>
                </div>
            </div>


            <div class="col card" style=" width:253px"><!--编辑者列表，包括主编与协作者-->
                <h5 style="text-align: center">编辑者</h5>
                <div style="margin: 10px auto">
                    <!--主编信息-->
                    <div class="row user_<%=chiefEditor.getUsername()%>" style="margin: 15px 5px;">
                        <a href="home?user=<%=chiefEditor.getUsername()%>"><!--用户头像-->
                            <img src="<%=chiefEditor.getAvatar()%>"
                                 style="width:40px;height: 40px;border-radius: 5%;            float: left;object-fit: cover;margin-right: 5px">
                        </a>
                        <div class="left">
                            <!--用户名与昵称-->
                            <p style="margin:0 5px">
                                <a href="home?user=<%=chiefEditor.getUsername()%>">
                                    <%=chiefEditor.getNickname()%>
                                </a>
                            </p>
                            <p class="grey-text" style="margin: 0 5px">
                                @<%=chiefEditor.getUsername()%>
                            </p>
                        </div>
                        <p class="right" style="margin: 10px;">主&nbsp;&nbsp;&nbsp;编</p>
                    </div>
                    <% if (collaborators != null) // 协作者信息
                        for (User collaborator : collaborators) {
                    %>
                    <div class="row user_<%=collaborator.getUsername()%>" style="margin: 15px 5px;">
                        <a href="home?user=<%=collaborator.getUsername()%>"><!--用户头像-->
                            <img src="<%=collaborator.getAvatar()%>"
                                 style="width:40px;height: 40px;border-radius: 5%;            float: left;object-fit: cover;margin-right: 5px">
                        </a>
                        <!--用户名与昵称-->
                        <div class="left">
                            <p style="margin: 0 5px">
                                <a href="home?user=<%=collaborator.getUsername()%>">
                                    <%=collaborator.getNickname()%>
                                </a>
                            </p>
                            <p class="grey-text" style="margin: 0 5px">
                                @<%=collaborator.getUsername()%>
                            </p>
                        </div>
                        <p class="right" style="margin: 10px;">协作者</p>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>

        <div class="card" style="padding: 20px; width: 1000px;"><!--评论-->
            <h6>评论</h6>
            <div class="row" style="width: 960px; margin-bottom: 0;"><!--评论输入框-->
                <div class="input-field col s12">
                <textarea id="comment_text" class="materialize-textarea" data-length="140"
                          style="float: left; width: 860px;"
                          placeholder="写下你的评论..." onclick="showButton()" oninput="enableButton()"></textarea>
                    <button class="btn blue disabled" style="display: none; margin-left: 16px;" id="comment_submit">
                        提交
                    </button>
                </div>
            </div>

            <div><!--本书已有评论-->
                <%
                    if (comments != null) {
                        for (Comment comment : comments) {%>
                <div id="comment_<%=comment.getID()%>" style="width: 960px; margin-bottom: 15px;">
                    <div class="row" style="margin-bottom: 0;">
                        <span><a href="${pageContext.request.contextPath}/home?user=<%=comment.getUsername()%>"><img
                                src="<%=comment.getAvatar()%>"
                                style="width: 24px;height: 24px; float: left;"></a></span>
                        <span><a style="float: left; margin-left: 10px;"
                                 href="${pageContext.request.contextPath}/home?user=<%=comment.getUsername()%>"><%=comment.getNickname()%></a></span>
                        <span><a class="grey-text"
                                 style=" float: right;"><%=sdf.format(comment.getDatetime())%></a></span>
                    </div>
                    <div class="row" style="margin: 0;">
                        <h6><%=comment.getContent()%>
                        </h6>
                    </div>
                    <%
                        if (session.getAttribute("username").equals(comment.getUsername())) { // 如果本条评论是当前用户发出的，可删除本条评论
                    %>
                    <div class="row" style="margin: 0;">
                        <a href="#comment_delete_confirm" class="grey-text modal-trigger delete_comment_request"
                           data-commentID="<%=comment.getID()%>"> <i
                                class="material-icons">delete_forever</i>
                            删除</a>
                    </div>
                    <div id="comment_delete_confirm" class="modal"><!--删除评论 确认模态框-->
                        <div class="modal-content">
                            <h6>你确定删除这条评论吗？</h6>
                        </div>
                        <div class="modal-footer">
                            <a href="#" class="modal-action modal-close waves-effect waves-green btn-flat">取消</a>
                            <a href="#"
                               class="modal-action modal-close waves-effect waves-green btn-flat deleteComment">确定</a>
                        </div>
                    </div>
                    <%}%>
                </div>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </div>
</main>
<%@include file="footer.html" %>
<script>
    $(document).ready(function () {
        $('select').material_select();
        $('#select_sequence').change(function () {
            $('#sequence').val($('#select_sequence').val());
        })
    });
    $('#comment_delete_confirm').modal();
    $('#set_collaborators_modal').modal();
    $('#delete_modal').modal();
    $('#history_modal').modal({
        ready: function (modal, trigger) {
            modal_render(trigger);
            $('#history_title').html(historyTitle);
            $('#history_content').html(historyContent);
        }
    });

    var follow_submit = $('#follow_submit');
    var favorite_submit = $('#favorite_submit');
    var favorite_icon = $('#favorite_icon');
    var comment_submit = $('#comment_submit');

    function showButton() {
        document.getElementById('comment_submit').style.display = "inline";
    }

    function enableButton() {
        $('#content_text').trigger('autoresize');
        if ($('#comment_text').val() === '') {
            if (!comment_submit.hasClass("disabled"))
                $('#comment_submit').addClass(" disabled");
        } else {
            if (comment_submit.hasClass("disabled"))
                $('#comment_submit').removeClass(" disabled");
        }
    }

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

    follow_submit.click(function (event) {
        event.preventDefault();
        $.get('follow?', {
            followee: follow_submit.data("followee"),
            method: follow_submit.data("method")
        }, function () {
            if (follow_submit.data("method") === "remove") {
                toast('取消关注成功');
                follow_submit.html("关注");
                follow_submit.data("method", "add");
            }
            else if (follow_submit.data("method") === "add") {
                toast('关注成功');
                follow_submit.html("取消关注");
                $('#follow_submit').data("method", "remove");
            }
        }).fail(function () { // 服务器响应错误信息
            toast("操作异常");
        })
    });

    favorite_submit.click(function (event) {
        event.preventDefault();
        $.get('${pageContext.request.contextPath}/favorite', {
            method: favorite_submit.data("method"),
            book:<%=request.getAttribute("bookID")%>
        }, function () {
            if (favorite_submit.data("method") === "remove") {
                toast('取消收藏成功');
                favorite_icon.html("favorite_border");
                favorite_submit.data("method", "add");
                favorite_icon.attr("data-tooltip", '收藏');
                favorite_icon.tooltip();
            }
            else if (favorite_submit.data("method") === "add") {
                toast('收藏成功');
                favorite_icon.html("favorite");
                favorite_submit.data("method", "remove");
                favorite_icon.attr("data-tooltip", '取消收藏');
                favorite_icon.tooltip();
            }
        }).fail(function () { // 服务器响应错误信息
            toast("操作异常");
        })
    });

    $('#collaborator_submit').click(function () {
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
            location.reload();
        })
    });

    comment_submit.click(function () { // 添加评论按钮
        var Content = $('#comment_text').val();
        if (Content.length > 140) {
            toast("超出长度限制");
            return;
        }
        if (Content === '') {
            toast("请键入评论");
            return;
        }
        $.post('${pageContext.request.contextPath}/comment', {
            method: "add",
            bookID: "<%=request.getAttribute("bookID")%>",
            username: "<%=session.getAttribute("username")%>",
            content: Content
        }, function () {
            toast("评论成功");
            location.reload();
        }).fail(function () {
            toast("操作异常，请重试");
        })
    });

    var delete_comment_id;
    $('.delete_comment_request').click(function () { // 页面上的删除评论按钮
        delete_comment_id = $(this).data('commentid');
    });

    $('.deleteComment').click(function (event) { // 删除评论模态框的确认按钮
        event.preventDefault();
        var CommentID = delete_comment_id;
        $.post('${pageContext.request.contextPath}/comment', {
            method: "delete",
            commentID: CommentID
        }, function () {
            toast("已删除评论");
            $('#comment_' + CommentID).remove();
        }).fail(function () {
            toast("操作异常，请重试");
        })
    });

    // 历史记录模态框 全局变量
    var historyTitle;
    var historyContent;

    // 历史记录模态框加载后的渲染函数
    function modal_render(trigger) {
        var Sequence = trigger.data('sequence');
        $.get('${pageContext.request.contextPath}/history', {
            book_id:<%=request.getAttribute("bookID")%>,
            sequence: Sequence
        }, function (responseText) { // 将历史记录渲染到模态框
            var history = JSON.parse(responseText);
            console.log(history);
            historyTitle = "第 " + Sequence + " 章 历史记录";
            historyContent = " <table class=\"bordered\">\n" +
                "        <thead>\n" +
                "          <tr>\n" +
                "              <th>提交记录</th>\n" +
                "              <th>编辑者</th>\n" +
                "              <th>时间</th>\n" +
                "              <th>提交说明</th>\n" +
                "          </tr>\n" +
                "        </thead>\n" +
                "        <tbody>";
            for (var i = 0; i < history.length; i++) {
                var cur = history[i];
                historyContent += "<tr>" +
                    "<td>" + "<a href=\"old?id=" + cur.ID + "\">" + cur.name + "</a></td>" +
                    "<td>" + "<a href= \"home?user=" + cur.editorUsername + "\">" + cur.editorNickname + "</a></td>" +
                    "<td>" + cur.modifiedTime + "</td>" +
                    "<td>" + cur.description + "</td>";
            }
            historyContent += "   </tbody>\n" +
                "      </table>";
            $('#history_title').html(historyTitle);
            $('#history_content').html(historyContent);
        }).fail(function () {
            toast("操作异常，请重试");
        })
    }

</script>
</body>
</html>
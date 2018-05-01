<%@ page import="model.Book" %>
<%@ page import="model.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <%
        Book[] books = (Book[]) request.getAttribute("books");
        User[] followees = (User[]) request.getAttribute("followees");
        User[] followers = (User[]) request.getAttribute("followers");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d  HH:mm");
    %>
    <title><%=request.getAttribute("nickname")%> - OurBook</title>
    <script>
        $(document).ready(function () {
            $('.modal').modal(); // 模态框

            $('#follow_submit').click(function (event) {
                event.preventDefault();
                $.get('${pageContext.request.contextPath}/follow?followee=<%=request.getAttribute("username")%>&method=' + $('#follow_submit').data("method"), {}, function (respondText) {
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
                });
            });

            $('#message_form').submit(function (evt) {
                evt.preventDefault();
                $.post('/message', {
                    method: 'send',
                    to: '<%=request.getAttribute("username")%>',
                    content: $('#content').val()
                }, function () {
                    toast('发送成功');
                }).fail(function () {
                    toast('发送失败');
                })
            });
        });
    </script>
</head>

<body class="grey lighten-4">
<jsp:include page="nav.jsp"/>
<main>
    <div class="container" style="margin-top: 23px;">

        <div id="message_modal" class="modal" style="min-width:300px">
            <form id="message_form">
                <input id="target_username" type="hidden">
                <div class="modal-content">
                    <div class="input-field">
                        <input id="content" type="text" class="validate" data-length="300">
                        <label for="content">发送内容</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <a class="modal-action modal-close waves-effect waves-green btn-flat">取消</a>
                    <a class="modal-action modal-close waves-effect waves-green btn-flat"
                       onclick="$('#message_form').submit();">
                        提交
                    </a>
                </div>
            </form>
        </div>

        <div class="row" style="width: 900px;margin: 0 auto">
            <div class="card row" style="padding: 20px">
                <img src="<%=request.getAttribute("avatar")%>"
                     style="width:160px;height: 160px;border-radius: 5%;float: left;object-fit: cover;margin-right: 20px">
                <div style="float:left;width:680px">
                    <!--用户的用户名与昵称-->
                    <h4 style="margin: 0;float: left"><%=request.getAttribute("nickname")%>
                    </h4>
                    <h5 class="grey-text" style="margin: 8px 10px;float: left">@<%=request.getAttribute("username")%>
                    </h5>
                    <a href="#" class="pink btn"
                       style="margin:5px 10px; -webkit-appearance:none; -moz-appearance:none;"
                       id="follow_submit"
                       data-method="<%=(boolean)request.getAttribute("isFollowing") ? "remove" : "add" %>">
                        <%=(boolean) request.getAttribute("isFollowing") ? "取消关注" : "关注"%>
                    </a>
                    <a class="btn modal-trigger" href="#message_modal"
                       style="margin:5px 10px; -webkit-appearance:none; -moz-appearance:none;">私信</a>
                </div>
                <!--用户的一句话描述--><%
                String description = (String) request.getAttribute("description");
                if (description != null) {
            %>
                <h6 style="float: left"><%=description%>
                </h6>
                <%
                } else {
                %>
                <h6 class="grey-text" style="float:left;">TA还没有自我介绍</h6>
                <%}%>
            </div>
        </div>
        <div class="row" style="width: 900px">
            <div class="col card" style="width:623px; margin-right:23px;"> <!-- TA写的书 目录-->
                <h5 style="text-align: center">TA写的书</h5>
                <div style="margin-top: 20px">
                    <%
                        if (books.length == 0) {%>
                    <h6 class="grey-text"
                        style="text-align: center; margin-top: 100px;margin-bottom: 15px;width: 600px">
                        TA还没有写任何书 </h6>
                    <%
                        }
                        for (Book book : books) {
                    %>
                    <div style="padding: 20px 10px;display: grid;grid-template-columns: 90px auto;width: 600px;
border-bottom: 1px solid lightgray">
                        <%
                            if (book.getCover() == null || book.getCover().equals("")) {
                        %>
                        <a href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>">
                            <div style="width: 90px;height: 120px;background-color: #0D47A1">
                                <h6 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                                    <%=book.getName()%>
                                </h6>
                            </div>
                        </a>
                        <%
                        } else {
                        %>
                        <a href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>">
                            <img style="width: 90px;height: 120px;object-fit: cover"
                                 src="<%=book.getCover()%>">
                        </a>
                        <%}%>
                        <div style="display: grid;grid-template-rows: 44px 24px 52px">
                            <a style="color: black;margin: 8px 20px;font-size: 16px"
                               href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>">
                                <%=book.getName()%>
                            </a>

                            <p class="grey-text" style="margin: 0 20px">
                                <i class="material-icons">remove_red_eye </i> <%=book.getClicks()%>
                                <i class="material-icons"
                                   style="margin-left: 10px">favorite </i> <%=book.getFavorites()%>
                            </p>
                            <p style="margin: 10px 20px">
                                最后更新： <%=book.getLastModified() != null ? sdf.format(book.getLastModified()) : "暂无"%>
                            </p>
                        </div>
                    </div>
                    <%}%>
                </div>
            </div>

            <div style="width: 253px;float: left">
                <div class="col card" style="width: 253px"> <!--关注列表-->
                    <h5 style="text-align: center">TA的关注</h5>
                    <% if (followees.length == 0) {%>
                    <h6 style="text-align: center;margin-top: 100px;width: 200px;margin-left:16px; margin-bottom: 15px;"
                        class="grey-text">
                        TA还没有关注任何人</h6>
                    <%
                    } else {%>
                    <div style="margin: 10px 0;display: grid;grid-template-columns: 230px;"><%
                        for (User user : followees) {
                    %>
                        <div class="row" style="margin: 15px 5px;">
                            <a href="home?user=<%=user.getUsername()%>"><!--用户头像-->
                                <img src="<%=user.getAvatar()%>"
                                     style="width:40px;height: 40px;border-radius: 5%;float: left;object-fit: cover;margin-right: 10px">
                            </a>
                            <div style="float:left;">
                                <!--用户名与昵称-->
                                <h6 style="margin:0;float: left">
                                    <a href="home?user=<%=user.getUsername()%>">
                                        <%=user.getNickname()%>
                                    </a>
                                </h6>
                                <h6 class="grey-text" style="margin: 0 10px;float: left">@<%=user.getUsername()%>
                                </h6>
                                <p class="grey-text" style="margin: 22px 0 0 0">
                                    <i class="material-icons">perm_identity</i> <%=user.getFollowers()%>
                                </p>
                            </div>
                        </div>
                        <%
                                }
                            }
                        %>
                    </div>
                </div>
                <div class="col card" style="width: 253px"> <!--TA的书迷-->
                    <h5 style="text-align: center">TA的书迷</h5>
                    <% if (followers.length == 0) {%>
                    <h6 style="text-align: center;margin-top: 100px;width: 200px;margin-left:16px;" class="grey-text">
                        你还没有任何书迷</h6>
                    <%
                    } else {%>
                    <div style="margin: 10px auto"><%
                        for (User user : followers) {
                    %>
                        <div class="row" style="margin: 25px 5px;">
                            <a href="home?user=<%=user.getUsername()%>"><!--用户头像-->
                                <img src="<%=user.getAvatar()%>"
                                     style="width:40px;height: 40px;border-radius: 5%; float: left;object-fit: cover;margin-right: 10px">
                            </a>
                            <div style="float:left;">
                                <!--用户名与昵称-->
                                <h6 style="margin:0;float: left">
                                    <a href="home?user=<%=user.getUsername()%>">
                                        <%=user.getNickname()%>
                                    </a>
                                </h6>
                                <h6 class="grey-text" style="margin: 0 10px;float: left">@<%=user.getUsername()%>
                                </h6>
                                <p class="grey-text" style="margin: 22px 0 0 0">
                                    <i class="material-icons">perm_identity</i> <%=user.getFollowers()%>
                                </p>
                            </div>
                        </div>
                        <%}%>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="footer.html" %>
</body>
</html>

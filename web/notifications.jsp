<%@ page import="model.Message" %>
<%@ page import="model.Notification" %>
<%@ page import="model.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Radiance
  Date: 4/27/18
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("username") == null)
        response.sendRedirect("/login");
    SimpleDateFormat sdf = new SimpleDateFormat("M.d  HH:mm");
    Notification[] unread = (Notification[]) request.getAttribute("unread");
    Notification[] read = (Notification[]) request.getAttribute("read");
    Map<User, Message[]> messageMap = (Map<User, Message[]>) request.getAttribute("messages");
%>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>通知 - OurBook</title>
    <style>
        .contained-area {
            padding: 0 !important;
        }

        .collapsible {
            margin: 0;
            border: 0;
            box-shadow: 0 0 0 !important;
        }
    </style>
    <script>
        $(document).ready(function () {
            var read_badge = $('#read_badge');
            var unread_badge = $('#unread_badge');
            var message_badge = $('#message_badge');
            $('.modal').modal();
            $('.collapsible').collapsible();

            $('.unread').click(function () {
                var ID = $(this).data("id");
                $.get('/notification', {
                    method: 'read',
                    ID: ID
                });
            });

            $('.unread_messages').click(function () {
                var user = $(this).data("user");
                $.get('/message', {
                    method: 'read',
                    from: user
                });
                message_badge.html(parseInt(message_badge.html()) - 1);
                $('.badge', this).remove();
            });

            $('#clear_read').click(function () {
                $.get('/notification', {
                    method: 'clearRead'
                }, function () {
                    $('#no_read').show();
                    $('#read').hide();
                    read_badge.html('0');
                }).fail(function () {
                    toast('清空失败');
                });
            });

            $('.delete_unread').click(function () {
                var ID = $(this).data('id');
                $.get('/notification', {
                    method: 'delete',
                    ID: ID
                }, function () {
                    $('.li_' + ID).remove();
                    unread_badge.html(parseInt(unread_badge.html()) - 1);
                    if (parseInt(unread_badge.html()) === 0) {
                        $('#no_unread').show();
                    }
                });
            });

            $('.delete_read').click(function () {
                var ID = $(this).data('id');
                $.get('/notification', {
                    method: 'delete',
                    ID: ID
                }, function () {
                    $('.li_' + ID).remove();
                    read_badge.html(parseInt(read_badge.html()) - 1);
                    if (parseInt(read_badge.html()) === 0) {
                        $('#clear_read').hide();
                        $('#no_read').show();
                    }
                });
            });

            $('.clear_messages').click(function () {
                var user = $(this).data('user');
                $.get('/message', {
                    method: 'clear',
                    target: user
                }, function () {
                    $('.li_' + user).remove();
                }).fail(function () {
                    toast('清除失败');
                });
            });

            $('.modal-trigger').click(function () {
                $('#target_username').val($(this).data('user'));
            });

            $('#message_form').submit(function (evt) {
                evt.preventDefault();
                $.post('/message', {
                    method: 'send',
                    to: $('#target_username').val(),
                    content: $('#content').val()
                }, function () {
                    toast('发送成功');
                }).fail(function () {
                    toast('发送失败');
                });
            });
        });
    </script>
</head>
<body>
<jsp:include page="nav.jsp"/>
<main>
    <div class="row card" style="width: 900px;margin: 20px auto">
        <div class="col s12">
            <ul class="tabs">
                <li class="tab col s4"><a class="active" href="#area1">未读通知
                    <span class="badge" id="unread_badge" style="line-height: 48px">
                    <%=unread.length%>
                </span></a></li>
                <li class="tab col s4"><a href="#area2">已读通知
                    <span class="badge" id="read_badge" style="line-height: 48px">
                    <%=read.length%>
                </span></a></li>
                <li class="tab col s4"><a href="#area3">私信
                    <span class="badge" id="message_badge" style="line-height: 48px">
                    <%=session.getAttribute("unreadMessages")%>
                </span></a></li>
            </ul>
        </div>
        <div id="area1" class="col s12 contained-area">

            <h4 class="center-align grey-text" id="no_unread"
                style="margin: 100px;<%=(unread.length == 0)?"":"display:none"%>">暂无未读通知</h4>
            <ul class="collapsible"><%
                for (Notification notification : unread) {
            %>
                <li class="li_<%=notification.getID()%>">
                    <div class="collapsible-header unread" style="padding:0px 30px"
                         data-id="<%=notification.getID()%>">
                        <h6 style="margin: 15px 20px 0 0"><%=notification.getHeader()%>
                        </h6>
                        <p class="grey-text">
                            <%=sdf.format(notification.getTime())%>
                        </p>
                    </div>
                    <div class="collapsible-body" style="padding: 0 30px;">
                        <p><%=notification.getContent()%>
                        </p>
                        <div style="height: 45px">
                            <a class="btn red right delete_unread" data-id="<%=notification.getID()%>">删除</a>
                        </div>
                    </div>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
        <div id="area2" class="col s12 contained-area">
            <h4 class="center-align grey-text" id="no_read"
                style="margin: 100px;<%=(read.length == 0)?"":"display:none"%>">暂无已读通知</h4>
            <ul class="collapsible <%=(read.length == 0)?"hide":""%>" id="read"><%
                for (Notification notification : read) {
            %>
                <li class="li_<%=notification.getID()%>">
                    <div class="collapsible-header" style="padding:0 30px;" data-id="<%=notification.getID()%>">
                        <h6 style="margin: 15px 20px 0 0"><%=notification.getHeader()%>
                        </h6>
                        <p class="grey-text">
                            <%=sdf.format(notification.getTime())%>
                        </p>
                    </div>
                    <div class="collapsible-body" style="padding: 0 30px;">
                        <p><%=notification.getContent()%>
                        </p>
                        <div style="height: 45px">
                            <a class="btn red right delete_read" data-id="<%=notification.getID()%>">删除</a>
                        </div>
                    </div>
                </li>
                <%
                    }
                %>
                <li id="clear_read">
                    <div class="collapsible-header red waves-effect waves-light"
                         style="border: 0;padding: 10px !important;">
                        <h6 style="text-align: center;width: 100%;cursor: pointer; margin: 0;" class="white-text">
                            清空已读通知
                        </h6>
                    </div>
                </li>
            </ul>
        </div>
        <div id="area3" class="col s12 contained-area">
            <%
                if (messageMap.keySet().size() == 0) {%>
            <h4 class="center-align grey-text" style="margin: 100px">暂无私信记录</h4>
            <%
            } else {%>
            <ul class="collapsible"><%
                for (User user : messageMap.keySet()) {
                    int unreadNum = 0;
                    for (Message message : messageMap.get(user)) if (!message.isRead()) unreadNum++;
            %>
                <li class="li_<%=user.getUsername()%>">
                    <div class="collapsible-header <%=unreadNum!=0?"unread_messages":""%>" style="padding:10px 30px"
                         data-user="<%=user.getUsername()%>">
                        <img src="<%=user.getAvatar()%>"
                             style="height: 52px; width: 52px;display: inline-block;margin-right: 20px;border-radius: 5%">
                        <h5 style="margin: 10px 20px 0 0"><%=user.getNickname()%>
                        </h5>
                        <%if (unreadNum > 0) {%>
                        <span class="new badge" style="margin: 15px 0!important;">
                            <%=unreadNum%></span><%}%>
                    </div>
                    <div class="collapsible-body" style="padding: 5px 30px 10px;">
                        <%for (Message message : messageMap.get(user)) {%>
                        <p class="grey-text" style="display: inline-block; margin:10px 20px 5px 0">
                            <%=sdf.format(message.getTime())%>
                        </p>
                        <p style="display: inline-block; margin:20px 20px 10px 0">
                            <%=message.getFromNickname()%>：
                        </p>
                        <p style="margin: 0 100px" <%=message.isRead() ? "class='grey-text'" : ""%>>
                            <%=message.getContent()%>
                        </p>
                        <%}%>
                        <div style="height: 36px;margin-top: 20px">
                            <a class="btn modal-trigger" href="#message_modal"
                               data-user="<%=user.getUsername()%>">对话</a>
                            <a class="btn red right clear_messages" data-user="<%=user.getUsername()%>">删除</a>
                        </div>
                    </div>
                </li>
                <%
                    }
                %>
            </ul>
            <%}%>
        </div>
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
    </div>
</main>
<%@ include file="footer.html" %>
</body>
</html>

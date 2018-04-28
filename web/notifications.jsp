<%@ page import="model.Notification" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.Message" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.User" %><%--
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
    SimpleDateFormat sdf = new SimpleDateFormat("M-dd  HH:mm");
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
            $('.modal').modal();
            $('.collapsible').collapsible();

            $('.unread').click(function () {
                var ID = $(this).data("id");
                $.get('/notification', {
                    method: 'read',
                    ID: ID
                })
            });

            $('.unread_messages').click(function () {
                var user = $(this).data("user");
                $.get('/message', {
                    method: 'read',
                    from: user
                })
            });

            $('.delete').click(function () {
                var ID = $(this).data('id');
                $.get('/notification', {
                    method: 'delete',
                    ID: ID
                }, function () {
                    $('.li_' + ID).remove();
                })
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
                })
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
                })
            });
        });
    </script>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="row card" style="width: 900px;margin: 20px auto">
    <div class="col s12">
        <ul class="tabs">
            <li class="tab col s4"><a class="active" href="#area1">未读通知<span class="badge" style="line-height: 48px">
                    <%=unread.length%>
                </span></a></li>
            <li class="tab col s4"><a href="#area2">已读通知<span class="badge" style="line-height: 48px">
                    <%=read.length%>
                </span></a></li>
            <li class="tab col s4"><a href="#area3">私信</a></li>
        </ul>
    </div>
    <div id="area1" class="col s12 contained-area">
        <%
            if (unread.length == 0) {%>
        <h4 class="center-align grey-text" style="margin: 100px">没有未读通知</h4>
        <%
        } else {%>
        <ul class="collapsible"><%
            for (Notification notification : unread) {
        %>
            <li class="li_<%=notification.getID()%>">
                <div class="collapsible-header unread" style="padding:10px 30px" data-id="<%=notification.getID()%>">
                    <h5 style="margin: 10px 20px 0 0"><%=notification.getHeader()%>
                    </h5>
                    <p class="grey-text">
                        <%=sdf.format(notification.getTime())%>
                    </p>
                </div>
                <div class="collapsible-body">
                    <p><%=notification.getContent()%>
                    </p>
                    <div style="height: 36px">
                        <a class="btn red right delete" data-id="<%=notification.getID()%>">删除通知</a>
                    </div>
                </div>
            </li>
            <%
                }
            %>
        </ul>
        <%}%>
    </div>
    <div id="area2" class="col s12 contained-area">
        <%
            if (read.length == 0) {%>
        <h4 class="center-align grey-text" style="margin: 100px">没有已读通知</h4>
        <%
        } else {%>
        <ul class="collapsible"><%
            for (Notification notification : read) {
        %>
            <li class="li_<%=notification.getID()%>">
                <div class="collapsible-header" style="padding:10px 30px" data-id="<%=notification.getID()%>">
                    <h5 style="margin: 10px 20px 0 0"><%=notification.getHeader()%>
                    </h5>
                    <p class="grey-text">
                        <%=sdf.format(notification.getTime())%>
                    </p>
                </div>
                <div class="collapsible-body">
                    <p><%=notification.getContent()%>
                    </p>
                    <div style="height: 36px">
                        <a class="btn red right delete" data-id="<%=notification.getID()%>">删除通知</a>
                    </div>
                </div>
            </li>
            <%
                }
            %>
        </ul>
        <%}%>
    </div>
    <div id="area3" class="col s12 contained-area">
        <%
            if (messageMap.keySet().size() == 0) {%>
        <h4 class="center-align grey-text" style="margin: 100px">没有私信记录</h4>
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
                    <span class="new badge" style="margin: 15px 0!important;"><%=unreadNum%></span>
                </div>
                <div class="collapsible-body">
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
                           data-user="<%=user.getUsername()%>">发送私信</a>
                        <a class="btn red right clear_messages" data-user="<%=user.getUsername()%>">清除记录</a>
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
</body>
</html>

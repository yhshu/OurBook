<%@ page import="model.Notification" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Radiance
  Date: 4/27/18
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");%>
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
            $('.collapsible').collapsible();
            $('.unread').click(function () {
                var ID = $(this).data("id");
                $.get('/notification', {
                    method: 'read',
                    ID: ID
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
        });
    </script>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="row card" style="width: 900px;margin: 20px auto">
    <div class="col s12">
        <ul class="tabs">
            <li class="tab col s4"><a class="active" href="#area1">未读通知</a></li>
            <li class="tab col s4"><a href="#area2">已读通知</a></li>
            <li class="tab col s4"><a href="#area3">私信</a></li>
        </ul>
    </div>
    <div id="area1" class="col s12 contained-area">
        <%
            Notification[] unread = (Notification[]) request.getAttribute("unread");
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
                    <p><%=notification.getMessage()%>
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
            Notification[] read = (Notification[]) request.getAttribute("read");
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
                    <p><%=notification.getMessage()%>
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
    <div id="area3" class="col s12 contained-area">私信
    </div>
</div>
</body>
</html>

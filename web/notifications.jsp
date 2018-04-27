<%@ page import="model.Notification" %><%--
  Created by IntelliJ IDEA.
  User: Radiance
  Date: 4/27/18
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>通知 - OurBook</title>
    <style>
        .contained-area {
            max-height: 75%;
            overflow-y: scroll;
            padding: 0 !important;
        }
        .collapsible{
            margin: 0;
            border: 0;
        }
    </style>
    <script>
        $(document).ready(function () {
            $('.collapsible').collapsible();
        });
    </script>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="row card" style="width: 900px;margin: 20px auto">
    <div class="col s12">
        <ul class="tabs">
            <li class="tab col s4"><a class="active" href="#test1">未读通知</a></li>
            <li class="tab col s4"><a href="#test2">已读通知</a></li>
            <li class="tab col s4"><a href="#test3">私信</a></li>
        </ul>
    </div>
    <div id="test1" class="col s12 contained-area">
        <ul class="collapsible">
            <%for (Notification notification : (Notification[]) request.getAttribute("unread")) {%>
            <li>
                <div class="collapsible-header"><%=notification.getHeader()%>
                </div>
                <div class="collapsible-body"><span><%=notification.getMessage()%></span></div>
            </li>
            <%}%>
        </ul>
    </div>
    <div id="test2" class="col s12 contained-area">
        <ul class="collapsible">
            <%for (Notification notification : (Notification[]) request.getAttribute("read")) {%>
            <li>
                <div class="collapsible-header"><%=notification.getHeader()%>
                </div>
                <div class="collapsible-body"><span><%=notification.getMessage()%></span></div>
            </li>
            <%}%>
        </ul>
    </div>
    <div id="test3" class="col s12 contained-area">私信</div>
</div>
</body>
</html>

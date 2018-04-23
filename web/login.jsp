<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title>登录 - OurBook</title>
</head>
<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null)
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                if (session.getAttribute("username") != null) {
                    response.sendRedirect("/index");
                    System.out.println("login.jsp: 自动登录成功，跳转到个人主页");
                }
                break;
            }
        }
%>
<body class="grey lighten-4">
<div class="row">
    <div class="card white" style="margin: 30px 31.5%; padding-bottom: 152px; padding-left: 16px;padding-right: 16px;">
        <div class="card-content black-text">
            <p class="card-title">登录到 OurBook</p>
            <br>
            <form action="${pageContext.request.contextPath}/UserServlet" method="post"
                  onsubmit="return check_input();" id="login">
                <input type="hidden" name="method" value="login"/>
                <div class="row">
                    <div class="input-field s12">
                        <input id="username" name="username" type="text" class="validate">
                        <label for="username">帐号</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field s12">
                        <input id="password" name="password" type="password" class="validate">
                        <label for="password">密码</label>
                    </div>
                </div>
                <br>
                <a class="black-text">新用户？</a><a href="/register">注册</a>
                <button type="submit" onclick="$('login').submit()"
                        class="waves-effect waves-light btn blue right">登 录
                </button>
            </form>
            <script>
                function check_input() {
                    if (document.getElementById('username').value === "" || document.getElementById('password').value === "") {
                        Materialize.toast('请输入用户名密码', 2000, 'rounded');
                        return false;
                    }
                    else return true;
                }

                <%
                if(request.getAttribute("message") != null && request.getAttribute("message").equals("login failed")){%>
                Materialize.toast('用户名或密码错误', 2000, 'rounded');
                <%}%>
            </script>
        </div>
    </div>
</div>
</body>
</html>
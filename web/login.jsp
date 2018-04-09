<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title>用户登录 - OurBook</title>
</head>

<body class="grey lighten-4">
<div class="row">
    <div class="card white" style="margin: 30px 31.5%; padding-bottom: 152px; padding-left: 16px;padding-right: 16px;">
        <div class="card-content black-text">
            <p class="card-title">登录到 OurBook</p>
            <br>
            <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
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
                <a class="black-text">新用户？</a><a href="register.jsp">注册</a>
                <input type="submit" class="waves-effect waves-light btn blue right" value="登录"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="service.UserService" %>
<%@ page import="service.impl.UserServiceImpl" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title>用户注册 - OurBook</title>
    <%
        UserService userService = new UserServiceImpl();
        User user = userService.find(request.getParameter("username"));
    %>
</head>
<%
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
        if (cookie.getName().equals("username")) {
            if (session.getAttribute("username") != null) {
                response.sendRedirect("/home");
                System.out.println("register.jsp: 自动登录成功，跳转到个人主页");
            }
            break;
        }
    }
%>
<body>
<nav> <!-- 顶部栏 -->
    <div class="nav-wrapper blue">
        <a href="home" class="brand-logo"><i class="material-icons">book</i>OurBook</a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li><a href="login.jsp">登录</a></li>
        </ul>
    </div>
</nav>

<div class="container row" style="height: 390px; margin-top: 20px"><!-- 页面中部 -->
    <div class="col s7">
        <h3>为写作团队而生</h3>
        <h5>OurBook 是一个受您工作方式启发的创作社区，帮助您的团队写作、合作与在线出版。</h5>
    </div>
    <div class="col s5">
        <form action="${pageContext.request.contextPath}/UserServlet" method="post" onsubmit="return check()">
            <input type="hidden" name="method" value="register"/>

            <div class="input-field col s12">
                <input id="username" name="username" type="text" class="validate">
                <label for="username">用户名</label>
            </div>
            <div class="input-field col s12">
                <input id="nickname" name="nickname" type="text" class="validate">
                <label for="nickname">昵称</label>
            </div>
            <div class="input-field col s12">
                <input id="password" name="password" type="password" class="validate">
                <label for="password">密码</label>
            </div>
            <div class="input-field col s12">
                <input id="password_confirm" type="password" class="validate">
                <label for="password_confirm">确认密码</label>
            </div>
            <br><br><br><br><br><br><br><br>
            <input type="submit" class="blue btn" id="submit" value="加入 OurBook"/>
        </form>

        <script>/*
          function checkusername() {
              var u = document.getElementById('username').value;
              if(k = 0){
                  M.toast({html: '该用户名已被注册', classes: 'rounded'});
                  return true;
              }

              else if(k = 1) {
                  M.toast({html: '该用户名已被注册', classes: 'rounded'});
                  return false;
              }
              }
          }*/
        </script>
        <script>
            function check() {
                var p1 = document.getElementById('password').value;
                var p2 = document.getElementById('password_confirm').value;
                if (p1 === "" || p2 === "") {
                    M.toast({html: '请输入密码', classes: 'rounded'});
                    return false;
                }
                if (p1 !== p2) {
                    M.toast({html: '请核对密码输入', classes: 'rounded'});
                    return false;
                } else
                    return true;
            }

        </script>
    </div>
</div>

<footer class="page-footer blue">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
                <h5 class="white-text">联系我们</h5>
                <p class="grey-text text-lighten-4">OurBook.com</p>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            Copyright © 2018 OurBook
        </div>
    </div>
</footer>
</body>
</html>
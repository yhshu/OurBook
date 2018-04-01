<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <!--文档 http://www.materialscss.com -->
    <link rel="stylesheet" href="css/materialize.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/myCSS.css">
    <!--Import jQuery before materialize.js-->
    <script src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="js/materialize.js"></script>
</head>

<body>
<nav> <!-- 顶部栏 -->
    <div class="nav-wrapper blue">
        <a href="#" class="brand-logo">Logo</a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li><a href="">注册</a></li>
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
        <div class="input-field col s12">
            <input id="username" type="email" class="validate">
            <label for="username">用户名</label>
        </div>
        <div class="input-field col s12">
            <input id="password" type="password" class="validate">
            <label for="password">密码</label>
        </div>
        <br><br><br><br><br><br><br><br>
        <button class="blue btn">加入 OurBook</button>
    </div>
</div>

<footer class="page-footer blue">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
                <h5 class="white-text">Footer Content</h5>
                <p class="grey-text text-lighten-4">You can use rows and columns here to organize your footer
                    content.</p>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            Copyright © 2018 LeetCode
        </div>
    </div>
</footer>
</body>
</html>
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
    <title>创建书籍 - OurBook</title>
</head>

<body>

<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
    <li><a href="#!" class="blue-text">我的主页</a></li>
    <li class="divider"></li>
    <li><a href="#!" class="blue-text">注销</a></li>
</ul>
<nav> <!-- 顶部栏 -->
    <div class="nav-wrapper blue">
        <a href="#!" class="brand-logo">Logo</a>
        <ul class="right hide-on-med-and-down">
            <li><a href="">Components</a></li>
            <!-- 右上角下拉列表 -->
            <li><a class="dropdown-trigger" data-target="dropdown1">Dropdown<i class="material-icons right">arrow_drop_down</i></a>
            </li>
        </ul>
    </div>
    <script>$(".dropdown-trigger").dropdown();</script>
</nav>
<div class="container" style="margin-top: 20px">
    <div class="col s5">
        <form action="${pageContext.request.contextPath}/AddBookServlet" method="post">
            <div class="input-field col s12">
                <input id="bookname" type="text" class="validate"/>
                <label for="bookname">书名</label>
            </div>
            <div class="input-field col s12">
                <input id="bookDescription" type="text" class="materialize-textarea"/>
                <label for="bookDescription">简介（可选）</label>
            </div>
            <input type="submit" class="blue btn" value="创建"/>
        </form>

    </div>
</div>
</body>
</html>
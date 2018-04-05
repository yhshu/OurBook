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
    <title>用户登录 - OurBook</title>
</head>

<body class="grey lighten-4">
<div class="row">
    <div class="card white" style="margin: 30px 31.5%; padding-bottom: 152px; padding-left: 16px;padding-right: 16px;">
        <div class="card-content black-text">
            <p class="card-title">登录到 OurBook</p>
            <br>

            <div class="row">
                <div class="input-field s12">
                    <input id="id" type="text" class="validate">
                    <label for="id">帐号</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field s12">
                    <input id="password" type="password" class="validate">
                    <label for="password">密码</label>
                </div>
            </div>
            <br>
            <a class="black-text">新用户？</a><a href="">注册</a>
            <a href="" class="waves-effect waves-light btn blue right">登录</a>
        </div>
    </div>
</div>
</body>
</html>
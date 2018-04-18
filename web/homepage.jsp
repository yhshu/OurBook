<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <title>个人主页 - OurBook</title>
</head>

<body>
<%@ include file="nav.jsp" %>
<div class="container">
    <div class="row">
        <h4 id="homepage_username"></h4><!--用户的用户名与昵称-->
        <h6 id="homepage_userDescription"></h6><!--用户的一句话描述-->
        <a class="modal-trigger" data-target="personalInfo" style="display: inline;"><i
                class="material-icons">settings</i></a>
        <!--TODO 修改个人信息 模态框 的后端逻辑-->
        <div id="personalInfo" class="modal"> <!--修改个人信息 模态框-->
            <div class="modal-content">
                <h4>修改个人信息</h4>
                <label for="new_nickname">昵称</label>
                <input type="text" name="new_nickname" id="new_nickname"/>
                <label for="new_description">一句话简介</label>
                <input type="text" name="new_description" id="new_description"/>
            </div>
            <div class="modal-footer">
                <button class="modal-action modal-close waves-effect waves-green btn-flat">取消
                </button>
                <button class="modal-action modal-close waves-effect waves-green btn-flat" id="submit_personal_info">
                    提交
                </button>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col s6"> <!--文章、书目或动态-->
            <h5 style=" margin-right: 30px;">我写的书</h5>
            <div> <!-- 我写的书 目录-->
                <%

                %>
            </div>
        </div>

        <div class="col s2"> <!--关注列表-->
            <h5>关注列表</h5>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        // 通过 cookie 修改 username_display
        var nickname = getCookie('nickname');
        var username = getCookie('username');
        if (nickname !== "" && username !== "")
            document.getElementById("homepage_username").innerText = nickname + " (" + username + ")";

        // 模态框
        $(document).ready(function () {
            $('.modal').modal();
        });
    });
</script>
</body>
</html>

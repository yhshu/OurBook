<%@ page import="model.Book" %>
<%@ page import="model.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <%
        Book[] books = (Book[]) request.getAttribute("books");
        Book[] favorites = (Book[]) request.getAttribute("favorites");
        User[] followees = (User[]) request.getAttribute("followees");
        User[] followers = (User[]) request.getAttribute("followers");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.M.d");
    %>
    <title><%=request.getAttribute("nickname")%> - OurBook</title>
    <script>
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#preview')
                        .attr('src', e.target.result);
                };
                reader.readAsDataURL(input.files[0]);
            }
        }

        function check_input() {
            if (!$.trim($('#new_nickname').val()).length) {
                toast('昵称不能为空');
                return false;
            }
            return true;
        }
    </script>
</head>

<body class="grey lighten-4">
<jsp:include page="nav.jsp"/>
<main>
    <div class="container row" style="margin-top: 23px;">
        <div class="row" style="width: 1000px;margin: 0 auto">
            <div class="card row" style="padding: 20px">
                <img src="<%=request.getAttribute("avatar")%>"
                     style="width:160px;height: 160px;border-radius: 5%;float: left;object-fit: cover;margin-right: 20px">
                <div style="float:left;width:780px">
                    <!--用户的用户名与昵称-->
                    <h4 style="margin: 0;float: left"><%=request.getAttribute("nickname")%>
                    </h4>
                    <h5 class="grey-text" style="margin: 8px 10px;float: left">@<%=request.getAttribute("username")%>
                    </h5>
                    <a class="modal-trigger waves-effect waves-light right"
                       data-target="personalInfo" style="display: inline; font-size: 32px">
                        <i class="material-icons small grey-text">settings</i></a>
                </div>
                <h6 style="float: left"><!--用户的一句话描述--><%
                    String description = (String) request.getAttribute("description");
                    if (description != null) {
                %>
                    <%=description%>
                    <%
                    } else {
                        description = "";
                    %>
                    <h6 class="grey-text">用一句话来描述自己吧</h6>
                    <%}%>
                </h6>

                <div id="personalInfo" class="modal" style="min-width:300px"> <!--修改个人信息 模态框-->
                    <form action="${pageContext.request.contextPath}/modifyUser" method="post"
                          enctype="multipart/form-data" id="info_form">
                        <div class="modal-content">
                            <div style="height:60px">
                                <h4 class="left">修改个人信息</h4>
                                <a class="modal-trigger grey-text right" href="#change_password">
                                    修改密码</a>
                            </div>
                            <label for="new_nickname">昵称</label>
                            <input type="text" name="new_nickname" id="new_nickname"
                                   value="<%=session.getAttribute("nickname")%>"/>
                            <label for="new_description">一句话简介</label>
                            <input type="text" name="new_description" id="new_description" value="<%=description%>"/>
                            <input id="avatar" type='file' name="avatar" onchange="readURL(this);"
                                   style="display: none"/>
                            <div style="margin: 20px auto; width: 300px;height: 128px">
                                <img id="preview" src="<%=session.getAttribute("avatar")%>" alt="your image"
                                     style="height: 160px;width:160px;object-fit: cover;
                             border-radius: 5%;float:left"/>
                                <label for="avatar" class="blue btn" style="float: right;margin-top: 62px">上传头像</label>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <a class="modal-action modal-close waves-effect waves-green btn-flat">取消
                            </a>
                            <a class="waves-effect waves-green btn-flat" id="info_submit">
                                提交
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="row" style="width: 1000px">
            <div class="col card" style="width:623px; margin-right:23px;">
                <h5 style="text-align: center">我写的书</h5>
                <div style="margin-top: 20px"> <!-- 我写的书 目录-->
                    <%
                        if (books.length == 0) {%>
                    <h5 class="grey-text"
                        style="text-align: center; margin-top: 100px;margin-bottom: 100px;width: 600px">
                        你还没有写任何书 </h5>
                    <%
                        }
                        for (Book book : books) {
                    %>
                    <div style="padding: 20px 10px;display: grid;grid-template-columns: 90px auto;width: 600px;
border-bottom: 1px solid lightgray">
                        <%
                            if (book.getCover() == null || book.getCover().equals("")) {
                        %>
                        <a href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>">
                            <div style="width: 90px;height: 120px;background-color: #0D47A1">
                                <h6 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                                    <%=book.getName()%>
                                </h6>
                            </div>
                        </a>
                        <%
                        } else {
                        %>
                        <a href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>">
                            <img style="width: 90px;height: 120px;object-fit: cover"
                                 src="<%=book.getCover()%>">
                        </a>
                        <%}%>
                        <div style="display: grid;grid-template-rows: 44px 24px 52px">
                            <a style="color: black;margin: 8px 24px;font-size: 16px"
                               href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>">
                                <%=book.getName()%>
                            </a>
                            <p class="grey-text" style="margin: 0 20px">
                                <i class="material-icons">remove_red_eye </i> <%=book.getClicks()%>
                                <i class="material-icons"
                                   style="margin-left: 10px">favorite </i> <%=book.getFavorites()%>
                            </p>
                            <p style="margin: 10px 20px">
                                最后更新： <%=book.getLastModified() != null ? sdf.format(book.getLastModified()) : "暂无"%>
                            </p>
                        </div>
                    </div>
                    <%}%>
                </div>
            </div>
            <div style="width: 353px;float: left"><!--右侧 收藏、关注与书迷-->
                <div class="col card" style="width: 353px;padding-bottom: 10px"> <!--收藏列表-->
                    <h5 style="text-align: center">我的收藏</h5>
                    <% if (favorites.length == 0) {%>
                    <h5 style="text-align: center;margin: 50px 0" class="grey-text">
                        你还没有收藏任何书</h5>
                    <%
                    } else {
                        for (Book book : favorites) {
                    %>
                    <div style="margin: 10px;display: grid;grid-template-columns:70px auto 24px;"
                         class="book_<%=book.getID()%>">
                        <a href="${pageContext.request.contextPath}/book?id=<%=book.getID()%>">
                            <%
                                if (book.getCover() == null || book.getCover().equals("")) {
                            %>
                            <div style="width: 60px;height: 80px;background-color: #0D47A1" class="left">
                                <p style="color: white;text-align: center">
                                    <%=book.getName()%>
                                </p>
                            </div>
                            <%
                            } else {
                            %>
                            <img style="width: 60px;height: 80px;object-fit: cover"
                                 src="<%=book.getCover()%>">
                            <%}%>
                        </a>
                        <div>
                            <a href="book?id=<%=book.getID()%>" class="black-text" style="margin: 5px 0">
                                <%=book.getName()%>
                            </a>
                            <p class="grey-text" style="margin: 5px 0">
                                <i class="material-icons">remove_red_eye </i> <%=book.getClicks()%>
                                <i class="material-icons"
                                   style="margin-left: 10px">favorite </i> <%=book.getFavorites()%>
                            </p>
                            <p class="grey-text" style="margin: 5px 0">
                                最后更新：<%=book.getLastModified() != null ? sdf.format(book.getLastModified()) : "暂无"%>
                            </p>
                        </div>
                        <a href="#" class="remove_favorite pink-text"
                           style="display: inline; -webkit-appearance:none; -moz-appearance:none;font-size: 24px;height: 36px;margin-top: 20px"
                           data-book="<%=book.getID()%>"><i class="material-icons">favorite</i></a>
                    </div>
                    <% }
                    }%>
                </div>
                <div class="col card" style="width: 353px"> <!--我的关注-->
                    <h5 style="text-align: center">我的关注</h5>
                    <% if (followees.length == 0) {%>
                    <h5 style="text-align: center;margin: 50px 0;width: 100%" class="grey-text">
                        你还没有关注任何人</h5>
                    <%
                    } else {
                        for (User user : followees) {%>
                    <div style="margin: 10px;display: grid;grid-template-columns:40px auto 100px;"
                         class="user_<%=user.getUsername()%>">
                        <a href="home?user=<%=user.getUsername()%>"><!--用户头像-->
                            <img src="<%=user.getAvatar()%>"
                                 style="width:40px;height: 40px;border-radius: 5%;float: left;object-fit: cover;margin-right: 5px">
                        </a>
                        <!--用户名与昵称-->
                        <div style="padding: 0 10px">
                            <p style="margin:0;float: left">
                                <a href="home?user=<%=user.getUsername()%>">
                                    <%=user.getNickname()%>
                                </a>
                            </p>
                            <p class="grey-text" style="margin:0;float: right">
                                @<%=user.getUsername()%>
                            </p>
                            <br>
                            <p class="grey-text left" style="margin: 0">
                                <i class="material-icons">perm_identity</i> <%=user.getFollowers()%>
                            </p>
                        </div>
                        <div>
                            <a class="btn blue remove_follow"
                               style="-webkit-appearance:none; -moz-appearance:none; height: 20px;line-height: 20px;display: inline"
                               data-user="<%=user.getUsername()%>">
                                取消关注</a>
                            <a class="btn modal-trigger" href="#message_modal"
                               style="-webkit-appearance:none; -moz-appearance:none; height: 20px;line-height: 20px;display: inline"
                               data-user="<%=user.getUsername()%>">
                                &nbsp;&nbsp;私&nbsp;&nbsp;信&nbsp; &nbsp;</a>
                        </div>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
                <div class="col card" style="width: 353px"> <!--我的书迷-->
                    <h5 style="text-align: center">我的书迷</h5>
                    <% if (followers.length == 0) {%>
                    <h5 style="text-align: center;margin: 50px 0;width: 100%" class="grey-text">
                        你还没有任何书迷</h5>
                    <%
                    } else {%>
                    <div style="margin: 10px auto"><%
                        for (User user : followers) {
                    %>
                        <div style="margin: 10px;display: grid;grid-template-columns:40px auto 100px">
                            <a href="home?user=<%=user.getUsername()%>"><!--用户头像-->
                                <img src="<%=user.getAvatar()%>"
                                     style="width:40px;height: 40px;border-radius: 5%;float: left;object-fit: cover;margin-right: 5px">
                            </a>
                            <!--用户名与昵称-->
                            <div style="padding: 0 10px">
                                <p style="margin:0;float: left">
                                    <a href="home?user=<%=user.getUsername()%>">
                                        <%=user.getNickname()%>
                                    </a>
                                </p>
                                <p class="grey-text" style="margin:0;float: right">
                                    @<%=user.getUsername()%>
                                </p>
                                <br>
                                <p class="grey-text left" style="margin: 0">
                                    <i class="material-icons">perm_identity</i> <%=user.getFollowers()%>
                                </p>
                            </div>
                            <div>
                                <a class="btn modal-trigger" href="#message_modal"
                                   style="-webkit-appearance:none; -moz-appearance:none; height: 20px;line-height: 20px;display: inline"
                                   data-user="<%=user.getUsername()%>">
                                    &nbsp;&nbsp;私&nbsp;&nbsp;信&nbsp; &nbsp;</a>
                            </div>
                        </div>
                        <%}%>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
    </div>
    <div id="message_modal" class="modal" style="min-width:300px"><!--私信发送 模态框-->
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
    <div id="change_password" class="modal" style="min-width:300px"><!--私信发送 模态框-->
        <form id="change_password_form">
            <div class="modal-content">
                <div class="input-field">
                    <input id="original_password" type="password" class="validate" data-length="20">
                    <label for="original_password">原密码</label>
                </div>
                <div class="input-field">
                    <input id="new_password" type="password" class="validate" data-length="20">
                    <label for="new_password">新密码</label>
                </div>
                <div class="input-field">
                    <input id="repeat_new_password" type="password" class="validate" data-length="20">
                    <label for="repeat_new_password">再次输入新密码</label>
                </div>
            </div>
            <div class="modal-footer">
                <a class="modal-action modal-close waves-effect waves-green btn-flat">取消</a>
                <a class="waves-effect waves-green btn-flat" id="change_password_submit">
                    提交
                </a>
            </div>
        </form>
    </div>
</main>
<script>
    $(document).ready(function () {
        $('.modal').modal(); // 模态框
        $('.remove_favorite').click(function (event) {
            var removed_fav = $(this).data('book');
            event.preventDefault();
            $.get('${pageContext.request.contextPath}/favorite?',
                {
                    method: 'remove',
                    book: removed_fav
                }, function () {
                    toast('取消收藏成功');
                    $('.book_' + removed_fav).remove();
                }).fail(function () { // 服务器响应错误信息
                toast("操作异常");
            })
        });

        $('.remove_follow').click(function (event) {
            var removed_user = $(this).data('user');
            event.preventDefault();
            $.get('${pageContext.request.contextPath}/follow',
                {
                    method: 'remove',
                    followee: removed_user
                }, function () {
                    toast('取消关注成功');
                    $('.user_' + removed_user).remove();
                }).fail(function () { // 服务器响应错误信息
                toast("操作异常");
            })
        });

        $('.modal-trigger').click(function () {
            $('#target_username').val($(this).data('user'));
        });

        $('#message_form').submit(function (evt) { // 提交私信内容
            evt.preventDefault();
            if ($('#content').val().length >= 300) {
                toast('超出长度限制');
                return;
            }
            $.post('/message', {
                method: 'send',
                to: $('#target_username').val(),
                content: $('#content').val()
            }, function () {
                toast('发送成功');
                $('#content').val("");
            }).fail(function () {
                toast('发送失败');
            })
        });

        $('#change_password_submit').click(function () {
            var original_password = $('#original_password').val();
            var new_password = $('#new_password').val();
            var repeat_new_password = $('#repeat_new_password').val();
            if (new_password.length > 20) {
                toast('新密码不能多于20位');
                return;
            } else if (new_password.length < 6) {
                toast('新密码不能少于6位');
                return;
            } else if (repeat_new_password !== new_password) {
                toast('两次输入的新密码不一致');
                return;
            } else if (new_password === original_password) {
                toast('新密码与原密码相同');
                return;
            }
            $.post('/UserServlet', {
                method: 'changePassword',
                original: original_password,
                new: new_password
            }, function () {
                toast('修改密码成功');
            }).fail(function (jqXHR) {
                if ((jqXHR.status) === 403) toast("新密码不能多于20位");
                else if ((jqXHR.status) === 400) toast('原密码错误');
                else toast('未知错误');
            });
        });

        $('#info_submit').click(function (evt) {
            if (check_input()) {
                $('#info_form').submit();
            }
        });

        $('#info_form').submit(function (evt) {
            evt.preventDefault();
            var data = new FormData($('#info_form')[0]);
            $.ajax({
                url: '${pageContext.request.contextPath}/modifyUser',
                type: 'POST',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                enctype: 'multipart/form-data',
                data: data,
                success: function (text) {
                    toast("更新个人信息成功");
                    window.location.href = text;
                }
            }).fail(function (jqXHR) {
                if ((jqXHR.status) === 403) toast("头像不能超过2MB");
                else if (jqXHR.status === 415) toast("头像必须是图片");
                else if (jqXHR.status === 400) toast("昵称不能为空");
                else toast('未知错误');
            });
        });

    })
    ;

</script>
<%@ include file="footer.html" %>
</body>
</html>

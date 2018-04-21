<%@ page import="model.Book" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <%
        Book[] books = (Book[]) request.getAttribute("books");
        String[] followees = (String[]) request.getAttribute("followees");
    %>
    <title>个人主页 - OurBook</title>
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

        $(document).ready(function () {
        })
    </script>
</head>

<body class="grey lighten-4">
<%@ include file="nav.jsp" %>
<div class="container" style="margin-top: 23px;">
    <div class="row card" style="padding: 20px;width: 900px">
        <img src="<%=request.getAttribute("avatar")%>"
             style="width:160px;height: 160px;border-radius: 5%;float: left;object-fit: cover;margin-right: 20px">
        <div style="float:left;width:680px">
            <h4 style="margin: 0;float: left"><%=request.getAttribute("nickname")%>
            </h4>
            <h4 class="grey-text" style="margin: 0 10px;float: left">@<%=request.getAttribute("username")%>
            </h4>
            <!--用户的用户名与昵称-->
            <a class="modal-trigger waves-effect waves-light"
               data-target="personalInfo" style="display: inline; font-size: 32px;float: right">
                <i class="material-icons small">settings</i></a>
        </div>
        <h6 style="float: left"><%
            String description = (String) request.getAttribute("description");
            if (description != null) {
        %>
            <%=description%>
            <%
            } else {
            %>
            <h6 class="grey-text">用一句话来描述自己吧</h6>
            <%}%>
        </h6><!--用户的一句话描述-->

        <div id="personalInfo" class="modal" style="min-width:300px"> <!--修改个人信息 模态框-->
            <form action="${pageContext.request.contextPath}/modifyUser" method="post" enctype="multipart/form-data">
                <div class="modal-content">
                    <h4>修改个人信息</h4>
                    <label for="new_nickname">昵称</label>
                    <input type="text" name="new_nickname" id="new_nickname"
                           value="<%=session.getAttribute("nickname")%>"/>
                    <label for="new_description">一句话简介</label>
                    <input type="text" name="new_description" id="new_description"/>
                    <input id="avatar" type='file' name="avatar" onchange="readURL(this);" style="display: none"/>
                    <div style="margin: 20px auto; width: 300px;height: 128px">
                        <img id="preview" src="<%=session.getAttribute("avatar")%>" alt="your image"
                             style="height: 160px;width:160px;object-fit: cover;
                             border-radius: 5%;float:left"/>
                        <label for="avatar" class="blue btn" style="float: right;margin-top: 62px">上传头像</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">取消
                    </a>
                    <button class="modal-action modal-close waves-effect waves-green btn-flat" id="submit_personal_info"
                            onclick="document.getElementById('personalInfo').submit();">
                        提交
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col card" style="width:623px"> <!--文章、书目或动态-->
            <h5 style="text-align: center">我写的书</h5>
            <div style="margin-top: 20px"> <!-- 我写的书 目录-->
                <%
                    if (books.length == 0) {%>
                <h6 class="grey-text" style="text-align: center; margin-top: 100px;width: 600px"> 你还没有写任何书 </h6>
                <%
                    }
                    for (Book book : books) {
                %>
                <div style="padding: 20px 10px;display: grid;grid-template-columns: 90px auto;width: 600px;
border-bottom: 1px solid lightgray">
                    <%
                        if (book.getCover() == null || book.getCover().equals("")) {
                    %>
                    <a href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
                        <div style="width: 90px;height: 120px;background-color: #0D47A1">
                            <h6 style="color: white;display: block;position: relative;top: 30%;text-align: center">
                                <%=book.getName()%>
                            </h6>
                        </div>
                    </a>
                    <%
                    } else {
                    %>
                    <a href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
                        <img style="width: 90px;height: 120px;object-fit: cover"
                             src="<%=book.getCover()%>">
                    </a>
                    <%}%>
                    <div style="display: grid;grid-template-rows: 44px 24px 52px">
                        <a style="color: black;margin: 8px 24px;font-size: 16px"
                           href="${pageContext.request.contextPath}/book.jsp?id=<%=book.getID()%>">
                            <%=book.getName()%>
                        </a>
                    </div>
                </div>
                <%}%>
            </div>
        </div>

        <div class="col" style="margin-right: 12px;">
            <!--不要随便删除它，除非需要改变排版-->
        </div>


        <div class="col card" style="width: 220px"> <!--关注列表-->
            <form action="${pageContext.request.contextPath}/FollowServlet" accept-charset="UTF-8" method="post"
                  id="delfollowee">
                <h5 style="text-align: center">我的关注</h5>
                <% if (followees.length == 0) {%>
                <h6 style="text-align: center;margin-top: 100px;width: 200px" class="grey-text">你还没有关注任何人</h6>
                <%
                } else {%>
                <div style="margin-top: 20px;display: grid;grid-template-columns: 80px 120px;"><%
                    for (String user : followees) {
                %>
                    <input type="hidden" name="method" value="delfollowee">
                    <input type="hidden" name="followee" value="<%=user%>">
                    <a href="home?user=<%=user%>" style="text-align: center;line-height: 31px">
                        <%=user%>
                    </a>
                    <a type="submit" class="btn blue"
                       style="display: inline; -webkit-appearance:none; -moz-appearance:none;
                        height: 21px;line-height: 21px;margin: 5px 10px"
                       onclick="document .getElementById ('delfollowee').submit();">取消关注</a>
                    <%
                            }
                        }
                    %>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('.modal').modal(); // 模态框
    });
</script>
</body>
</html>

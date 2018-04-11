<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
    <li><a id="username_display" class="blue-text">未登录</a></li><!--显示用户名与昵称-->
    <li><a href="homepage.jsp" class="blue-text">我的主页</a></li>
    <li class="divider"></li>
    <li><a href="/LogoutServlet" class="blue-text">退出</a></li>
</ul>
<script>
    // 通过 cookie 修改下拉列表中的 username_display
    $(document).ready(function () {
        var nickname = getCookie('nickname');
        var username = getCookie('username');
        if (nickname !== "" && username !== "")
            document.getElementById("username_display").innerText = nickname + " (" + username + ")";
        // 搜索框点叉，清除框中内容
        var search_type = <%=request.getParameter("searchType")%>;
        $('#search-delete').on('click', function () {
            $('#search').val('');
        });
        $('#nav_search_type').hide();
        $('#search').on('click', function (event) {
            $('#nav_search_type').show();
            event.stopPropagation();
        });
        $(document).on('click', function () {
            $('#nav_search_type').hide();
        });
        if(search_type===null||search_type==='book'){
            $('#search_book').addClass('active');
        } else if(search_type==='chapter'){
            $('#search_chapter').addClass('active');
        } else if(search_type==='user'){
            $('#search_user').addClass('active');
        }
    });
</script>
<nav> <!-- 顶部栏 -->
    <div class="nav-wrapper blue row" style="margin: 0">
        <div class="col s3">
            <a href="homepage.jsp" class="brand-logo"><i class="material-icons">book</i>OurBook</a>
        </div>
        <div class="nav-wrapper col s6" style="padding: 5px">
            <form action="SearchBookServlet">
                <div class="input-field blue lighten-1">
                    <input id="search" type="search" name="keywords" placeholder="搜索"
                           required>
                    <label class="label-icon" for="search"><i class="material-icons"
                                                              style="vertical-align:bottom">search</i></label>
                    <i class="material-icons" id="search-delete">close</i>
                </div>
            </form>
        </div>
        <div class="col s3">
            <ul class="right hide-on-med-and-down">
                <li><a href="">Components</a></li>
                <!-- 右上角下拉列表 -->
                <li><a class="dropdown-trigger" data-target="dropdown1">Dropdown<i class="material-icons right">arrow_drop_down</i></a>
                </li>
            </ul>
        </div>
    </div>
    <script>$(".dropdown-trigger").dropdown();</script>
    <div class="blue" id="nav_search_type">
        <ul class="hide-on-med-and-down" style="position: relative;height: 64px;width: 182px;margin: auto">
            <li id="search_book"><a href="">书籍</a></li>
            <li id="search_chapter"><a href="">章节</a></li>
            <li id="search_user"><a href="">用户</a></li>
        </ul>
    </div>
</nav>
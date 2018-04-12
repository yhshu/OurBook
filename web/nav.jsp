<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
    <li><a id="username_display" class="blue-text">未登录</a></li><!--显示用户名与昵称-->
    <li><a href="homepage.jsp" class="blue-text">我的主页</a></li>
    <li class="divider"></li>
    <li><a href="${pageContext.request.contextPath}/LogoutServlet" class="blue-text">退出</a></li>
</ul>

<script>
    $(document).ready(function () {
        // 通过 cookie 修改下拉列表中的 username_display
        var nickname = getCookie('nickname');
        var username = getCookie('username');
        if (nickname !== "" && username !== "")
            document.getElementById("username_display").innerText = nickname + " (" + username + ")";

        // 搜索框点叉，清除框中内容
        var queryDict = {};
        location.search.substr(1).split("&").forEach(function (item) {
            queryDict[item.split("=")[0]] = item.split("=")[1]
        });
        var search_value = queryDict['keywords'];
        var search_type = queryDict['search_type'];
        $('#search-delete').on('click', function () {
            $('#search').val('');
        });
        if (search_value === undefined) $('#nav_search_type').hide();
        if (search_type === null || search_type === 'book') {
            $('#search_type').val('book');
            $('#search_book_button').addClass('active');
        } else if (search_type === 'article') {
            $('#search_type').val('article');
            $('#search_article_button').addClass('active');
        } else if (search_type === 'user') {
            $('#search_type').val('user');
            $('#search_user_button').addClass('active');
        }
        $('#search_book').attr('href',
            "${pageContext.request.contextPath}/search.jsp?search_type=book&keywords=" + search_value);
        $('#search_article').attr('href',
            "${pageContext.request.contextPath}/search.jsp?search_type=article&keywords=" + search_value);
        $('#search_user').attr('href',
            "${pageContext.request.contextPath}/search.jsp?search_type=user&keywords=" + search_value);
    });
</script>

<nav> <!-- 顶部栏 -->
    <div class="nav-wrapper blue row" style="margin: 0">
        <!-- Logo -->
        <div class="col s3">
            <a href="homepage.jsp" class="brand-logo"><i class="material-icons">book</i>OurBook</a>
        </div>
        <!-- 搜索框 -->
        <div class="nav-wrapper col s6" style="padding: 5px">
            <form id="search_form" action="BookServlet" method="post">
                <div class="input-field blue lighten-1">
                    <input id="search" type="search" name="keywords" placeholder="搜索"
                           required>
                    <label class="label-icon" for="search"><i class="material-icons"
                                                              style="vertical-align:bottom">search</i></label>
                    <i class="material-icons" id="search-delete">close</i>
                </div>
                <input type="hidden" name="method" value="search"/>
                <input type="hidden" id="search_type" name="search_type" value="book">
            </form>
        </div>
        <div class="col s3">
            <ul class="right hide-on-med-and-down">
                <li><a href="">Components</a></li>
                <!-- 右上角下拉列表 -->
                <li><a class="dropdown-trigger" data-target="dropdown1">我<i
                        class="material-icons right">arrow_drop_down</i></a>
                </li>
            </ul>
        </div>
    </div>
    <script>$(".dropdown-trigger").dropdown();</script>
    <div class="blue" id="nav_search_type">
        <ul class="hide-on-med-and-down" style="position: relative;height: 64px;width: 182px;margin: auto">
            <li id="search_book_button"><a id="search_book">书籍</a></li>
            <li id="search_article_button"><a id="search_article">文章</a></li>
            <li id="search_user_button"><a id="search_user">用户</a></li>
        </ul>
    </div>
</nav>
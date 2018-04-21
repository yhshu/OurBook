<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
    <li><a href="${pageContext.request.contextPath}/home" class="blue-text">我的主页</a></li>
    <li class="divider"></li>
    <li><a href="${pageContext.request.contextPath}/LogoutServlet" class="blue-text">退出</a></li>
</ul>

<script>
    $(document).ready(function () {
        var queryDict = {};
        location.search.substr(1).split("&").forEach(function (item) {
            queryDict[item.split("=")[0]] = item.split("=")[1]
        });
        var search_value = queryDict['keywords'];
        var search_type = queryDict['type'];
        $('#search-delete').on('click', function () {
            $('#search').val(''); // 搜索框点叉，清除框中内容
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
        } // 将搜索请求提交给 SearchServlet
        $('#search_book').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value);
        $('#search_article').attr('href',
            "${pageContext.request.contextPath}/search?type=article&keywords=" + search_value);
        $('#search_user').attr('href',
            "${pageContext.request.contextPath}/search?type=user&keywords=" + search_value);

        $('.dropdown-trigger').dropdown({
            hover: true,
            coverTrigger: false
        });
    });
</script>

<nav> <!-- 顶部栏 -->
    <div class="nav-wrapper blue row" style="margin: 0">
        <!-- Logo -->
        <div class="col s3">
            <a href="${pageContext.request.contextPath}/home" class="brand-logo"><i class="material-icons">book</i>OurBook</a>
        </div>
        <!-- 搜索框 -->
        <div class="nav-wrapper col s6" style="padding: 5px">
            <form id="search_form" action="search"> <!--请求 SearchServlet 的服务-->
                <div class="input-field blue lighten-1">
                    <input id="search" type="search" name="keywords" placeholder="搜索" required>
                    <label class="label-icon" for="search"><i class="material-icons" style="vertical-align:bottom">search</i></label>
                    <i class="material-icons" id="search-delete">close</i>
                </div>
                <input type="hidden" id="search_type" name="type" value="book">
            </form>
        </div>
        <div class="col s3">
            <ul class="right hide-on-med-and-down">
                <li><a href="addBook"><i class="material-icons">mode_edit</i></a></li>
                <!-- 右上角下拉列表 -->
                <li style="height: 64px">
                    <a class="dropdown-trigger" data-target="dropdown1" style="height: 64px">
                        <span style="position:relative;bottom:10px;right:10px;margin-left: 10px">
                            <%=session.getAttribute("nickname")%>
                        </span>
                        <img src="<%=session.getAttribute("avatar")%>" style="width: 32px;height:32px;margin-top: 16px;
                    object-fit: cover;border-radius: 5%">
                        <i class="material-icons right">arrow_drop_down</i></a>
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
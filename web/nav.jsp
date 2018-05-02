<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
    <li><a href="${pageContext.request.contextPath}/home" class="blue-text">我的主页</a></li>
    <li><a href="${pageContext.request.contextPath}/notifications" class="blue-text">通知中心
        <%if ((int) session.getAttribute("unreadNotifications") > 0) {%>
        <span class="new badge right" style="margin-top:2px"><%=session.getAttribute("unreadNotifications")%>
                </span><%}%></a></li>
    <li><a href="${pageContext.request.contextPath}/notifications#area3" class="blue-text">我的私信
        <%if ((int) session.getAttribute("unreadMessages") > 0) {%>
        <span class="new badge right" style="margin-top:2px"><%=session.getAttribute("unreadMessages")%>
                </span><%}%></a></li>
    <li class="divider"></li>
    <li><a href="${pageContext.request.contextPath}/logout" class="blue-text">退出</a></li>
</ul>

<ul id="dropdown2" class="dropdown-content dropdown-nested">
    <li><a class=" blue-text" id="last-updated">最后更新</a></li>
    <li><a class="dropdown-button blue-text" data-activates="dropdown3" data-alignment="left" data-hover="hover">
        按点击数&nbsp;<span class="right-triangle blue-text" style="float: right">&#9656;</span></a></li>
    <li><a class="dropdown-button blue-text" data-activates="dropdown4" data-alignment="left" data-hover="hover">
        按收藏数&nbsp;<span class="right-triangle blue-text" style="float: right">&#9656;</span></a></li>
</ul>

<ul id="dropdown3" class="dropdown-content">
    <li><a class="blue-text" id="click-day">一天内</a></li>
    <li><a class="blue-text" id="click-week">一周内</a></li>
    <li><a class="blue-text" id="click-month">一个月内</a></li>
    <li><a class="blue-text" id="click-year">一年内</a></li>
    <li><a class="blue-text" id="click-all">所有时间</a></li>
</ul>

<ul id="dropdown4" class="dropdown-content">
    <li><a class="blue-text" id="fav-day">一天内</a></li>
    <li><a class="blue-text" id="fav-week">一周内</a></li>
    <li><a class="blue-text" id="fav-month">一个月内</a></li>
    <li><a class="blue-text" id="fav-year">一年内</a></li>
    <li><a class="blue-text" id="fav-all">所有时间</a></li>
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
        $('#last-updated').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=last_updated");
        $('#click-day').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=click&range=day");
        $('#click-week').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=click&range=week");
        $('#click-month').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=click&range=month");
        $('#click-year').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=click&range=year");
        $('#click-all').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=click&range=all");
        $('#fav-day').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=fav&range=day");
        $('#fav-week').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=fav&range=week");
        $('#fav-month').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=fav&range=month");
        $('#fav-year').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=fav&range=year");
        $('#fav-all').attr('href',
            "${pageContext.request.contextPath}/search?type=book&keywords=" + search_value + "&sort=fav&range=all");
    });
</script>

<nav> <!-- 顶部栏 -->
    <div class="nav-wrapper blue row" style="margin: 0">
        <!-- Logo -->
        <div class="col s3">
            <a href="${pageContext.request.contextPath}/index" class="brand-logo">
                <i class="material-icons">book</i>OurBook</a>
        </div>
        <!-- 搜索框 -->
        <div class="nav-wrapper col s6" style="padding: 5px">
            <form id="search_form" action="search"> <!--请求 SearchServlet 的服务-->
                <div class="input-field blue lighten-1">
                    <input id="search" type="search" name="keywords" placeholder="搜索" required>
                    <label class="label-icon" for="search"><i class="material-icons"
                                                              style="vertical-align:bottom">search</i></label>
                    <i class="material-icons" id="search-delete">close</i>
                </div>
                <input type="hidden" id="search_type" name="type" value="book">
            </form>
        </div>
        <div class="col s3">
            <ul class="right hide-on-med-and-down">
                <li><a href="create"><i class="material-icons edit_icon">mode_edit</i></a></li>
                <!-- 右上角下拉列表 -->
                <li style="height: 64px;min-width: 160px">
                    <a class="dropdown-button" data-activates="dropdown1" data-beloworigin="true"
                       data-hover="hover" data-alignment="right" style="height: 64px">
                        <span style="position:relative;bottom:10px;right:10px;margin-left: 10px">
                            <%=session.getAttribute("nickname")%>
                        </span>
                        <img src="<%=session.getAttribute("avatar")%>"
                             style="width: 32px;height:32px;margin-top: 16px;
                    object-fit: cover;border-radius: 5%">
                        <i class="material-icons right">arrow_drop_down</i></a>
                </li>
            </ul>
        </div>
    </div>
    <script>$(".dropdown-button").dropdown();</script>
    <div class="blue" id="nav_search_type">
        <ul class="hide-on-med-and-down" id="type"
            style="position: relative;height: 64px;width: 192px;margin: auto;font-size: 0">
            <li id="search_book_button" style="width: 64px;text-align: center"><a id="search_book">书籍</a></li>
            <li id="search_article_button" style="width: 64px;text-align: center"><a id="search_article">章节</a></li>
            <li id="search_user_button" style="width: 64px;text-align: center"><a id="search_user">用户</a></li>
            <%if (request.getParameter("type") != null && request.getParameter("type").equals("book")) {%>
            <li style="margin-left: 35px">
                <a class="dropdown-button grey-text" data-activates="dropdown2" data-hover="hover"
                   data-beloworigin="true">
                    排序&nbsp;&nbsp;&nbsp;<i class="material-icons right" style="margin-top:4px">
                    arrow_drop_down</i></a></li>
            <%}%>
        </ul>
    </div>
</nav>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
    <li><a id="username_display" class="blue-text">未登录</a></li> <!--显示用户名与昵称-->
    <li><a href="homepage.jsp" class="blue-text">我的主页</a></li>
    <li class="divider"></li>
    <li><a href="#!" class="blue-text">退出</a></li>
</ul>
<script>
    // 通过cookie 修改id 为 username_display 和 nickname_display 的元素
</script>
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
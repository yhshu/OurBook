<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
    <li><a id="username_display" class="blue-text">未登录</a></li><!--显示用户名与昵称-->
    <li><a href="homepage.jsp" class="blue-text">我的主页</a></li>
    <li class="divider"></li>
    <li><a href="/LogoutServlet" class="blue-text">退出</a></li>
</ul>
<script>
    function getCookie(c_name) {
        if (document.cookie.length > 0) {
            c_start = document.cookie.indexOf(c_name + "=")
            if (c_start != -1) {
                c_start = c_start + c_name.length + 1
                c_end = document.cookie.indexOf(";", c_start)
                if (c_end == -1) c_end = document.cookie.length
                return unescape(document.cookie.substring(c_start, c_end))
            }
        }
        return ""
    }

    // 通过cookie 修改id 为 username_display 和 nickname_display 的元素
    $(document).ready()
    {
        var nickname = getCookie('nickname');
        var username = getCookie('username');
        if (nickname !== "" && username !== "")
            document.getElementById("username_display").innerText = nickname + " (" + username + ")";
    }
</script>
<nav> <!-- 顶部栏 -->
    <div class="nav-wrapper blue row">
        <div class="col s4">
            <a href="#!" class="brand-logo"><i class="material-icons">book</i>OurBook</a>
        </div>
        <div class="nav-wrapper col s4" style="padding: 5px">
            <form>
                <div class="input-field blue lighten-1">
                    <input id="search" type="search" required>
                    <label class="label-icon" for="search"><i class="material-icons">search</i></label>
                    <i class="material-icons">close</i>
                </div>
            </form>
        </div>
        <div class="col s4">
            <ul class="right hide-on-med-and-down">
                <li><a href="">Components</a></li>
                <!-- 右上角下拉列表 -->
                <li><a class="dropdown-trigger" data-target="dropdown1">Dropdown<i class="material-icons right">arrow_drop_down</i></a>
                </li>
            </ul>
        </div>
    </div>
    <script>$(".dropdown-trigger").dropdown();</script>
</nav>
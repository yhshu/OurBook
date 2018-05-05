
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
%>
<meta charset="UTF-8">
<!-- 文档 http://www.materialscss.com -->
<link rel="stylesheet" href="css/materialize.css">
<link rel="stylesheet" href="css/materialize_icon.css">
<link rel="stylesheet" href="css/myCSS.css">
<!--不要随意更改js的引入顺序-->
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/jQueryFormPlugin-4.2.2.js"></script>
<script type="text/javascript" src="js/materialize.js"></script>
<script type="text/javascript" src="js/myJS.js"></script>
<script>
    $(document).ready(function () {
        var edit_icon = $('.edit_icon');
        var notification_icon = $('.notification_icon');
        var modify_chapter_icon = $('.modify_chapter_icon');
        var remove_favorite = $('a .material-icons:contains("favorite")');
        var add_favorite = $('a .material-icons:contains("favorite_border")');
        var favorites = $('p .material-icons:contains("favorite")');
        var clicks = $('p .material-icons:contains("remove_red_eye")');
        var followers = $('p .material-icons:contains("perm_identity")');
        var settings = $('a .material-icons:contains("settings")');
        var history = $('i.material-icons:contains("history")');
        notification_icon.addClass("tooltipped");
        notification_icon.attr('data-tooltip', '通知');
        edit_icon.addClass("tooltipped");
        edit_icon.attr('data-tooltip', '创建新书');
        modify_chapter_icon.addClass("tooltipped");
        modify_chapter_icon.attr('data-tooltip', '修改章节');
        remove_favorite.addClass("tooltipped");
        remove_favorite.attr("data-tooltip", '取消收藏');
        add_favorite.addClass("tooltipped");
        add_favorite.attr("data-tooltip", '收藏');
        favorites.addClass("tooltipped");
        favorites.attr("data-tooltip", '收藏量');
        clicks.addClass("tooltipped");
        clicks.attr("data-tooltip", '点击量');
        followers.addClass("tooltipped");
        followers.attr("data-tooltip", '书迷数');
        settings.addClass("tooltipped");
        settings.attr("data-tooltip", '设置');
        history.addClass("tooltipped");
        history.attr("data-tooltip", '查看历史');
        $('.tooltipped').tooltip({position: 'bottom'});
    });

    function toast(msg) {
        Materialize.toast(msg, 2000, 'rounded');
    }
</script>
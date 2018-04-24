<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<meta charset="UTF-8">
<!-- 文档 http://www.materialscss.com -->
<link rel="stylesheet" href="css/materialize.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="css/myCSS.css">
<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.js"></script>
<script type="text/javascript" src="js/materialize.js"></script>
<script type="text/javascript" src="js/myJS.js"></script>

<script>
    function toast(msg) {
        Materialize.toast(msg, 2000, 'rounded');
    }

    $(document).ready(function () {
        $('.edit_icon').addClass("tooltipped");
        $('.edit_icon').attr('data-tooltip', '创建新书');
        $('a .material-icons:contains("favorite")').addClass("tooltipped");
        $('a .material-icons:contains("favorite")').attr("data-tooltip", '取消收藏');
        $('a .material-icons:contains("favorite_border")').addClass("tooltipped");
        $('a .material-icons:contains("favorite_border")').attr("data-tooltip", '收藏');
        $('p .material-icons:contains("favorite")').addClass("tooltipped");
        $('p .material-icons:contains("favorite")').attr("data-tooltip", '收藏量');
        $('p .material-icons:contains("remove_red_eye")').addClass("tooltipped");
        $('p .material-icons:contains("remove_red_eye")').attr("data-tooltip", '点击量');
        $('p .material-icons:contains("perm_identity")').addClass("tooltipped");
        $('p .material-icons:contains("perm_identity")').attr("data-tooltip", '书迷数');
        $('.tooltipped').tooltip({position: 'bottom'});
    });

</script>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    // TODO 若本地存在cookie，实现自动登录
%>
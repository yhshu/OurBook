<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <%@ include file="header.jsp" %>
    <title>创建书籍 - OurBook</title>
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
            if (!$.trim($('#bookName').val()).length) {
                toast('书名不能为空');
                return false;
            } else if (!$.trim($('#keywords').val()).length) {
                toast('关键词不能为空');
                return false;
            }
            return true;
        }

        $(document).ready(function () {
            $('#bookDescription').characterCounter(); // 文本框记数

            $('#form').submit(function (event) {
                event.preventDefault();
                if (check_input()) {
                    // Create an FormData object
                    var data = new FormData($('#form')[0]);
                    $.ajax({
                        url: '${pageContext.request.contextPath}/addBook',
                        type: 'POST',
                        async: false,
                        cache: false,
                        contentType: false,
                        processData: false,
                        enctype: 'multipart/form-data',
                        data: data,
                        success: function (respondText) {
                            toast("创建书籍成功");
                            window.location.href = respondText;
                        }
                    }).fail(function (jqXHR) {
                        if ((jqXHR.status) === 403) toast("封面不能超过2MB");
                        else if (jqXHR.status === 415) toast("封面必须是图片");
                        else if (jqXHR.status === 400) toast("书名只能包括汉字、字母或数字");
                        else toast('未知错误');
                    });
                }
            })
        });
    </script>
</head>

<body>
<jsp:include page="nav.jsp"/>
<main>
    <div class="container" style="margin: 20px auto">
        <div class="col card" style="width: 600px; padding: 20px; margin:15px 18.5% ;">
            <form action="${pageContext.request.contextPath}/addBook" method="post" enctype="multipart/form-data"
                  id='form'>
                <div style="border-bottom: 1px solid lightgray">
                    <h4><i class="material-icons">book</i>创建一本书</h4>
                </div>
                <div style="padding: 0 20px">
                    <div class="input-field col s12">
                        <input id="bookName" name="bookName" type="text" class="validate" data-length="40"/>
                        <label for="bookName">书名</label>
                    </div>
                    <div class="input-field col s12">
                <textarea id="bookDescription" name="bookDescription" type="text" class="materialize-textarea"
                          data-length="100"></textarea>
                        <label for="bookDescription">简介（可选）</label>
                    </div>
                    <div class="input-field col s12">
                        <input id="keywords" name="keywords" type="text" class="validate" data-length="40">
                        <label for="keywords">关键词</label>
                    </div>
                    <div style="padding:10px 60px">
                        <input id="cover" type='file' name="cover" onchange="readURL(this);" style="display: none"/>
                        <img id="preview" src="img/icon/plus-icon.png" alt="your image"
                             style="display: inline-block;width: 240px;background-color: #f6f6f6;object-fit: cover"/>
                        <label for="cover" class="blue btn" style="margin-left:60px;float: right;
                     display: inline-block">上传封面</label>
                    </div>
                    <div style="margin: 20px">
                        <input type="submit" class="blue btn" value="创建书籍" style="margin: auto; display: block"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
</main>
<%@ include file="footer.html" %>
</body>
</html>
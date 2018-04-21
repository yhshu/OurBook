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
    </script>
</head>

<body>
<%@ include file="nav.jsp" %>
<div class="container" style="margin-top: 20px">
    <div class="col card" style="width: 600px; padding: 20px">
        <form action="${pageContext.request.contextPath}/addBook" method="post" enctype="multipart/form-data">
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
                    <label for="cover" class="blue btn" style="margin: -240px 0 0 60px;
                     display: inline-block">上传封面</label>
                </div>
                <div style="margin: 20px">
                    <input type="submit" class="blue btn" value="创建书籍" style="margin: auto; display: block"/>
                </div>
            </div>
        </form>
        <script>
            $(document).ready(function () {
                $('#bookDescription').characterCounter(); // 文本框记数
            });
        </script>
    </div>
</div>
</body>
</html>
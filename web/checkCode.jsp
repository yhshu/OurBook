<%@ page contentType="image/jpg; charset=utf-8" pageEncoding="utf-8" %>
<jsp:useBean id="image" scope="page" class="service.impl.CheckCodeServiceImpl"/>
<%
    String str = image.getCertPic(0, 0, response.getOutputStream());
    session.setAttribute("certCode", str);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>Insert title here</title>
    </head>
    <body>

    </body>
</html>
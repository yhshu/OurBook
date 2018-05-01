<%@ page contentType="image/jpg; charset=utf-8" pageEncoding="utf-8" %>
<jsp:useBean id="image" scope="page" class="service.impl.CheckCodeServiceImpl"/>
<%
    String str = image.getCertPic(0, 0, response.getOutputStream());
    System.out.println("验证码: " + str);
    session.setAttribute("certCode", str);
%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>Insert title here</title>
    </head>
    <body>
    </body>
</html>
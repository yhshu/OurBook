<%@ page contentType="image/jpeg"%>
<jsp:useBean id="image" scope="page" class="service.impl.CheckCodeServiceImpl"/>
<%
    String str = image.getCertPic(0, 0, response.getOutputStream());
    System.out.println("验证码: " + str);
    session.setAttribute("certCode", str);
%>

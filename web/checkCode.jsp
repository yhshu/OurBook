<%@ page contentType="image/jpeg"%>
<jsp:useBean id="image" scope="page" class="service.impl.CheckCodeServiceImpl"/>
<%
    String str = image.getCertPic(0, 0, response.getOutputStream());
    session.setAttribute("certCode", str);
%>
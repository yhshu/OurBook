<%@ page language="java" contentType="image/jpg; charset=utf-8"
    pageEncoding="utf-8"%>
    <jsp:useBean id="image" scope="page" class="dao.impl.MakeCertPicImple" />
    <%String str = image.getCertPic(0,0,response.getOutputStream());
    session.setAttribute("certCode",str);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
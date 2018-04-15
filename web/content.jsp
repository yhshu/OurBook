<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.BufferedReader" %>
<head>
    <%@ include file="header.jsp" %>
</head>
<body style="height: 100%">
<jsp:include page="nav.jsp"/>
<div class="row" style="height: 100%">
    <div class="col s3 grey lighten-5" style="height: 100%">
        <ul>
            <li style="padding: 5px">
                <a class="black-text">item1</a>
            </li>
            <li style="padding: 5px">
                <a class="black-text">item2</a>
            </li>
            <li style="padding: 5px">
                <a class="black-text">item3</a>
            </li>
        </ul>
    </div>
    <div class="col s9" style="height: 100%;">
        <h4 style="padding: 5px 20px;"><%=request.getAttribute("name")%>
        </h4>
        <%
            try {
                BufferedReader br = (BufferedReader)request.getAttribute("reader");
                String line;
                while ((line = br.readLine()) != null) {%>
        <p style="padding: 0 10px;"><%=line%>
        </p>
        <%
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </div>
</div>

</body>
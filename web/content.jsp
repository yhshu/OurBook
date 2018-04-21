<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="dao.ChapterDao" %>
<%@ page import="dao.impl.ChapterDaoImpl" %>
<%@ page import="model.Chapter" %>
<%@ page import="java.io.BufferedReader" %>
<head>
    <%@ include file="header.jsp" %>
</head>
<body style="height: 100%">
<jsp:include page="nav.jsp"/>
<div class="row" style="height: 100%">
    <div class="col s3 grey lighten-5" style="height: 100%">
        <div style="margin-left: 4px; margin-top: 10px;">
            <a href="${pageContext.request.contextPath}/book?id=<%=request.getAttribute("bookID")%>"><i
                    class="material-icons">arrow_back</i>返回</a></div>
        <ul style="margin-top: 2px;">
            <!-- 本书的所有章节目录-->
            <%
                ChapterDao chapterDao = new ChapterDaoImpl();
                Chapter[] chapters = chapterDao.findByBookID((int) request.getAttribute("bookID"));
                for (Chapter chapter : chapters) {
            %>
            <li style="padding: 5px">
                <a class="black-text"
                   href="/read?book=<%=chapter.getBookID()%>&sequence=<%=chapter.getSequence()%>"><%=chapter.getName()%>
                </a>
            </li>
            <%
                }%>
        </ul>
    </div>
    <div class="col s9" style="height: 100%;">
        <h4 style="padding: 5px 20px;"><%=request.getAttribute("name")%>
        </h4>
        <%
            try {
                BufferedReader br = (BufferedReader) request.getAttribute("reader");
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
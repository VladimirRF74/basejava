<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Title</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div>
    <a href="resume?action=new">Create new resume</a>
</div>
<br>
<section>
    <table class="list">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th>Delete</th>
            <th>Edit</th>
        </tr>
        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"></jsp:useBean>
            <tr>
                <td class="c1"><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td class="c1" style="text-align:center"><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                </td>
                <td class="c2" style="text-align:center"><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" alt="delete"></a></td>
                <td class="c2" style="text-align:center"><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="pencil"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><label>
                <input type="text" name="fullName" pattern="[a-zA-Zа-яёА-ЯЁ].{2,}" title="2 или более символов" size=50
                       value="${resume.fullName}" required>
            </label></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><label>
                    <input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}">
                </label></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <tr>
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(type)}"></c:set>
                <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"></jsp:useBean>
                <h3><a>${type.title}</a></h3>
                <c:choose>
                    <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                        <textarea name='${type}' cols=75 rows=3><%=section%></textarea>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                        <textarea name='${type}' cols=75
                                  rows=3><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                    </c:when>
                </c:choose>
                <c:if test="${type=='EXPERIENCE'}">
                    <label>
                        <textarea name="${type}" cols="80" rows="3"><%=section%></textarea>
                    </label>
                </c:if>
                <c:if test="${type=='EDUCATION'}">
                    <label>
                        <textarea name="${type}" cols="80" rows="3"><%=section%></textarea>
                    </label>
                </c:if>
            </c:forEach>
        </tr>
        <br>
        <br>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Назад</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
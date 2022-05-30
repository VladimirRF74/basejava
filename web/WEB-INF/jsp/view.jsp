<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<br>
<jsp:include page="fragments/header.jsp"/>
<section id="contact">
    <dl>
        <dt><h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="pencil"></a></h2></dt>
        <dd>
            <ul>
                <c:forEach var="contactEntry" items="${resume.contacts}">
                    <jsp:useBean id="contactEntry"
                                 type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                    <li><%=contactEntry.getKey().toHtml(contactEntry.getValue())%></li>
                </c:forEach>
            </ul>
        </dd>
    </dl>
</section>
<table class="section">
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key}"></c:set>
        <c:set var="section" value="${sectionEntry.value}"></c:set>
        <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"></jsp:useBean>
        <tr>
            <td><h3><a id="type.name">${type.title}</a></h3></td>
        </tr>
        <c:choose>
            <c:when test="${type=='OBJECTIVE'}">
                <tr><td></td>
                    <td colspan="2">
                        <%=((TextSection) section).getContent()%>
                    </td>
                </tr>
            </c:when>
            <c:when test="${type=='PERSONAL'}">
                <tr><td></td>
                    <td colspan="2">
                        <%=((TextSection) section).getContent()%>
                    </td>
                </tr>
            </c:when>
            <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                <tr>
                    <td colspan="2">
                        <ul>
                            <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                                <li>${item}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:when>
            <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="itemE" items="<%=((OrganizationSection) section).getOrganizations()%>">
                        <tr>
                            <td colspan="2">
                                <c:choose>
                                    <c:when test="${empty itemE.link.url}">
                                        <h3>${itemE.link.name}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${itemE.link.url}">${itemE.link.name}</a></h3>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="itemSp" items="${itemE.specialisations}">
                            <jsp:useBean id="itemSp" type="com.urise.webapp.model.Organization.Specialisation"/>
                            <tr>
                                <td align="center"><%=DateUtil.format(itemSp.getStartDate())%><br>
                                <%=DateUtil.format(itemSp.getEndDate())%></td>
                                <td ><h4><a>${itemSp.title}</a></h4>
                                    <tr>
                                        <td></td>
                                        <td >${itemSp.description}</td>
                                    </tr>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
</table><br>
<button type="button" onclick="window.history.back()">Назад</button>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <ul>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <li><%=contactEntry.getKey().toHtml(contactEntry.getValue())%></li><br>
        </c:forEach>
    </ul>
</section>
<section id="section">
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key}"></c:set>
        <c:set var="section" value="${sectionEntry.value}"></c:set>
        <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"></jsp:useBean>

            <th><h3><a id="type.name">${type.title}</a></h3></th>
            <td>
                <c:if test="${type=='OBJECTIVE'}">
                    <td>
                        <ul><a><%=((TextSection) section).getContent()%></a></ul>
                    </td>
                </c:if>
            </td>
            <td>
                <c:if test="${type=='PERSONAL'}">
                    <td>
                        <ul><a><%=((TextSection) section).getContent()%></a></ul>
                    </td>
                </c:if>
            </td>
            <td>
                <c:if test="${type=='ACHIEVEMENT'}">
                    <td>
                        <c:forEach var="itemA" items="<%=((ListSection) section).getItems()%>">
                            <ul><li>${itemA}</li></ul>
                        </c:forEach>
                    </td>
                </c:if>
            </td>
            <td>
                <c:if test="${type=='QUALIFICATIONS'}">
                    <td>
                        <c:forEach var="itemQ" items="<%=((ListSection) section).getItems()%>">
                            <ul><li>${itemQ}</li></ul>
                        </c:forEach>
                    </td>
                </c:if>
            </td>
            <td>
                <c:if test="${type=='EXPERIENCE'}">
                    <td>
                        <c:forEach var="itemE" items="<%=((OrganizationSection) section).getOrganizations()%>">
                            <ul>
                                <h4><a href="${itemE.link.url}">${itemE.link.name}</a></h4>
                                <c:forEach var="itemSp" items="${itemE.specialisations}">
                                    ${itemSp.startDate}<br>
                                    ${itemSp.endDate}<br>
                                    <h4><a>${itemSp.title}</a></h4>
                                    ${itemSp.description}<br>
                                </c:forEach>
                            </ul>
                        </c:forEach>
                    </td>
                </c:if>
            </td>
                <c:if test="${type=='EDUCATION'}">
                    <c:forEach var="itemEd" items="<%=((OrganizationSection) section).getOrganizations()%>">
                        <ul>
                            <h4><a href="${itemEd.link.url}">${itemEd.link.name}</a></h4>
                            <c:forEach var="itemSp" items="${itemEd.specialisations}">
                                ${itemSp.startDate}<br>
                                ${itemSp.endDate}<br>
                                <h4><a>${itemSp.title}</a></h4>
                                ${itemSp.description}<br>
                            </c:forEach>
                        </ul>
                    </c:forEach>
                </c:if>
    </c:forEach>
</section>
<button type="button" onclick="window.history.back()">Назад</button>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
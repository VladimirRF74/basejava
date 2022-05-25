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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="pencil"></a>
    </h2>
    <ul>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <li><%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
            </li>
            <br>
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
        <tr>
            <th><h3><a id="type.name">${type.title}</a></h3></th>
        </tr>
        <c:choose>
            <c:when test="${type=='OBJECTIVE'}">
                <tr>
                    <td colspan="2">
                        <%=((TextSection) section).getContent()%>
                    </td>
                </tr>
            </c:when>
            <c:when test="${type=='PERSONAL'}">
                <tr>
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
                <dl>
                    <c:forEach var="itemE" items="<%=((OrganizationSection) section).getOrganizations()%>">
                            <dt><h3><a href="${itemE.link.url}">${itemE.link.name}</a></h3></dt>
                            <c:forEach var="itemSp" items="${itemE.specialisations}">
                                <jsp:useBean id="itemSp" type="com.urise.webapp.model.Organization.Specialisation"/>
                                <dd><%=DateUtil.format(itemSp.getStartDate())%></dd>
                                <dd><%=DateUtil.format(itemSp.getEndDate())%></dd>
                                <dt><h4><a>${itemSp.title}</a></h4></dt>
                                <dd>${itemSp.description}</dd>
                            </c:forEach>
                    </c:forEach>
                </dl>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<button type="button" onclick="window.history.back()">Назад</button>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

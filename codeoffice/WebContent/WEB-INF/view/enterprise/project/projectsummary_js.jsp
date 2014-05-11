<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="co" uri="http://www.codeoffice.com/colib"%>
<c:forEach var="s" items="${param.summary}" varStatus="status">
	['<co:date date="${s.date}" format="dd-MMM"/>',
	'<c:choose>
		<c:when test="${not empty s.versionStart}">'<spring:message code="project.v_start"/> - ${s.versionCode}'</c:when>
		<c:when test="${not empty s.versionRelease}">'<spring:message code="project.v_release"/> - ${s.versionRelease}'</c:when>
		<c:when test="${not empty s.versionDelay}">'<spring:message code="project.v_delay"/> - ${s.versionDelay}'</c:when>
	<c:otherwise>'null'</c:otherwise>
	</c:choose>'
	, ${s.noCount}, ${s.noResolved}]
	<c:if test="${!status.last}">,</c:if>
</c:forEach>
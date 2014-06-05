<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="user"/>
	</jsp:include>
</div>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/um_menu.jsp">
		<jsp:param name="menu" value="usersessions"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.um.usersession.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.um.usersession.description"/></div>
			</div>
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-page">
						<td colspan="6">showing n items</td>
					</tr>
					<tr class="list-table-header">
						<td><spring:message code="entity.userSession.id"/></td>
						<td><spring:message code="entity.userSession.user"/></td>
						<td><spring:message code="entity.userSession.ip"/></td>
						<td><spring:message code="entity.userSession.requests"/></td>
						<td><spring:message code="entity.userSession.lastRequest"/></td>
						<td><spring:message code="entity.userSession.sessionCreation"/></td>
					</tr>
					<c:forEach items="${userSessions}" var="userSession">
					<tr class="list-table-item">
						<td>${userSession.sessionID}</td>
						<td><code:user user="${userSession.user}"/></td>
						<td>${userSession.ip}</td>
						<td>${userSession.requests}</td>
						<td><fmt:formatDate value="${userSession.access}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><fmt:formatDate value="${userSession.create}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="home"/>
</jsp:include>
<script type="text/javascript">
	$(function() {
		$("#tabs").tabs();
	});
</script>
<div id="content">
	<div class="subelement">
		<div class="title"><spring:message code="settings.submenu"/></div>
		<div class="content">
			<c:if test="${fn:length(sessionScope.SETTING_SUBMENU) eq 0}"><code:info title="settings.no_submenu"/></c:if>
			<c:if test="${fn:length(sessionScope.SETTING_SUBMENU) gt 0}">
			<table class="default-table">
				<tr>
					<th><spring:message code="settings.priority"/></th>
					<th><spring:message code="settings.title"/></th>
					<th><spring:message code="settings.link"/></th>
				</tr>
				<c:forEach items="${sessionScope.SETTING_SUBMENU}" var="submenu">
				<tr>
					<td>${submenu.priority}</td>
					<td>${submenu.title}</td>
					<td><a href="${submenu.link}">${submenu.link}</a></td>
				</tr>
				</c:forEach>
			</table>
			</c:if>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
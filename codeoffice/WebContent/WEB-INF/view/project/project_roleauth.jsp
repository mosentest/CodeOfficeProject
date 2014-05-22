<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/project_menu.jsp">
				<jsp:param name="menu" value="rolegroup"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="assets/img/icon_authority.png"/><span class="titlespan"><spring:message code="project.rolegroup_authtable"/></span></div>
					<div class="content">
					<c:if test="${fn:length(roles) eq 0}"><spring:message code="project.no_rolegroups"/></c:if>
					<c:if test="${fn:length(roles) gt 0}">
					<c:set var="permissionLength" value="${fn:length(permissions)}"/>
					<table class="default-table left-header">
						<tr>
							<th><spring:message code="rolegroup.role"/></th>
							<th><spring:message code="rolegroup.role_description"/></th>
							<c:forEach var="index" 	begin="1" end="${permissionLength}" step="1">
							<th><spring:message code="${permissions[permissionLength - index].code}"/></th>
							</c:forEach>
						</tr>
						<c:forEach items="${roles}" var="role">
						<tr class="border-bottom">
							<td>${role.name}</td>
							<td>${role.description}</td>
							<c:forEach var="index" 	begin="1" end="${permissionLength}" step="1">
							<td><code:checkmark value="${codefunction:bitwiseAnd(permissions[permissionLength - index].value, role.value) gt 0}"/></td>
							</c:forEach>
						</tr>
						</c:forEach>
					</table>
					</c:if>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
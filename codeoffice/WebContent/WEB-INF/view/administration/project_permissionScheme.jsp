<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="project"/>
	</jsp:include>
</div>
<spring:message var="text_clone" code="application.clone"/>
<spring:message var="text_delete" code="application.delete"/>
<spring:message var="text_view_permission" code="administration.project.permissionscheme.viewPermissions"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/project_menu.jsp">
		<jsp:param name="menu" value="permissionscheme"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title imglink">
				<span>${permissionScheme.name}</span>
				<input type="submit" onclick="javascript:url('administration/permissionScheme/clone?scheme=${permissionScheme.name}');"
					class="button" value="<spring:message code="application.clone"/>"/>
				<form class="inline-form" action="administration/permissionScheme/delete?scheme=${permissionScheme.name}" method="POST">
					<input type="submit" onclick="javascript:confirmSubmit(event, 'Delete?');" class="button" value="<spring:message code="application.delete"/>"/>
				</form>
				</div>
				<div class="sub-element-description">${permissionScheme.description}</div>
			</div>
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.project.permissionscheme.permission"/></td>
						<td><spring:message code="administration.project.permissionscheme.groups"/></td>
						<td><spring:message code="administration.project.permissionscheme.users"/></td>
						<td><spring:message code="administration.project.permissionscheme.projectRoles"/></td>
						<td><spring:message code="administration.project.permissionscheme.operations"/></td>
					</tr>
					<c:forEach items="${permissionScheme.projectPermissionSettings}" var="scheme">
					<tr class="list-table-item">
						<td class="info-value">${scheme.name}</td>
						<td>${scheme.description}</td>
						<td><code:user user="${scheme.creator}"/></td>
						<td>
							<ul class="info-ul-list">
							<c:forEach items="${scheme.projects}" var="project">
								<li><a class="link" href="#"></a></li>
							</c:forEach>
							</ul>
						</td>
						<td></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<form id="remoteForm" method="POST"></form>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
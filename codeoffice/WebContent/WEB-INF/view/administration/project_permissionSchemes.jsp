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
				<div class="sub-element-title"><spring:message code="administration.project.projectPermissionScheme"/></div>
				<div class="sub-element-description">Edit Project Permission Scheme Settings.</div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<form:form action="administration/permissionScheme/create" modelAttribute="permissionScheme" method="POST">
						<table class="minor-form-table">
							<tr class="minor-form-title-row">
								<td colspan="2"><spring:message code="administration.project.permissionscheme.createPermissionScheme"/></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.project.permissionscheme.name"/></td>
								<td class="minor-form-input-col"><form:input path="name"/></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.project.permissionscheme.description"/></td>
								<td class="minor-form-input-col"><form:input path="description" class="long-field" /></td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" class="button" value="<spring:message code="application.create"/>"/></td>
							</tr>
						</table>
					</form:form>
				</div>
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.project.permissionscheme.name"/></td>
						<td><spring:message code="administration.project.permissionscheme.description"/></td>
						<td><spring:message code="administration.project.permissionscheme.creator"/></td>
						<td><spring:message code="administration.project.permissionscheme.relatedProjects"/></td>
						<td><spring:message code="administration.project.permissionscheme.operations"/></td>
					</tr>
					<c:forEach items="${permissionSchemes}" var="scheme">
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
						<td>
						<security:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
							<a class="link" href="administration/permissionScheme/${scheme.name}.html">${text_view_permission}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="administration/permissionScheme/${scheme.name}/clone">${text_clone}</a>
							<c:if test="${not scheme.defaultScheme}">
								<span class="minorspace">&#183;</span>
								<a class="link" href="javascript:remoteSubmit(event, 'administration/permissionScheme/${scheme.name}/delete', 'Delete?');">${text_delete}</a>
							</c:if>
						</security:authorize>
						</td>
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
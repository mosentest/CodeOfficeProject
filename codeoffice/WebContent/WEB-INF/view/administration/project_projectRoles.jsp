<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="project"/>
	</jsp:include>
</div>
<spring:message var="text_edit" code="application.edit"/>
<spring:message var="text_delete" code="application.delete"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/project_menu.jsp">
		<jsp:param name="menu" value="projectrole"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.project.projectRolesSettings"/></div>
				<div class="sub-element-description">Edit Project Roles Settings.</div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<form:form action="administration/projectRole/create" modelAttribute="projectRole" method="POST">
						<table class="filter-table">
							<tr class="filter-table-title">
								<td colspan="2"><spring:message code="administration.project.projectrole.createProjectRole"/></td>
							</tr>
							<code:formError errors="${formErrors}"/>
							<tr class="filter-table-label">
								<td><spring:message code="administration.project.projectrole.name"/><span class="icon-required">&nbsp;</span><span class="minorspace"></span>
								<span class="description-info">format ([a-zA-Z]+((-)?[a-zA-Z])+)</span></td>
								<td><spring:message code="administration.project.projectrole.description"/></td>
							</tr>
							<tr class="filter-table-input">
								<td><form:input path="name"/></td>
								<td><form:input path="description" class="long-field" /></td>
							</tr>
 							<tr class="filter-table-input">
								<td colspan="2"><input class="button" type="submit" value="<spring:message code="application.create"/>" /></td>
							</tr>
						</table>
					</form:form>
				</div>
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.project.projectrole.name"/></td>
						<td><spring:message code="administration.project.projectrole.operations"/></td>
					</tr>
					<c:forEach items="${projectRoles}" var="projectRole">
					<tr class="list-table-item">
						<td>
							<span class="title-info">${projectRole.name}</span>
							<c:if test="${not empty projectRole.description}"><br /><span class="description-info">${projectRole.description}</span></c:if>
						</td>
						<td>
							<a class="link" href="administration/projectRole/edit.html?role=${projectRole.name}">${text_edit}</a><span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/projectRole/delete?role=${projectRole.name}', 'Delete?');">${text_delete}</a>
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
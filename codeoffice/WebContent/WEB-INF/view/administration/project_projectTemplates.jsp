<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="project"/>
	</jsp:include>
</div>
<spring:message var="text_edit" code="application.edit" />
<spring:message var="text_delete" code="application.delete" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/project_menu.jsp">
		<jsp:param name="menu" value="projecttemplate"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.project.projecttemplate.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.project.projecttemplate.description"/></div>
			</div>
			<div class="filter-content">
				<form:form action="administration/projectTemplate/create" modelAttribute="projectTemplate" method="POST">
					<table class="minor-form-table">
						<tr class="minor-form-title-row">
							<td colspan="2"><spring:message code="entity.projectTemplate.create"/></td>
						</tr>
						<code:formError errors="${formErrors}"/>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.projectTemplate.name"/><span class="icon-required">&nbsp;</span></td>
							<td class="minor-form-input-col"><form:input path="name"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.projectTemplate.description"/></td>
							<td class="minor-form-input-col"><form:input path="description" class="long-field" /></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.projectTemplate.projectPermissionScheme"/></td>
							<td class="minor-form-input-col">
								<form:select path="projectPermissionScheme.id">
									<option value="">---  SELECT  ---</option>
									<c:forEach items="${projectPermissionSchemes}" var="scheme">
										<form:option value="${scheme.id}" label="${scheme.name}"/>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.projectTemplate.workFlow"/></td>
							<td class="minor-form-input-col">
								<form:select path="workFlow.id">
									<option value="">---  SELECT  ---</option>
									<c:forEach items="${workFlows}" var="workFlow">
										<form:option value="${workFlow.id}" label="${workFlow.name}"/>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.projectTemplate.notificationScheme"/></td>
							<td class="minor-form-input-col">
								<form:select path="notificationScheme.id">
									<option value="">---  SELECT  ---</option>
									<c:forEach items="${notificationSchemes}" var="scheme">
										<form:option value="${scheme.id}" label="${scheme.name}"/>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" class="button" value="<spring:message code="application.create"/>"/></td>
						</tr>
					</table>
				</form:form>
			</div>
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="entity.projectTemplate.name"/></td>
						<td><spring:message code="entity.projectTemplate.projectPermissionScheme"/></td>
						<td><spring:message code="entity.projectTemplate.workFlow"/></td>
						<td><spring:message code="entity.projectTemplate.notificationScheme"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${projectTemplates}" var="template">
					<tr class="list-table-item">
						<td><span class="title-info">${template.name}</span>
							<c:if test="${not empty template.description}"><br />
							<span class="description-info">${template.description}</span></c:if>
						</td>
						<td><a class="link" href="administration/permissionScheme.html?scheme=${codefunction:maskURL(template.projectPermissionScheme.name)}">${template.projectPermissionScheme.name}</a></td>
						<td><a class="link" href="administration/workFlow.html?workflow=${codefunction:maskURL(template.workFlow.name)}">${template.workFlow.name}</a></td>
						<td><a class="link" href="administration/notificationScheme.html?scheme=${codefunction:maskURL(template.notificationScheme.name)}">${template.notificationScheme.name}</a></td>
						<td>
							<a class="link" href="administration/projectTemplate/edit.html?template=${codefunction:maskURL(template.name)}">${text_edit}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/projectTemplate/delete?template=${codefunction:maskURL(template.name)}', 'Delete?');">${text_delete}</a>
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
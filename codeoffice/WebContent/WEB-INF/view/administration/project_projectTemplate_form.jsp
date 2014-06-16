<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
				<div class="sub-element-title">${projectTemplate.name}</div>
				<div class="sub-element-description">${projectTemplate.description}</div>
			</div>
			<div class="sub-element-content">
				<form:form action="administration/projectTemplate/edit?template=${projectTemplate.name}" modelAttribute="projectTemplate" method="POST">
					<table class="form-table">
						<form:hidden path="id"/>
						<code:formError errors="${formErrors}"/>
						<tr>
							<td class="form-label-col"><spring:message code="entity.projectTemplate.name"/><span class="icon-required">&nbsp;</span></td>
							<td class="form-input-col"><form:input path="name"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="entity.projectTemplate.description"/></td>
							<td class="form-input-col"><form:input path="description" class="long-field" /></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="entity.projectTemplate.projectPermissionScheme"/></td>
							<td class="form-input-col">
								<form:select path="projectPermissionScheme.id">
									<option value="">---  SELECT  ---</option>
									<c:forEach items="${projectPermissionSchemes}" var="scheme">
										<option value="${scheme.id}" ${scheme eq projectTemplate.projectPermissionScheme ? 'selected' : ''} >${scheme.name}</option>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="entity.projectTemplate.workFlow"/></td>
							<td class="form-input-col">
								<form:select path="workFlow.id">
									<option value="">---  SELECT  ---</option>
									<c:forEach items="${workFlows}" var="workFlow">
										<option value="${workFlow.id}" ${workFlow eq projectTemplate.workFlow ? 'selected' : ''} >${workFlow.name}</option>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="entity.projectTemplate.notificationScheme"/></td>
							<td class="form-input-col">
								<form:select path="notificationScheme.id">
									<option value="">---  SELECT  ---</option>
									<c:forEach items="${notificationSchemes}" var="scheme">
										<option value="${scheme.id}" ${scheme eq projectTemplate.notificationScheme ? 'selected' : ''} >${scheme.name}</option>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" class="button" value="<spring:message code="application.update"/>"/></td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<form id="remoteForm" method="POST"></form>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
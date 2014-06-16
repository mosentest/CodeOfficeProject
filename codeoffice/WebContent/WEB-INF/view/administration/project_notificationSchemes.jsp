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
<spring:message var="text_view" code="application.view" />
<spring:message var="text_clone" code="application.clone" />
<spring:message var="text_edit" code="application.edit" />
<spring:message var="text_delete" code="application.delete" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/project_menu.jsp">
		<jsp:param name="menu" value="notificationscheme"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.project.notificationscheme.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.project.notificationscheme.description"/></div>
			</div>
			<div class="filter-content">
				<form:form action="administration/notificationScheme/create" modelAttribute="notificationScheme" method="POST">
					<table class="minor-form-table">
						<tr class="minor-form-title-row">
							<td colspan="2"><spring:message code="entity.projectTemplate.create"/></td>
						</tr>
						<code:formError errors="${formErrors}"/>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.notificationScheme.name"/><span class="icon-required">&nbsp;</span></td>
							<td class="minor-form-input-col"><form:input path="name"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.notificationScheme.description"/></td>
							<td class="minor-form-input-col"><form:input path="description" class="long-field" /></td>
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
						<td><spring:message code="entity.notificationScheme.name"/></td>
						<td><spring:message code="entity.notificationScheme.creator"/></td>
						<td><spring:message code="entity.notificationScheme.modified"/></td>
						<td><spring:message code="entity.notificationScheme.projects"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${notificationSchemes}" var="scheme">
					<tr class="list-table-item">
						<td><span class="title-info">${scheme.name}</span>
							<c:if test="${not empty scheme.description}"><br />
							<span class="description-info">${scheme.description}</span></c:if>
						</td>
						<td><code:user user="${scheme.creator}" width="20" height="20"/></td>
						<td><fmt:formatDate value="${scheme.modified}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${scheme.projects}" var="project">
									<li><a class="link" href="#">${project.name}</a></li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<c:set var="maskURL" value="${codefunction:maskURL(scheme.name)}"/>
							<a class="link" href="administration/notificationScheme.html?scheme=${maskURL}">${text_view}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="administration/notificationScheme/clone?scheme=${maskURL}">${text_clone}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/notificationScheme/delete?scheme=${maskURL}', 'Delete?');">${text_delete}</a>
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="issue"/>
	</jsp:include>
</div>
<spring:message var="text_edit" code="application.edit" />
<spring:message var="text_delete" code="application.delete" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="priority"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.issue.priorities"/></div>
				<div class="sub-element-description">Edit your enterprise global settings.</div>
			</div>
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.issue.issuepriority.name"/></td>
						<td><spring:message code="administration.issue.issuepriority.description"/></td>
						<td><spring:message code="administration.issue.issuepriority.icon"/></td>
						<td><spring:message code="administration.issue.issuepriority.color"/></td>
						<td><spring:message code="administration.issue.issuepriority.order"/></td>
						<td><spring:message code="administration.issue.issuepriority.operations"/></td>
					</tr>
					<c:forEach items="${issuePriorities}" var="priority">
					<tr class="list-table-item">
						<td class="title-info">${priority.name}</td>
						<td class="description-info">${priority.description}</td>
						<td><img src="assets/img/office/priority/${priority.icon}.png"/></td>
						<td><span class="color-info" style="background-color: #${priority.color};">&nbsp;</span></td>
						<td>${priority.priorityOrder}</td>
						<td>
							<a class="link" href="administration/issuePriority/${priority.name}/edit.html">${text_edit}</a>
							<c:if test="${not status.defaultPriority}">
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/issuePriority/${priority.name}/delete', 'Delete?');">${text_delete}</a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
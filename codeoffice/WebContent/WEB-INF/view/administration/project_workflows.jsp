<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="project"/>
	</jsp:include>
</div>
<spring:message var="text_view" code="application.view" />
<spring:message var="text_edit" code="application.edit" />
<spring:message var="text_delete" code="application.delete" />
<spring:message var="text_clone" code="application.clone" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/project_menu.jsp">
		<jsp:param name="menu" value="workflow"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.project.workflow.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.project.workflow.description"/></div>
			</div>
			<div class="filter-content">
				<a class="link" href="administration/workFlow/create.html"><spring:message code="entity.workFlow.create"/></a>
			</div>
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="entity.workFlow.name"/></td>
						<td><spring:message code="entity.workFlow.creator"/></td>
						<td><spring:message code="entity.workFlow.modified"/></td>
						<td><spring:message code="entity.workFlow.steps"/></td>
						<td><spring:message code="entity.workFlow.defaultStatus"/></td>
						<td><spring:message code="entity.workFlow.issueStatus"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${workFlows}" var="workFlow">
					<tr class="list-table-item">
						<td><span class="title-info">${workFlow.name}</span>
							<c:if test="${not empty workFlow.description}"><br />
							<span class="description-info">${workFlow.description}</span></c:if>
						</td>
						<td><code:user user="${workFlow.creator}"/></td>
						<td class="description-info">
							<c:if test="${not empty workFlow.modified}"><fmt:formatDate value="${workFlow.modified}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></c:if>
						</td>
						<td class="info-number">${workFlow.steps}</td>
						<td><span class="loungez" style="background-color: #${workFlow.defaultStatus.color}">${workFlow.defaultStatus.name}</span></td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${workFlow.issueStatus}" var="issueStatus">
								<li>${issueStatus.name}</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<a class="link" href="administration/workFlow/${workFlow.name}.html">${text_view}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="administration/workFlow/edit.html?workflow=${workFlow.name}">${text_edit}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="administration/workFlow/clone?workflow=${workFlow.name}">${text_clone}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/workFlow/delete?workflow=${workFlow.name}', 'Delete?');">${text_delete}</a>
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
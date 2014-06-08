<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="project"/>
	</jsp:include>
</div>
<script>
	function selectTypeIcon(icon, name, status, id) {
		$('#typeIcon-icon_' + id).attr('src', 'assets/img/office/status/' + icon + ".png");
		$('#typeIcon-text_' + id).text(name);
		$('.image-select-ul-list').css({'left' : '-9999px'});
		$("input[name='" + id + ".id']").val(status);
	}
</script>
<c:set var="maskURL" value="${codefunction:maskURL(workFlow.name)}"/>
<spring:message var="text_edit" code="application.edit" />
<spring:message var="text_delete" code="application.delete" />
<spring:message var="text_clone" code="application.clone" />
<spring:message var="text_any" code="application.any"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/project_menu.jsp">
		<jsp:param name="menu" value="workflow"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title imglink">
					<span>${workFlow.name}</span>
					<input type="button" class="button" onclick="javascript:url('/administration/workFlow/edit.html?workFlow=${maskURL}');" value="${text_edit}"/>
					<input type="button" class="button" onclick="javascript:url('/administration/workFlow/clone?workFlow=${maskURL}');" value="${text_clone}"/>	
				</div>
				<div class="sub-element-description">${workFlow.description}</div>
			</div>
			<div class="sub-element-content">
				<table class="form-table">
					<tr>
						<td class="form-label-col"><spring:message code="entity.workFlow.defaultStatus"/>:</td>
						<td class="form-input-col"><span class="loungez ${workFlow.defaultStatus.color}" >${workFlow.defaultStatus.name}</span></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.workFlow.resolvedStatus"/>:</td>
						<td class="form-input-col"><span class="loungez ${workFlow.resolvedStatus.color}" >${workFlow.resolvedStatus.name}</span></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.workFlow.closedStatus"/>:</td>
						<td class="form-input-col"><span class="loungez ${workFlow.closedStatus.color}" >${workFlow.closedStatus.name}</span></td>
					</tr>
				</table>
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="entity.workFlowTransition.from"/></td>
						<td><spring:message code="entity.workFlowTransition.transition"/></td>
						<td><spring:message code="entity.workFlowTransition.to"/></td>
						<td><spring:message code="entity.workFlowTransition.requiredPermissions"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${workFlow.transitions}" var="transition">
					<tr class="list-table-item">
						<td><span class="loungez ${transition.from.color}" >${transition.from.name}</span></td>
						<td>${transition.transition}</td>
						<td><span class="loungez ${transition.to.color}" >${transition.to.name}</span></td>
						<td>
							<ul class="info-ul-list ul-list-high">
								<c:if test="${transition.requiredPermissionValue eq 1721729024}">
								<li>${text_any}</li>
								</c:if>
								<c:if test="${transition.requiredPermissionValue ne 1721729024}">
								<c:forEach items="${transition.requiredPermissions}" var="permission">
									<li><spring:message code="${permission.key}"/></li>
								</c:forEach>
								</c:if>
							</ul>
						</td>
						<td><a class="link" href="javascript:remoteSubmit(event, 'administration/workFlowTransition/delete?workFlow=${maskURL}&transition=${transition.id}', 'Delete?');">${text_delete}</a></td>
					</tr>
					</c:forEach>
				</table>
			</div>
			<div class="filter-table">
				<c:if test="${fn:length(issueStatus) gt 0}">
				<form:form action="administration/workFlowTransition/create?workflow=${maskURL}" modelAttribute="workFlowTransition" method="POST">
				<form:hidden path="workFlow.id" value="${workFlow.id}"/>
				<table class="filter-table">
					<tr class="filter-table-title">
						<td colspan="4"><spring:message code="entity.workFlowTransition.add"/></td>
					</tr>
					<code:formError errors="${formErrors}"/>
					<tr class="filter-table-label">
						<td><spring:message code="entity.workFlowTransition"/><span class="icon-required">&nbsp;</span>:</td>
						<td><spring:message code="entity.workFlowTransition.from"/><span class="icon-required">&nbsp;</span>:</td>
						<td><spring:message code="entity.workFlowTransition.to"/><span class="icon-required">&nbsp;</span>:</td>
						<td><spring:message code="entity.workFlowTransition.requiredPermissions"/>:</td>
					</tr>
					<tr class="filter-table-input">
						<td class="form-top-col"><form:input path="transition"/></td>
						<td class="form-top-col">
							<form:hidden path="from.id" value="${issueStatus[0].id}"/>
							<span class="image-select-indicator imglink" id="typeIcon_from">
								<img id="typeIcon-icon_from" src="assets/img/office/status/${issueStatus[0].icon}.png"/>
								<span id="typeIcon-text_from" class="text">${issueStatus[0].name}</span>
								<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
							</span>
							<div class="image-select-ul-list">
								<ul>
									<c:forEach items="${issueStatus}" var="status">
									<li class="imglink" onclick="javascript:selectTypeIcon('${status.icon}', '${status.name}', ${status.id}, 'from');">
										<img src="assets/img/office/status/${status.icon}.png"/>
										<span class="text">${status.name}</span>
									</li>
									</c:forEach>
								</ul>
							</div>
						</td>
						<td class="form-top-col">
							<form:hidden path="to.id" value="${issueStatus[0].id}"/>
							<span class="image-select-indicator imglink" id="typeIcon_to">
								<img id="typeIcon-icon_to" src="assets/img/office/status/${issueStatus[0].icon}.png"/>
								<span id="typeIcon-text_to" class="text">${issueStatus[0].name}</span>
								<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
							</span>
							<div class="image-select-ul-list">
								<ul>
									<c:forEach items="${issueStatus}" var="status">
									<li class="imglink" onclick="javascript:selectTypeIcon('${status.icon}', '${status.name}', ${status.id},'to');">
										<img src="assets/img/office/status/${status.icon}.png"/>
										<span class="text">${status.name}</span>
									</li>
									</c:forEach>
								</ul>
							</div>
						</td>
						<td class="form-top-col">
							<form:select path="requiredPermissions" multiple="true" size="6">
								<c:forEach items="${projectPermissions}" var="permission">
									<option value="${permission}"><spring:message code="${permission.key}"/></option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					<tr class="filter-table-input">
						<td colspan="4"><input type="submit" class="button" value="<spring:message code="entity.workFlowTransition.add"/>"/></td>
					</tr>
				</table>
				</form:form>
				</c:if>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<form id="remoteForm" method="POST"></form>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
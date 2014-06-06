<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="project"/>
	</jsp:include>
</div>
<script>
	function selectTypeIcon(icon, id) {
		$('#typeIcon-icon_' + id).attr('src', 'assets/img/office/status/' + icon + ".png");
		$('#typeIcon-text_' + id).text(icon);
		$('.image-select-ul-list').css({'left' : '-9999px'});
		$("input[name='" + id + "']").val(icon);
	}
</script>
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
				<div class="sub-element-title imglink">
					<span>${workFlow.name}</span>
					<input type="button" class="button" onclick="javascript:url('/administration/workFlow/edit.html?workflow=${workFlow.name}');" value="${text_edit}"/>
					<input type="button" class="button" onclick="javascript:url('/administration/workFlow/clone?workflow=${workFlow.name}');" value="${text_clone}"/>
					<input type="button" class="button" onclick="javascript:remoteSubmit(event, 'administration/workFlow/delete?workflow=${workFlow.name}', 'Delete?');" value="${text_delete}"/>				
				</div>
				<div class="sub-element-description">${workFlow.description}</div>
			</div>
			<div class="filter-table">
				<c:if test="${fn:length(issueStatus) gt 0}">
				<form:form action="administration/workFlow/transition/create" modelAttribute="workFlowTransition" method="POST">
				<table class="filter-table">
					<tr class="filter-table-title"></tr>
					<tr class="filter-table-label">
						<td><spring:message code="entity.workFlowTransition"/>:</td>
						<td><spring:message code="entity.workFlowTransition.from"/>:</td>
						<td><spring:message code="entity.workFlowTransition.to"/>:</td>
						<td><spring:message code="entity.workFlowTransition.requiredPermissions"/>:</td>
					</tr>
					<tr class="filter-table-input">
						<td class="form-top-col"><form:input path="transition"/></td>
						<td class="form-top-col">
							<form:hidden path="from"/>
							<span class="image-select-indicator imglink" id="typeIcon_from">
								<img id="typeIcon-icon_from" src="assets/img/office/status/${issueStatus[0].icon}.png"/>
								<span id="typeIcon-text_from" class="text">${issueStatus[0].name}</span>
								<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
							</span>
							<div class="image-select-ul-list">
								<ul>
									<c:forEach items="${issueStatus}" var="status">
									<li class="imglink" onclick="javascript:selectTypeIcon('${status.icon}', 'from');">
										<img src="assets/img/office/status/${status.icon}.png"/>
										<span class="text">${status.name}</span>
									</li>
									</c:forEach>
								</ul>
							</div>
						</td>
						<td class="form-top-col">
							<form:hidden path="to"/>
							<span class="image-select-indicator imglink" id="typeIcon_to">
								<img id="typeIcon-icon_to" src="assets/img/office/status/${issueStatus[0].icon}.png"/>
								<span id="typeIcon-text_to" class="text">${issueStatus[0].name}</span>
								<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
							</span>
							<div class="image-select-ul-list">
								<ul>
									<c:forEach items="${issueStatus}" var="status">
									<li class="imglink" onclick="javascript:selectTypeIcon('${status.icon}', 'to');">
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
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="entity.workFlowTransition.from"/></td>
						<td><spring:message code="entity.workFlowTransition"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${transitionMap}" var="transitions">
					<tr class="list-table-item">
						<td><span class="loungez" style="background-color: #${transitions.key.color}">${transitions.key.name}</span>
						<c:if test="${transitions.key eq workFlow.defaultStatus}">(<spring:message code="application.default"/>)</c:if></td>
						<td>
							<ul class="info-ul-list ul-list-high">
								<c:forEach items="${transitions.value}" var="transition">
								<li class="imglink">
									<span class="info-number">${transition.transition}</span> --&gt; 
									<span class="loungez" style="background-color: #${transition.to.color}">${transition.to.name}</span>
								</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<a class="link" href="administration/workFlow/${workFlow.name}.html">${text_view}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="administration/workFlow/edit.html?workflow=${workFlow.name}">${text_edit}</a>
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
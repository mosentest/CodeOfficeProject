<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="issue"/>
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
<c:set var="edit" value="${not empty workFlow.id}"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/project_menu.jsp">
		<jsp:param name="menu" value="workflow"/>
	</jsp:include>
	<div id="maincontent">
		<div class="panel-content">
			<div class="panel-element">
				<div class="panel-element-title">
					<div class="panel-element-info">
						<c:if test="${edit}">${workFlow.name}</c:if>
						<c:if test="${not edit}"><spring:message code="entity.workFlow.create"/></c:if>
					</div>
					<div class="panel-element-description">${workFlow.description}</div>
				</div>
				<div class="panel-element-content">
					<c:if test="${not edit}"><c:set var="url" value="administration/workFlow/create"/></c:if>
					<c:if test="${edit}"><c:set var="url">administration/workFlow/edit?workFlow=${codefunction:maskURL(workFlow.name)}</c:set></c:if>
					<form:form action="${url}" modelAttribute="workFlow" method="POST">
					<table class="minor-form-table">
						<form:hidden path="id"/>
						<code:formError errors="${formErrors}"/>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.workFlow.name"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col"><form:input path="name"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.workFlow.description"/>:</td>
							<td class="minor-form-input-col"><form:textarea path="description" rows="3" cols="30"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.workFlow.defaultStatus"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col">
								<form:hidden path="defaultStatus.id" value="${edit ? workFlow.defaultStatus.id : issueStatus[0].id}"/>
								<span class="image-select-indicator imglink" id="typeIcon_defaultStatus">
									<c:if test="${edit}">
									<img id="typeIcon-icon_defaultStatus" src="assets/img/office/status/${workFlow.defaultStatus.icon}.png"/>
									<span id="typeIcon-text_defaultStatus" class="text">${workFlow.defaultStatus.name}</span>
									</c:if>
									<c:if test="${not edit}">
									<img id="typeIcon-icon_defaultStatus" src="assets/img/office/status/${issueStatus[0].icon}.png"/>
									<span id="typeIcon-text_defaultStatus" class="text">${issueStatus[0].name}</span>
									</c:if>
									<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
								</span>
								<div class="image-select-ul-list">
									<ul>
										<c:forEach items="${issueStatus}" var="status">
										<li class="imglink" onclick="javascript:selectTypeIcon('${status.icon}', '${status.name}', ${status.id},'defaultStatus');">
											<img src="assets/img/office/status/${status.icon}.png"/>
											<span class="text">${status.name}</span>
										</li>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.workFlow.resolvedStatus"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col">
								<form:hidden path="resolvedStatus.id" value="${edit ? workFlow.resolvedStatus.id : issueStatus[0].id}"/>
								<span class="image-select-indicator imglink" id="typeIcon_resolvedStatus">
									<c:if test="${edit}">
									<img id="typeIcon-icon_resolvedStatus" src="assets/img/office/status/${workFlow.resolvedStatus.icon}.png"/>
									<span id="typeIcon-text_resolvedStatus" class="text">${workFlow.resolvedStatus.name}</span>
									</c:if>
									<c:if test="${not edit}">
									<img id="typeIcon-icon_resolvedStatus" src="assets/img/office/status/${issueStatus[0].icon}.png"/>
									<span id="typeIcon-text_resolvedStatus" class="text">${issueStatus[0].name}</span>
									</c:if>
									<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
								</span>
								<div class="image-select-ul-list">
									<ul>
										<c:forEach items="${issueStatus}" var="status">
										<li class="imglink" onclick="javascript:selectTypeIcon('${status.icon}', '${status.name}', ${status.id},'resolvedStatus');">
											<img src="assets/img/office/status/${status.icon}.png"/>
											<span class="text">${status.name}</span>
										</li>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.workFlow.closedStatus"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col">
								<form:hidden path="closedStatus.id" value="${edit ? workFlow.closedStatus.id : issueStatus[0].id}"/>
								<span class="image-select-indicator imglink" id="typeIcon_closedStatus">
									<c:if test="${edit}">
									<img id="typeIcon-icon_closedStatus" src="assets/img/office/status/${workFlow.closedStatus.icon}.png"/>
									<span id="typeIcon-text_closedStatus" class="text">${workFlow.closedStatus.name}</span>
									</c:if>
									<c:if test="${not edit}">
									<img id="typeIcon-icon_closedStatus" src="assets/img/office/status/${issueStatus[0].icon}.png"/>
									<span id="typeIcon-text_closedStatus" class="text">${issueStatus[0].name}</span>
									</c:if>
									<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
								</span>
								<div class="image-select-ul-list">
									<ul>
										<c:forEach items="${issueStatus}" var="status">
										<li class="imglink" onclick="javascript:selectTypeIcon('${status.icon}', '${status.name}', ${status.id}, 'closedStatus');">
											<img src="assets/img/office/status/${status.icon}.png"/>
											<span class="text">${status.name}</span>
										</li>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="submit" class="button largebutton" value="<spring:message code="application.save"/>">
								<a class="link" href="administration/workFlows.html"><spring:message code="application.cancel"/></a>
							</td>
						</tr>
					</table>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<form id="remoteForm" method="POST"></form>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
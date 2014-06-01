<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="issue"/>
	</jsp:include>
</div>
<script>
	function selectTypeIcon(icon) {
		$('#typeIcon-icon').attr('src', 'assets/img/office/type/' + icon + ".png");
		$('#typeIcon-text').text(icon);
		$('.image-select-ul-list').css({'left' : '-9999px'});
		$("input[name='icon']").val(icon);
	}
</script>
<spring:message var="text_standard" code="administration.issue.issuetype.standard" />
<spring:message var="text_subtask" code="administration.issue.issuetype.subtask" />
<spring:message var="text_edit" code="application.edit" />
<spring:message var="text_delete" code="application.delete" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="subtask"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.issue.subtasks"/></div>
				<div class="sub-element-description">Edit your enterprise global settings.</div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<form:form action="administration/subtask/create" modelAttribute="issueType" method="POST">
						<table class="minor-form-table">
							<tr class="minor-form-title-row">
								<td colspan="2"><spring:message code="administration.issue.issuetype.createSubtask"/></td>
							</tr>
							<code:formError errors="${formErrors}"/>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.issue.issuetype.name"/></td>
								<td class="minor-form-input-col"><form:input path="name"/></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.issue.issuetype.description"/></td>
								<td class="minor-form-input-col"><form:input path="description" class="long-field" /></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.issue.issuetype.icon"/></td>
								<td class="minor-form-input-col">
									<form:hidden path="icon"/>
									<span class="image-select-indicator imglink" id="typeIcon">
										<img id="typeIcon-icon" src="assets/img/office/type/${icons[0]}.png"/>
										<span id="typeIcon-text" class="text">${icons[0]}</span>
										<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
									</span>
									<div class="image-select-ul-list">
										<ul>
											<c:forEach items="${icons}" var="icon">
											<li class="imglink" onclick="javascript:selectTypeIcon('${icon}');">
												<img src="assets/img/office/type/${icon}.png"/>
												<span class="text">${icon}</span>
											</li>
											</c:forEach>
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" class="button" value="<spring:message code="application.create"/>"/></td>
							</tr>
						</table>
					</form:form>
				</div>
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.issue.issuetype.name"/></td>
						<td><spring:message code="administration.issue.issuetype.icon"/></td>
						<td><spring:message code="administration.issue.issuetype.type"/></td>
						<td><spring:message code="administration.issue.issuetype.operations"/></td>
					</tr>
					<c:forEach items="${issueTypes}" var="issueType">
					<tr class="list-table-item">
						<td>
							<span class="title-info">${issueType.name}</span>
							<c:if test="${not empty issueType.description}"><br /><span class="description-info">${issueType.description}</span></c:if>
						</td>
						<td><img src="assets/img/office/type/${issueType.icon}.png"/></td>
						<td>${issueType.standard ? text_standard : text_subtask}</td>
						<td>
							<a class="link" href="administration/issueType/${issueType.name}/edit.html">${text_edit}</a>
							<c:if test="${not issueType.defaultType}">
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/issueType/${issueType.name}/delete', 'Delete?');">${text_delete}</a>
							</c:if>
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
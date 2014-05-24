<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<script type="text/javascript">
function merge(event) {
	if ($('.m_mergable:checked').length == 0) {
		event.preventDefault();
		return;
	}
}
function select_merge(id) {
	$('#m_' + id).click();
	var checked = $('#m_' + id).attr('checked');
	if (checked === undefined) {
		$('#m_' + id).attr('checked', true);
		$('#a_' + id).animate({backgroundColor: '#ffcc00'}, 'slow');
	} else {
		$('#m_' + id).attr('checked', false);
		$('#a_' + id).animate({backgroundColor: 'transparent'}, 'slow');
	}
	if ($('.m_mergable:checked').length > 1) {
		$('#mergebutton').removeClass('disabled-button');
		$('#mergebutton').addClass('button');
		$('#mergebutton').removeAttr('disabled');
	} else {
		$('#mergebutton').addClass('disabled-button');
		$('#mergebutton').removeClass('button');
		$('#mergebutton').attr('disabled', 'disabled');
	}
}
</script>
<spring:message var="text_edit" code="application.edit"/>
<spring:message var="text_delete" code="application.delete"/>
<spring:message var="text_merge" code="application.merge"/>
<spring:message var="text_create" code="component.create_new_component"/>
<security:authorize access="hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_MANAGER', 'ROLE_ADMIN')">
	<c:set var="VC_MANAGER_AUTH" value="true"/>
</security:authorize>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/project_menu.jsp">
				<jsp:param name="menu" value="components"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<c:if test="${VC_MANAGER_AUTH}">
						<div class="action-field ">
							<code:imagelink image="icon_add" text="${text_create}" link="enterprise/pro_${project.code}/component/create"/>
						</div>
					</c:if>
					<div class="title imglink"><img src="assets/img/office/icon_components.png"/><span class="titlespan"><spring:message code="project.components"/></span></div>
					<div class="content">
						<c:if test="${fn:length(components) eq 0}"><code:info title="project.no_components"/></c:if>
						<c:if test="${fn:length(components) gt 0}">
						<form:form action="enterprise/pro_${project.code}/m_merge" modelAttribute="mergeComponent" method="POST">
						<table class="default-table left-header">
						<tr>
							<c:if test="${VC_MANAGER_AUTH}"><th style="text-align: center;"><spring:message code="application.merge"/></th></c:if>
							<th></th>
							<th><spring:message code="component.name"/></th>
							<th><spring:message code="component.lead"/></th>
							<th><spring:message code="component.default_reporter"/></th>
							<th><spring:message code="component.default_assignee"/></th>
							<th><spring:message code="component.case_count"/></th>
							<th><spring:message code="component.description"/></th>
							<c:if test="${VC_MANAGER_AUTH}"><th></th><th></th></c:if>
						</tr>
						<tr><td colspan="10"><form:hidden path="project" value="${project.code}"/></td></tr>
						<c:forEach items="${components}" var="component" varStatus="status">
						<tr id="a_${status.index}">
							<c:if test="${VC_MANAGER_AUTH}">
							<td class="center">
								<a class="image-link" href="javascript:select_merge(${status.index})"><img src="assets/img/icon_merge.png" title="${text_merge}"/></a>
								<form:checkbox class="m_mergable" id="m_${status.index}" path="componentCode" value="${component.code}" style="display: none;"/>
							</td>
							</c:if>
							<td><img src="assets/img/office/icon_component.png" width="20" height="20"/></td>
							<td><a href="enterprise/pro_${project.code}/m_${component.code}">${component.name}</a></td>
							<td><code:user user="${component.lead}"/></td>
							<td><code:user user="${component.defaultReporter}"/></td>
							<td><code:user user="${component.defaultAssignee}"/></td>
							<td>${component.noCase}</td>
							<td>${component.description}</td>
							<c:if test="${VC_MANAGER_AUTH}">
							<td class="center"><a class="image-link" href="enterprise/pro_${project.code}/m_${component.code}/edit"><img src="assets/img/icon_edit.png" title="${text_edit}"/></a></td>
							<td class="center"><a class="image-link" href="enterprise/pro_${project.code}/m_${component.code}/delete"><img src="assets/img/icon_remove.png" title="${text_delete}"/></a></td>
							</c:if>
						</tr>
						</c:forEach>
						<tr class="separator-tr"><td colspan="10"></td></tr>
						<tr>
							<td colspan="10"><input id="mergebutton" class="disabled-button largebutton" type="submit" value="${text_merge}" onclick="merge(event);" disabled="disabled"/></td>
						</tr>
						</table>
						</form:form>
						</c:if>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
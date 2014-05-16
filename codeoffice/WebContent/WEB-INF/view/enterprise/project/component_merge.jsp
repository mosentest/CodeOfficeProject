<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<script type="text/javascript">
var CODE = 0;
var NAME = 2;
var LEAD = 3;
var REPORTER = 4;
var ASSIGNEE = 5;
var DESCRIPTION = 7;
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
<spring:message var="text_merge" code="application.merge"/>
<link rel="stylesheet" type="text/css" href="css/project.css">
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/component_menu.jsp"/>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/office/icon_merge.png"/><span class="titlespan"><spring:message code="component.merge_components"/></span></div>
					<div class="content">
						<form:form action="" modelAttribute="mergeComponent" method="POST">
						<table class="form-table">
							<tr>
								<td><spring:message code="component.code"/></td>
								<td><form:input path=""/></td>
							</tr>
							<tr>
								<td><spring:message code="component.project"/></td>
								<td>${project.name}</td>
							</tr>
							<tr>
								<td><spring:message code="component.name"/></td>
								<td><form:input path="name"/></td>
							</tr>
							<tr>
								<td><spring:message code="component.lead"/></td>
								<td><form:input path=""/></td>
							</tr>
							<tr>
								<td><spring:message code="component.default_reporter"/></td>
								<td><form:input path=""/></td>
							</tr>
							<tr>
								<td><spring:message code="component.default_assignee"/></td>
								<td><form:input path=""/></td>
							</tr>
							<tr>
								<td><spring:message code="component.description"/></td>
								<td><form:input path="description"/></td>
							</tr>
							<tr>
								<td colspan="2"><input id="mergebutton" class="${fn:length(mergeComponent.componentCode) lt 2 ? 'disabled-button' : 'button'} largebutton" 
									type="submit" value="${text_merge}" onclick="merge(event);" disabled="disabled"/></td>
							</tr>
						</table>
						</form:form>
					</div>
				</div>
				<div class="sep-30"></div>
				<div class="subelement">
					<div class="title"><spring:message code="component.available_components_to_merge"/></div>
					<div class="content">
						<table class="default-table left-header">
						<tr>
							<th style="text-align: center;"><spring:message code="component.set_as_default"/></th>
							<th style="text-align: center;"><spring:message code="application.merge"/></th>
							<th></th>
							<th><spring:message code="component.name"/></th>
							<th><spring:message code="component.lead"/></th>
							<th><spring:message code="component.default_reporter"/></th>
							<th><spring:message code="component.default_assignee"/></th>
							<th><spring:message code="component.case_count"/></th>
							<th><spring:message code="component.description"/></th>
						</tr>
						<c:forEach items="${components}" var="component" varStatus="status">
						<c:set var="componentSelected" value="${codefunction:arrayContains(mergeComponent.componentCode, component.code)}"/>
						<tr id="a_${status.index}" style="background-color:${componentSelected ? '#ffcc00' : 'transparent'}" >
							<td class="center"><input type="radio" id="r_${status.index}" name="default"/></td>
							<td class="center">
								<a class="image-link" href="javascript:select_merge(${status.index})"><img src="img/icon_merge.png" title="${text_merge}"/></a>
								<input type="checkbox" class="m_mergable" ${componentSelected ? 'checked=checked' : ''} 
									id="m_${status.index}" value="${component.code}" style="display: none;"/>
							</td>
							<td><img src="img/office/icon_component.png" width="20" height="20"/></td>
							<td><a href="enterprise/pro_${project.code}/m_${component.code}">${component.name}</a></td>
							<td><code:user user="${component.lead}"/></td>
							<td><code:user user="${component.defaultReporter}"/></td>
							<td><code:user user="${component.defaultAssignee}"/></td>
							<td>${component.noCase}</td>
							<td>${component.description}</td>
						</tr>
						</c:forEach>
						</table>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
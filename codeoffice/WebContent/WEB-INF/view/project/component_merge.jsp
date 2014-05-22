<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<script type="text/javascript">
function getComponentInfo(code) {
	$.ajax({
		url: 'enterprise/pro_${project.code}/m_' + code + '/info',
		type: 'GET',
		responseType: 'json',
		success: function(data) {
			$("input[name='id']").val(data.id);
			$("input[name='code']").val(data.code);
			$("input[name='name']").val(data.name);
			$('#lead').text('').append("<a href=\"enterprise/user/" + data.lead.replace(" ", "_") + "\">" + data.lead + "</a>");
			$('#defaultReporter').text('').append("<a href=\"enterprise/user/" + data.defaultReporter.replace(" ", "_") + "\">" + data.defaultReporter + "</a>");
			$('#defaultAssignee').text('').append("<a href=\"enterprise/user/" + data.defaultAssignee.replace(" ", "_") + "\">" + data.defaultAssignee + "</a>");
			$("textArea[name='description']").val(data.description);
		},
		error: function(xhr, status, error) {
			console.log("error: " + JSON.parse(xhr.responseText));
		}
	});
}
function merge(event) {
	$('#merge-form').attr('action', 'enterprise/pro_${project.code}/m_' + $("input[name='code']").val() + "/merge");
	$('#merge-form').submit();
}
function select_merge(id, code) {
	$('#m_' + id).click();
	var checked = $('#m_' + id).attr('checked');
	if (checked === undefined) {
		$('#m_' + id).attr('checked', true);
		$('#a_' + id).animate({backgroundColor: '#ffcc00'}, 'slow');
		var clone = $('#cc_prototype_' + code).clone();
		clone.attr('id', 'cc_' + code);
		clone.appendTo('#hidden-fields');
	} else {
		$('#m_' + id).attr('checked', false);
		$('#a_' + id).animate({backgroundColor: 'transparent'}, 'slow');
		$('#cc_' + code).remove();
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
<spring:message var="text_select" code="application.select"/>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/component_menu.jsp"/>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="assets/img/office/icon_merge.png"/><span class="titlespan"><spring:message code="component.merge_components"/></span></div>
					<div class="content">
						<form:form action="" id="merge-form" modelAttribute="mergeComponent" method="POST">
						<table class="default-table">
							<tr>
								<td id="hidden-fields">
									<form:hidden path="id" value="${targetComponent.id}"/>
									<c:forEach items="${mergeComponent.componentCode}" var="code">
										<input id="cc_${code}" type="hidden" name="componentCode" value="${code}"/>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.code"/>:</td>
								<td><form:input path="code" readonly="true" value="${targetComponent.code}"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.project"/>:</td>
								<td>${project.name}</td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.name"/>:</td>
								<td><form:input path="name" readonly="true" value="${targetComponent.name}"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.lead"/>:</td>
								<td id="lead"><code:user user="${targetComponent.lead}" showImage="false" showSpace="false"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.default_reporter"/>:</td>
								<td id="defaultReporter"><code:user user="${targetComponent.defaultReporter}" showImage="false" showSpace="false"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.default_assignee"/>:</td>
								<td id="defaultAssignee"><code:user user="${targetComponent.defaultAssignee}" showImage="false" showSpace="false"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.description"/>:</td>
								<td><textArea name="description" readonly="readonly" cols="30" rows="6">${targetComponent.description}</textArea></td>
							</tr>
							<tr>
								<c:set var="mergeDisabled" value="${fn:length(mergeComponent.componentCode) lt 2}"/>
								<td colspan="2"><input id="mergebutton" class="${mergeDisabled ? 'disabled-button' : 'button'} largebutton" 
									type="submit" value="${text_merge}" onclick="merge(event);" ${mergeDisabled ? 'disabled=disabled' : ''}/></td>
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
							<td class="center">
								<input type="radio" id="r_${status.index}" onclick="getComponentInfo('${component.code}')" ${status.first ? 'checked=checked' : ''} name="default"/>
							</td>
							<td class="center">
								<a class="image-link" href="javascript:select_merge(${status.index}, '${component.code}')"><img src="assets/img/icon_merge.png" title="${text_merge}"/></a>
								<input type="checkbox" class="m_mergable" ${componentSelected ? 'checked=checked' : ''} 
									id="m_${status.index}" value="${component.code}" style="display: none;"/>
								<input id="cc_prototype_${component.code}" type="hidden" name="componentCode" value="${component.code}"/>
							</td>
							<td><img src="assets/img/office/icon_component.png" width="20" height="20"/></td>
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
<jsp:include page="/WEB-INF/view/footer.jsp" />
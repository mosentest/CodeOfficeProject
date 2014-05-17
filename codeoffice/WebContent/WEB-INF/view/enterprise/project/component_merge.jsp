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
<link rel="stylesheet" type="text/css" href="css/project.css">
<script type="text/javascript">
function getComponentInfo(code) {
	$.ajax({
		url: 'enterprise/pro_${project.code}/m_' + code + '/info',
		type: 'GET',
		success: function(data) {
			console.log(data);
		},
		error: function(e) {
			console.log(e);
		}
	});
}
function merge(event) {
	$('#merge-form').attr('url', 'project_{project}/m_' + $("input[name='code']") + "/merge");
	$('#merge-form').submit();
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
<spring:message var="text_merge" code="application.merge"/>
<spring:message var="text_select" code="application.select"/>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/component_menu.jsp"/>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/office/icon_merge.png"/><span class="titlespan"><spring:message code="component.merge_components"/></span></div>
					<div class="content">
						<form:form action="" id="merge-form" modelAttribute="mergeComponent" method="POST">
						<table class="form-table">
							<tr>
								<td>
									<form:hidden path="id" value="${targetComponent.id}"/>
									<form:hidden path="code" value="${targetComponent.id}"/>
								</td>
							</tr>
							<tr>
								<td><spring:message code="component.code"/></td>
								<td>
									<c:if test="${empty targetComponent}"><form:input path="code"/></c:if>
									<c:if test="${not empty targetComponent}">${targetComponent.code}</c:if>
								</td>
							</tr>
							<tr>
								<td><spring:message code="component.project"/></td>
								<td>${project.name}</td>
							</tr>
							<tr>
								<td><spring:message code="component.name"/></td>
								<td>
									<c:if test="${empty targetComponent}"><form:input path="name"/></c:if>
									<c:if test="${not empty targetComponent}">${targetComponent.name}</c:if>
								</td>
							</tr>
							<tr>
								<td><spring:message code="component.lead"/></td>
								<td>
									<c:if test="${empty targetComponent}">
									<form:select multitple="single" path="lead">
										<form:option value="0" label="------ ${text_select} ------"/>
										<c:forEach items="${leadGroup}" var="roleGroup">
											<optgroup label="${roleGroup.role.name}">
												<c:forEach items="${roleGroup.users}" var="user">
													<option label="${user.fullName}" value="${user.id}" ${targetComponent.lead.id eq user.id ? 'selected=selected' : ''}/>
												</c:forEach>
											</optgroup>
										</c:forEach>
									</form:select>
									</c:if>
									<c:if test="${not empty targetComponent}"><code:user user="${targetComponent.lead}"/></c:if>	
								</td>
							</tr>
							<tr>
								<td><spring:message code="component.default_reporter"/></td>
								<td>
									<c:if test="${empty targetComponent}">
									<form:select multitple="single" path="defaultReporter">
										<form:option value="0" label="------ ${text_select} ------"/>
										<c:forEach items="${userGroup}" var="roleGroup">
											<optgroup label="${roleGroup.role.name}">
												<c:forEach items="${roleGroup.users}" var="user">
													<option label="${user.fullName}" value="${user.id}" ${targetComponent.defaultReporter.id eq user.id ? 'selected=selected' : ''}/>
												</c:forEach>
											</optgroup>
										</c:forEach>
									</form:select>
									</c:if>
									<c:if test="${not empty targetComponent}"><code:user user="${targetComponent.defaultReporter}"/></c:if>		
								</td>
							</tr>
							<tr>
								<td><spring:message code="component.default_assignee"/></td>
								<td>
									<c:if test="${empty targetComponent}">
									<form:select multitple="single" path="defaultAssignee">
										<form:option value="0" label="------ ${text_select} ------"/>
										<c:forEach items="${userGroup}" var="roleGroup">
											<optgroup label="${roleGroup.role.name}">
												<c:forEach items="${roleGroup.users}" var="user">
													<option label="${user.fullName}" value="${user.id}" ${targetComponent.defaultAssignee.id eq user.id ? 'selected=selected' : ''}/>
												</c:forEach>
											</optgroup>
										</c:forEach>
									</form:select>
									</c:if>
									<c:if test="${not empty targetComponent}"><code:user user="${targetComponent.defaultAssignee}"/></c:if>		
								</td>
							</tr>
							<tr>
								<td><spring:message code="component.description"/></td>
								<td>
									<c:if test="${empty targetComponent}"><form:input path="description"/></c:if>
									<c:if test="${not empty targetComponent}">${targetComponent.description}</c:if>
								</td>
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
							<c:if test="${empty targetComponent}"><th style="text-align: center;"><spring:message code="component.set_as_default"/></th></c:if>
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
							<c:if test="${empty targetComponent}"><td class="center"><input type="radio" id="r_${status.index}" onclick="getComponentInfo(${component.code})" name="default"/></td></c:if>
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
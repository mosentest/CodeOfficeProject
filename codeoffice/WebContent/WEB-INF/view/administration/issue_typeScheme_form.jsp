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
<script>
	$(function() {
		$('#available-types, #selected-types').sortable({
			connectWith: '.list-types'
		}).disableSelection();
	});
	function clearTypes() {
		$('#available-types').find("input[type='hidden']").remove();
	}
</script>
<spring:message var="text_standard" code="administration.issue.issuetype.standard" />
<spring:message var="text_subtask" code="administration.issue.issuetype.subtask" />
<c:set var="edit" value="${not empty issueTypeScheme.id}"/>
<c:set var="editURL">
	<c:if test="${edit}">edit?scheme=${issueTypeScheme.name}</c:if>
	<c:if test="${not edit}">create</c:if>
</c:set>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="typeScheme"/>
	</jsp:include>
	<div id="maincontent">
		<div class="panel-content">
			<div class="panel-element">
				<div class="panel-element-title">
					<div class="panel-element-info">
						<c:if test="${edit}">${issueTypeScheme.name}</c:if>
						<c:if test="${not edit}"><spring:message code="administration.issue.issuetypescheme.createIssueTypeScheme"/></c:if>
					</div>
				</div>
				<div class="panel-element-content">
					<form:form action="administration/typeScheme/${editURL}" modelAttribute="issueTypeScheme" method="POST">
					<table class="minor-form-table">
						<form:hidden path="id"/>
						<code:formError errors="${formErrors}"/>
						<tr>
							<td class="minor-form-label-col"><spring:message code="administration.issue.issuetypescheme.name"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col"><form:input path="name"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="administration.issue.issuetypescheme.description"/>:</td>
							<td class="minor-form-input-col"><form:textarea path="description" rows="3" cols="30"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="administration.issue.issuetypescheme.issueTypes"/>:</td>
							<td></td>
						</tr>
						<tr>
							<td><spring:message code="administration.issue.issuetypescheme.selected"/>:</td>
							<td><spring:message code="administration.issue.issuetypescheme.available"/>:</td>
						</tr>
						<tr>
							<c:set var="typeIndex" value="0" scope="page"/>
							<td class="form-top-col">
								<ul id="selected-types" class="list-types">
									<c:forEach items="${issueTypeScheme.issueTypes}" var="type">
										<li class="imglink">
											<input type="hidden" name="issueTypes[${typeIndex}].id" value="${type.id}">
											<img src="assets/img/office/type/${type.icon}.png"/>
											<span class="text">${type.name}</span>
										</li>
										<c:set var="typeIndex" value="${typeIndex + 1}"/>
									</c:forEach>
								</ul>
							</td>
							<td class="form-top-col">
								<ul id="available-types" class="list-types"><li>
									<c:forEach items="${issueTypes}" var="type">
										<li class="imglink">
											<input type="hidden" name="issueTypes[${typeIndex}].id" value="${type.id}">
											<img src="assets/img/office/type/${type.icon}.png"/>
											<span class="text">${type.name}</span>
										</li>
										 <c:set var="typeIndex" value="${typeIndex + 1}"/>
									</c:forEach>
								</ul>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="submit" onclick="javascript:clearTypes();" class="button largebutton" value="<spring:message code="application.save"/>">
								<a class="link" href="administration/typeSchemes.html"><spring:message code="application.cancel"/></a>
							</td>
						</tr>
					</table>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
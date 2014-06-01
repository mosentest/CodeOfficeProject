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
<spring:message var="text_edit" code="application.edit" />
<spring:message var="text_delete" code="application.delete" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="issueLink"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.issue.issueLinks"/></div>
				<div class="sub-element-description">Edit your enterprise global settings.</div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<form:form action="administration/link/create" modelAttribute="issueLink" method="POST">
						<table class="minor-form-table">
							<tr class="minor-form-title-row">
								<td colspan="2"><spring:message code="administration.issue.issuelink.createIssueLink"/></td>
							</tr>
							<code:formError errors="${formErrors}"/>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.issue.issuelink.name"/></td>
								<td class="minor-form-input-col"><form:input path="name"/></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.issue.issuelink.outwardDescription"/></td>
								<td class="minor-form-input-col"><form:input path="outwardLink" class="long-field" /></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.issue.issuelink.inwardDescription"/></td>
								<td class="minor-form-input-col"><form:input path="inwardLink" class="long-field" /></td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" class="button" value="<spring:message code="application.create"/>"/></td>
							</tr>
						</table>
					</form:form>
				</div>
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.issue.issuelink.name"/></td>
						<td><spring:message code="administration.issue.issuelink.outwardDescription"/></td>
						<td><spring:message code="administration.issue.issuelink.inwardDescription"/></td>
						<td><spring:message code="administration.issue.issuelink.operations"/></td>
					</tr>
					<c:forEach items="${issueLinks}" var="issueLink">
					<tr class="list-table-item">
						<td><span class="title-info">${issueLink.name}</span></td>
						<td>${issueLink.outwardLink}</td>
						<td>${issueLink.inwardLink}</td>
						<td>
							<a class="link" href="administration/issueLink/${issueLink.name}/edit.html">${text_edit}</a>
							<c:if test="${not issueLink.defaultLink}">
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/issueLink/${issueLink.name}/delete', 'Delete?');">${text_delete}</a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
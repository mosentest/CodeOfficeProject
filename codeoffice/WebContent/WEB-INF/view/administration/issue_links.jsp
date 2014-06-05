<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="issue"/>
	</jsp:include>
</div>
<spring:message var="text_edit" code="application.edit" />
<spring:message var="text_delete" code="application.delete" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="link"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.issue.issuelink.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.issue.issuelink.description"/></div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<form:form action="administration/link/create" modelAttribute="issueLink" method="POST">
						<table class="minor-form-table">
							<tr class="minor-form-title-row">
								<td colspan="2"><spring:message code="entity.issueLink.create"/></td>
							</tr>
							<code:formError errors="${formErrors}"/>
							<tr>
								<td class="minor-form-label-col"><spring:message code="entity.issueLink.name"/><span class="icon-required">&nbsp;</span></td>
								<td class="minor-form-input-col"><form:input path="name"/></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="entity.issueLink.outwardLink"/><span class="icon-required">&nbsp;</span></td>
								<td class="minor-form-input-col"><form:input path="outwardLink" class="long-field" /></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="entity.issueLink.inwardLink"/><span class="icon-required">&nbsp;</span></td>
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
						<td><spring:message code="entity.issueLink.name"/></td>
						<td><spring:message code="entity.issueLink.outwardLink"/></td>
						<td><spring:message code="entity.issueLink.inwardLink"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${issueLinks}" var="link">
					<tr class="list-table-item">
						<td><span class="title-info">${link.name}</span></td>
						<td>${link.outwardLink}</td>
						<td>${link.inwardLink}</td>
						<td>
							<a class="link" href="administration/link/edit.html?link=${link.name}">${text_edit}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/link/delete?link=${link.name}', 'Delete?');">${text_delete}</a>
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
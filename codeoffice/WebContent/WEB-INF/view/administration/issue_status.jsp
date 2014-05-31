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
		<jsp:param name="menu" value="status"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.issue.issueLinks"/></div>
				<div class="sub-element-description">Edit your enterprise global settings.</div>
			</div>
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.issue.issuestatus.name"/></td>
						<td><spring:message code="administration.issue.issuestatus.description"/></td>
						<td><spring:message code="administration.issue.issuestatus.icon"/></td>
						<td><spring:message code="administration.issue.issuestatus.color"/></td>
						<td><spring:message code="administration.issue.issuestatus.order"/></td>
						<td><spring:message code="administration.issue.issuestatus.operations"/></td>
					</tr>
					<c:forEach items="${issueStatus}" var="status">
					<tr class="list-table-item">
						<td class="title-info">${status.name}</td>
						<td class="description-info">${status.description}</td>
						<td><img src="assets/img/office/status/${status.icon}.png"/></td>
						<td><span class="color-info" style="background-color: #${status.color};">&nbsp;</span></td>
						<td>${status.statusOrder}</td>
						<td>
							<a class="link" href="administration/issueStatus/${status.name}/edit.html">${text_edit}</a>
							<c:if test="${not status.defaultStatus}">
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/issueStatus/${status.name}/delete', 'Delete?');">${text_delete}</a>
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
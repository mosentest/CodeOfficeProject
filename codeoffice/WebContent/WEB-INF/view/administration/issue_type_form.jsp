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
<spring:message var="text_standard" code="administration.issue.issuetype.standard" />
<spring:message var="text_subtask" code="administration.issue.issuetype.subtask" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="issueType"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title">${issueStatus.name}</div>
				<div class="sub-element-description">${issueStatus.description}</div>
			</div>
			<div class="sub-element-content">
				<table class="form-table">
					<tr>
						<td><spring:message code="administration.issue.issuetype.name"/></td>
						<td><spring:message code="administration.issue.issuetype.icon"/></td>
						<td><spring:message code="administration.issue.issuetype.type"/></td>
						<td><spring:message code="administration.issue.issuetype.relatedSchemes"/></td>
					</tr>
					<tr>
						<td>
							<span class="title-info">${issueType.name}</span>
							<c:if test="${not empty issueType.description}"><br /><span class="description-info">${issueType.description}</span></c:if>
						</td>
						<td><img src="assets/img/office/type/${issueType.icon}.png"/></td>
						<td>${issueType.standard ? text_standard : text_subtask}</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${issueType.issueTypeSchemes}" var="scheme">
								<li><a class="link" href="administration/issueTypeScheme/${scheme.name}.html">${scheme.name}</a></li>
								</c:forEach>
							</ul>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<form id="remoteForm" method="POST"></form>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
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
<spring:message var="text_clone" code="application.clone" />
<spring:message var="text_associate" code="administration.issue.issuetypescheme.associate" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="issueTypeScheme"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.issue.issueTypeSchemes"/></div>
				<div class="sub-element-description">Edit your enterprise global settings.</div>
			</div>
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.issue.issuetypescheme.name"/></td>
						<td><spring:message code="administration.issue.issuetypescheme.issueTypes"/></td>
						<td><spring:message code="administration.issue.issuetypescheme.relatedProjects"/></td>
						<td><spring:message code="administration.issue.issuetypescheme.operations"/></td>
					</tr>
					<c:forEach items="${issueTypeSchemes}" var="scheme">
					<tr class="list-table-item">
						<td><span class="title-info">${scheme.name}</span>
							<c:if test="${not empty scheme.description}"><br />
							<span class="description-info">${scheme.description}</span></c:if>
						</td>
						<td>
							<ul>
								<c:forEach items="${scheme.issueTypes}" var="issueType">
								<li class="imglink">
									<img src="assets/img/office/type/${issueType.icon}.png">
									<span class="minorspace"></span><span>${issueType.name}</span>
								</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${scheme.projects}" var="project">
								<li><a class="link" href="project/${project.code}.html">${project.name}</a></li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<a class="link" href="administration/issueTypeScheme/${scheme.name}/edit.html">${text_edit}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="administration/issueTypeScheme/${scheme.name}/associate.html">${text_associate}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="administration/issueTypeScheme/${scheme.name}/clone">${text_clone}</a>
							<c:if test="${not scheme.defaultScheme}">
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/issueTypeScheme/${scheme.name}/delete', 'Delete?');">${text_delete}</a>
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
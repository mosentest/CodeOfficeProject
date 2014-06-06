<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="issue"/>
	</jsp:include>
</div>
<spring:message var="text_edit" code="application.edit" />
<spring:message var="text_delete" code="application.delete" />
<spring:message var="text_clone" code="application.clone" />
<spring:message var="text_associate" code="application.associate" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="typeScheme"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.issue.issuetypescheme.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.issue.issuetypescheme.description"/></div>
			</div>
			<div class="filter-content">
				<a class="link" href="administration/typeScheme/create.html"><spring:message code="entity.issueTypeScheme.create"/></a>
			</div>
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="entity.issueTypeScheme.name"/></td>
						<td><spring:message code="entity.issueTypeScheme.issueTypes"/></td>
						<td><spring:message code="entity.issueTypeScheme.projects"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${issueTypeSchemes}" var="scheme">
					<tr class="list-table-item">
						<td><span class="title-info">${scheme.name}</span>
							<c:if test="${not empty scheme.description}"><br />
							<span class="description-info">${scheme.description}</span></c:if>
						</td>
						<td>
							<ul class="default-ul-list">
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
							<c:set var="maskURL" value="${codefunction:maskURL(scheme.name)}"/>
							<a class="link" href="administration/typeScheme/edit.html?scheme=${maskURL}">${text_edit}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="administration/typeScheme/associate.html?scheme=${maskURL}">${text_associate}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="administration/typeScheme/clone?scheme=${maskURL}">${text_clone}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/typeScheme/delete?scheme=${maskURL}', 'Delete?');">${text_delete}</a>
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
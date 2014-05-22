<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<spring:message var="text_create" code="version.create_new_version"/>
<security:authorize access="hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_MANAGER', 'ROLE_ADMIN')">
	<c:set var="VC_MANAGER_AUTH" value="true"/>
</security:authorize>
<link rel="stylesheet" type="text/css" href="css/project.css">
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/project_menu.jsp">
				<jsp:param name="menu" value="versions"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<c:if test="${VC_MANAGER_AUTH}">
						<div class="action-field ">
							<code:imagelink image="icon_add" text="${text_create}" link="enterprise/pro_${project.code}/version/create"/>
						</div>
					</c:if>
					<div class="title imglink"><img src="assets/img/office/icon_versions.png"/><span class="titlespan"><spring:message code="project.versions"/></span></div>
					<div class="content">
						<c:if test="${fn:length(versions) eq 0}"><code:info message="project.no_versions"/></c:if>
						<c:if test="${fn:length(versions) gt 0}">
						<table class="default-table center">
							<tr>
								<th></th>
								<th><spring:message code="version.released"/></th>
								<th><spring:message code="version.code"/></th>
								<th><spring:message code="version.estimated_start_date"/></th>
								<th><spring:message code="version.start_date"/></th>
								<th><spring:message code="version.estimated_release_date"/></th>
								<th><spring:message code="version.release_date"/></th>
								<th><spring:message code="version.release_count"/></th>
								<th><spring:message code="version.related_count"/></th>
								<th><spring:message code="version.started"/></th>
								<th><spring:message code="version.description"/></th>
								<c:if test="${VC_MANAGER_AUTH}"><th></th><th></th></c:if>
							</tr>
							<c:forEach items="${versions}" var="version">
							<tr>
								<td>
									<c:if test="${version.released}"><img src="assets/img/office/icon_version_released.png"/></c:if>
									<c:if test="${not version.released}"><img src="assets/img/office/icon_version_unreleased.png"/></c:if>
								</td>
								<td><code:checkmark value="${version.released}" checkmarkOnly="false"/></td>
								<td><a href="enterprise/pro_${project.code}/v_${version.code}">${version.code}</a></td>
								<td><fmt:formatDate value="${version.estimatedStart}" pattern="yyyy-MM-dd"/></td>
								<td><fmt:formatDate value="${version.start}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${version.estimatedRelease}" pattern="yyyy-MM-dd"/></td>
								<td>
									<c:if test="${empty version.delay}"><code:date date="${version.release}"/></c:if>
									<c:if test="${not empty version.delay}">
										<span class="delayed-version"><spring:message code="version.delayed_to"/>:&nbsp;<fmt:formatDate value="${version.release}" pattern="yyyy-MM-dd"/></span>
									</c:if>
								</td>
								<td>${version.noRelease}</td>
								<td>${version.noRelated}</td>
								<td><code:checkmark value="${version.started}" checkmarkOnly="false"/></td>
								<td>${version.description}</td>
								<c:if test="${VC_MANAGER_AUTH}">
								<td class="center"><a class="image-link" href="enterprise/pro_${project.code}/v_${version.code}/edit"><img src="assets/img/icon_edit.png" title="${text_edit}"/></a></td>
								<td class="center"><a class="image-link" href="enterprise/pro_${project.code}/v_${version.code}/delete"><img src="assets/img/icon_remove.png" title="${text_delete}"/></a></td>
								</c:if>
							</tr>
							</c:forEach>
						</table>
						</c:if>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
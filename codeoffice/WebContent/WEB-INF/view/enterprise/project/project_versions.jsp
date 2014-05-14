<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/project_menu.jsp">
				<jsp:param name="menu" value="versions"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/office/icon_versions.png"/><span class="titlespan"><spring:message code="project.p_versions"/></span></div>
					<div class="content">
						<c:if test="${fn:length(versions) eq 0}"><code:info message="project.noversions"/></c:if>
						<c:if test="${fn:length(versions) gt 0}">
						<table class="default-table center">
							<tr>
								<th></th>
								<th><spring:message code="project.v_released"/></th>
								<th><spring:message code="project.v_code"/></th>
								<th><spring:message code="project.v_start"/></th>
								<th><spring:message code="project.v_release"/></th>
								<th><spring:message code="project.v_norelease"/></th>
								<th><spring:message code="project.v_norelated"/></th>
								<th><spring:message code="project.v_started"/></th>
								<th><spring:message code="project.v_description"/></th>
							</tr>
							<c:forEach items="${versions}" var="version">
							<tr>
								<td>
									<c:if test="${version.released}"><img src="img/office/icon_version_released.png"/></c:if>
									<c:if test="${not version.released}"><img src="img/office/icon_version_unreleased.png"/></c:if>
								</td>
								<td><code:checkmark value="${version.released}" checkmarkOnly="false"/></td>
								<td><a href="enterprise/pro_${project.code}/v_${version.code}">${version.code}</a></td>
								<td><code:date date="${version.start}"/></td>
								<td>
									<c:if test="${empty version.delay}"><code:date date="${version.release}"/></c:if>
									<c:if test="${not empty version.delay}">
										<span class="delayed-version"><spring:message code="project.v_delayedto"/>:&nbsp;<code:date date="${version.delay}"/></span>
									</c:if>
								</td>
								<td>${version.noRelease}</td>
								<td>${version.noRelated}</td>
								<td><code:checkmark value="${version.started}" checkmarkOnly="false"/></td>
								<td>${version.description}</td>
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
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
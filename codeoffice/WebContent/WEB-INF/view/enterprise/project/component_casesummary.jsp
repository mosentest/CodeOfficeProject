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
			<jsp:include page="/WEB-INF/view/enterprise/project/component_menu.jsp">
				<jsp:param name="menu" value="casesummary"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/office/icon_statistic.png"/><span class="titlespan"><spring:message code="component.case_summary"/></span></div>
					<div class="content">
						<div class="element-block">
							<div class="subelement">
								<div class="title"><spring:message code="component.status_summary"/></div>
								<div class="content">
									<c:if test="${fn:length(statusMap) eq 0}"><code:info message="component.no_status_summary"/></c:if>
									<c:if test="${fn:length(statusMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${statusMap}" var="status">
										<tr>
											<td class="percent-key imglink"><code:enum value="${status.key}"/></td>
											<td class="percent-value">${status.value}</td>
											<td class="percent-percent"><code:percent number="${status.value}" total="${totalCase}"/></td>
										</tr>
										</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="subelement">
								<div class="title"><spring:message code="component.priority_summary"/></div>
								<div class="content">
									<c:if test="${fn:length(priorityMap) eq 0}"><code:info message="component.no_priority_summary"/></c:if>
									<c:if test="${fn:length(priorityMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${priorityMap}" var="priority">
										<tr>
											<td class="percent-key imglink"><code:enum value="${priority.key}"/></td>
											<td class="percent-value">${priority.value}</td>
											<td class="percent-percent"><code:percent number="${priority.value}" total="${totalCase}"/></td>
										</tr>
										</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="subelement">
								<div class="title"><spring:message code="component.assignee_summary"/></div>
								<div class="content">
									<c:if test="${fn:length(assigneeMap) eq 0}"><code:info message="component.no_assignee_summary"/></c:if>
									<c:if test="${fn:length(assigneeMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${assigneeMap}" var="assignee">
										<tr>
											<td class="percent-key"><code:user user="${assignee.key}" width="30" height="30"/></td>
											<td class="percent-value">${assignee.value}</td>
											<td class="percent-percent"><code:percent number="${assignee.value}" total="${totalCase}"/></td>
										</tr>
										</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
						</div>
						
						<div class="element-block">
							<div class="subelement">
								<div class="title"><spring:message code="component.version_summary"/></div>
								<div class="content">
									<c:if test="${fn:length(versionMap) eq 0}"><code:info message="component.no_version_summary"/></c:if>
									<c:if test="${fn:length(versionMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${versionMap}" var="version">
										<tr>
											<td class="percent-key imglink">
												<c:if test="${version.key.released}"><img src="img/office/icon_version_released.png"/></c:if>
												<c:if test="${not version.key.released}"><img src="img/office/icon_version_unreleased.png"/></c:if>
												<a class="text" href="enterprise/pro_${project.code}/v_${version.key.code}">${version.key.code}</a>
											</td>
											<td class="percent-value">${version.value}</td>
											<td class="percent-percent"><code:percent number="${version.value}" total="${totalCase}"/></td>
										</tr>
										</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="subelement">
								<div class="title"><spring:message code="component.label_summary"/></div>
								<div class="content">
									<c:if test="${fn:length(labelMap) eq 0}"><code:info message="component.no_label_summary"/></c:if>
									<c:if test="${fn:length(labelMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${labelMap}" var="label">
										<tr>
											<td class="percent-key imglink">
												<img src="img/office/icon_label.png" width="20" height="20"/>
												<a class="text" href="enterprise/pro_${project.code}/l_${label.key.label}">${label.key.label}</a>
											</td>
											<td class="percent-value">${label.value}</td>
											<td class="percent-percent"><code:percent number="${label.value}" total="${totalCase}"/></td>
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
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
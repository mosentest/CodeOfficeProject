<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/version_menu.jsp">
				<jsp:param name="menu" value="relatedsummary"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="assets/img/office/icon_statistic.png"/><span class="titlespan"><spring:message code="version.related_case_summary"/></span></div>
					<div class="content">
						<div class="element-block">
							<div class="subelement">
								<div class="title"><spring:message code="version.status_summary"/></div>
								<div class="content">
									<c:if test="${fn:length(statusMap) eq 0}"><code:info message="version.no_label_summary"/></c:if>
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
								<div class="title"><spring:message code="version.priority_summary"/></div>
								<div class="content">
									<c:if test="${fn:length(priorityMap) eq 0}"><code:info message="version.no_priority_summary"/></c:if>
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
								<div class="title"><spring:message code="version.assignee_summary"/></div>
								<div class="content">
									<c:if test="${fn:length(assigneeMap) eq 0}"><code:info message="version.no_assignee_summary"/></c:if>
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
								<div class="title"><spring:message code="version.component_summary"/></div>
								<div class="content">
									<c:if test="${fn:length(componentMap) eq 0}"><code:info message="version.no_component_summary"/></c:if>
									<c:if test="${fn:length(componentMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${componentMap}" var="component">
										<tr>
											<td class="percent-key imglink">
												<img src="assets/img/office/icon_component.png" width="20" height="20"/>
												<a class="text" href="enterprise/pro_${project.code}/m_${component.key.code}">${component.key.name}</a>
											</td>
											<td class="percent-value">${component.value}</td>
											<td class="percent-percent"><code:percent number="${component.value}" total="${totalCase}"/></td>
										</tr>
										</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="subelement">
								<div class="title"><spring:message code="version.label_summary"/></div>
								<div class="content">
									<c:if test="${fn:length(labelMap) eq 0}"><code:info message="version.no_label_summary"/></c:if>
									<c:if test="${fn:length(labelMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${labelMap}" var="label">
										<tr>
											<td class="percent-key imglink">
												<img src="assets/img/office/icon_label.png" width="20" height="20"/>
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
<jsp:include page="/WEB-INF/view/footer.jsp" />
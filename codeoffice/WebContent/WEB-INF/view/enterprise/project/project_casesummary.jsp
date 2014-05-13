<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="co" uri="http://www.codeoffice.com/colib"%>
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
				<jsp:param name="menu" value="casesummary"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/office/icon_statistic.png"/><span class="titlespan"><spring:message code="project.p_casesummary"/></span></div>
					<div class="content">
						<div class="element-block">
							<div class="subelement">
								<div class="title"><spring:message code="project.statussummary"/></div>
								<div class="content">
									<c:if test="${fn:length(statusMap) eq 0}">
										<spring:message var="message" code="project.nocases"/>
										<co:info message="${message}"/>
									</c:if>
									<c:if test="${fn:length(statusMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${statusMap}" var="status">
										<tr>
											<td class="percent-key imglink">
												<co:caseEnum value="${status.key}"/>
												<span class="text"><spring:message code="${status.key.code}"/></span>
											</td>
											<td class="percent-value">${status.value}</td>
											<td class="percent-percent"><co:percent number="${status.value}" total="${totalCase}"/></td>
										</tr>
										</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="subelement">
								<div class="title"><spring:message code="project.unresolved"/>:&nbsp;<spring:message code="project.bypriority"/></div>
								<div class="content">
									<c:if test="${fn:length(priorityMap) eq 0}">
										<spring:message var="message" code="project.nocases"/>
										<co:info message="${message}"/>
									</c:if>
									<c:if test="${fn:length(priorityMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${priorityMap}" var="priority">
										<tr>
											<td class="percent-key imglink">
												<spring:message var="message" code="${priority.key.code}"/>
												<co:caseEnum value="${priority.key}" text="message"/>
											</td>
											<td class="percent-value">${priority.value}</td>
											<td class="percent-percent"><co:percent number="${priority.value}" total="${totalCase}"/></td>
										</tr>
										</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="subelement">
								<div class="title"><spring:message code="project.unresolved"/>:&nbsp;<spring:message code="project.byassignee"/></div>
								<div class="content">
									<c:if test="${fn:length(assigneeMap) eq 0}">
										<spring:message var="message" code="project.nocases"/>
										<co:info message="${message}"/>
									</c:if>
									<c:if test="${fn:length(assigneeMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${assigneeMap}" var="assignee">
										<tr>
											<td class="percent-key"><co:user user="${assignee.key}" width="30" height="30"/></td>
											<td class="percent-value">${assignee.value}</td>
											<td class="percent-percent"><co:percent number="${assignee.value}" total="${totalCase}"/></td>
										</tr>
										</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
						</div>
						
						<div class="element-block">
							<div class="subelement">
								<div class="title"><spring:message code="project.unresolved"/>:&nbsp;<spring:message code="project.byversion"/></div>
								<div class="content">
									<c:if test="${fn:length(versionMap) eq 0}">
										<spring:message var="message" code="project.nocases"/>
										<co:info message="${message}"/>
									</c:if>
									<c:if test="${fn:length(versionMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${versionMap}" var="version">
										<tr>
											<td class="percent-key imglink">
												<c:if test="${version.key.released}"><img src="img/office/icon_version_released.png"/></c:if>
												<c:if test="${not version.key.released}"><img src="img/office/icon_version_unreleased.png"/></c:if>
												<a class="text" href="enterprise/project/${project.code}/version/${version.key.code}">${version.key.code}</a>
											</td>
											<td class="percent-value">${version.value}</td>
											<td class="percent-percent"><co:percent number="${version.value}" total="${totalCase}"/></td>
										</tr>
										</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="subelement">
								<div class="title"><spring:message code="project.unresolved"/>:&nbsp;<spring:message code="project.bycomponent"/></div>
								<div class="content">
									<c:if test="${fn:length(componentMap) eq 0}">
										<spring:message var="message" code="project.nocases"/>
										<co:info message="${message}"/>
									</c:if>
									<c:if test="${fn:length(componentMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${componentMap}" var="component">
										<tr>
											<td class="percent-key imglink">
												<img src="img/office/icon_component.png" width="20" height="20"/>
												<a class="text" href="enterprise/project/${project.code}/component/${component.key.id}">${component.key.name}</a>
											</td>
											<td class="percent-value">${component.value}</td>
											<td class="percent-percent"><co:percent number="${component.value}" total="${totalCase}"/></td>
										</tr>
										</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="subelement">
								<div class="title"><spring:message code="project.unresolved"/>:&nbsp;<spring:message code="project.bylabel"/></div>
								<div class="content">
									<c:if test="${fn:length(labelMap) eq 0}">
										<spring:message code="project.nocases"/>
										<co:info message="${message}"/>
									</c:if>
									<c:if test="${fn:length(labelMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${labelMap}" var="label">
										<tr>
											<td class="percent-key imglink">
												<img src="img/office/icon_label.png" width="20" height="20"/>
												<a class="text" href="enterprise/project/${project.code}/label/${label.key.label}">${label.key.label}</a>
											</td>
											<td class="percent-value">${label.value}</td>
											<td class="percent-percent"><co:percent number="${label.value}" total="${totalCase}"/></td>
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
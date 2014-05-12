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
									<c:if test="${fn:length(statusMap.keySet) eq 0}">
										<div class="info-element imglink">
											<img src="img/info.png"/>
											<span><spring:message code="project.nocases"/></span>
										</div>
									</c:if>
									<c:if test="${fn:length(statusMap.keySet) gt 0}">
									<table class="default-table">
										<c:forEach items="${statusMap.keySet}" var="key">
										<tr>
											<c:set var="keycode"><spring:message code="${key.code}"/></c:set>
											<td class="percent-key"><co:caseEnum value="${key}" text="${keydcode}" imageOnly="false"/></td>
											<td class="percent-value">${statusMap[key]}</td>
											<td class="percent-percent"><co:percent number="${statusMap[key]}" total="${totalCase}"/></td>
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
									<c:if test="${fn:length(priorityMap.keySet) eq 0}">
										<div class="info-element imglink">
											<img src="img/info.png"/>
											<span><spring:message code="project.nocases"/></span>
										</div>
									</c:if>
									<c:if test="${fn:length(priorityMap.keySet) gt 0}">
									<table class="default-table">
										<c:forEach items="${priorityMap.keySet}" var="key">
										<tr>
											<c:set var="keycode"><spring:message code="${key.code}"/></c:set>
											<td class="percent-key"><co:caseEnum value="${key}" text="${keydcode}" imageOnly="false"/></td>
											<td class="percent-value">${priorityMap[key]}</td>
											<td class="percent-percent"><co:percent number="${priorityMap[key]}" total="${totalCase}"/></td>
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
									<c:if test="${fn:length(assigneeMap.keySet) eq 0}">
										<div class="info-element imglink">
											<img src="img/info.png"/>
											<span><spring:message code="project.nocases"/></span>
										</div>
									</c:if>
									<c:if test="${fn:length(assigneeMap.keySet) gt 0}">
									<table class="default-table">
										<c:forEach items="${assigneeMap.keySet}" var="key">
										<tr>
											<td class="percent-key"><co:user user="${key}" width="30" height="30"/></td>
											<td class="percent-value">${assigneeMap[key]}</td>
											<td class="percent-percent"><co:percent number="${assigneeMap[key]}" total="${totalCase}"/></td>
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
									<c:if test="${fn:length(versionMap.keySet) eq 0}">
										<div class="info-element imglink">
											<img src="img/info.png"/>
											<span><spring:message code="project.nocases"/></span>
										</div>
									</c:if>
									<c:if test="${fn:length(versionMap.keySet) gt 0}">
									<table class="default-table">
										<c:forEach items="${versionMap.keySet}" var="key">
										<tr>
											<td class="percent-key">
												<c:if test="${key.released}"><img src="img/office/icon_version_released.png"/></c:if>
												<c:if test="${not key.released}"><img src="img/office/icon_version_unreleased.png"/></c:if>
												<a href="enterprise/project/${project.code}/version/${key.code}">${key.code}</a>
											</td>
											<td class="percent-value">${versionMap[key]}</td>
											<td class="percent-percent"><co:percent number="${versionMap[key]}" total="${totalCase}"/></td>
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
									<c:if test="${fn:length(componentMap.keySet) eq 0}">
										<div class="info-element imglink">
											<img src="img/info.png"/>
											<span><spring:message code="project.nocases"/></span>
										</div>
									</c:if>
									<c:if test="${fn:length(componentMap.keySet) gt 0}">
									<table class="default-table">
										<c:forEach items="${componentMap.keySet}" var="key">
										<tr>
											<td class="percent-key">
												<img src="img/office/icon_component.png" width="20" height="20"/>
												<a href="enterprise/project/${project.code}/component/${key.id}">${key.name}</a>
											</td>
											<td class="percent-value">${componentMap[key]}</td>
											<td class="percent-percent"><co:percent number="${componentMap[key]}" total="${totalCase}"/></td>
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
									<c:if test="${fn:length(labelMap.keySet) eq 0}">
										<div class="info-element imglink">
											<img src="img/info.png"/>
											<span><spring:message code="project.nocases"/></span>
										</div>
									</c:if>
									<c:if test="${fn:length(labelMap.keySet) gt 0}">
									<table class="default-table">
										<c:forEach items="${labelMap.keySet}" var="key">
										<tr>
											<td class="percent-key"><a href="enterprise/project/${project.code}/label/${key.label}">${key.label}</a></td>
											<td class="percent-value">${labelMap[key]}</td>
											<td class="percent-percent"><co:percent number="${labelMap[key]}" total="${totalCase}"/></td>
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
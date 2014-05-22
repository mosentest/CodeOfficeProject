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
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/project_menu.jsp">
				<jsp:param name="menu" value="dashboard"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="assets/img/office/icon_dashboard.png"/><span class="titlespan"><spring:message code="project.dashboard"/></span></div>
					<div class="content">
						<div class="element-block">
							<div class="content">
							<div class="element">
								<div class="title"><spring:message code="project.case_stream"/></div>
								<div class="content">
								<c:if test="${caseStream.totalElements eq 0}"><code:info message="project.no_case"/></c:if>
								<c:if test="${caseStream.totalElements gt 0}">
									<table class="default-table case-table">
										<tr>
											<th class="fit-cell"></th>
											<th style="text-align: center;"><spring:message code="case.code"/></th>
											<th><spring:message code="case.summary"/></th>
											<th><spring:message code="case.status"/></th>
										</tr>
										<c:forEach items="${caseStream.content}" var="c">
										<tr>
											<td class="fit-cell"><code:enum image="${c.priority}"/></td>
											<td class="center"><a href="enterprise/pro_${project.code}/${c.code}">${c.code}</a></td>
											<td>${c.summary}</td>
											<td><code:enum value="${c.status}"/></td>
										</tr>
										</c:forEach>
										<tr><td colspan="4"><code:page page="${caseStream}" url="enterprise/pro_${project.code}/case"/></td></tr>
									</table>
								</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="subelement">
								<div class="title"><spring:message code="project.activity_stream"/></div>
								<div class="content">
								<c:if test="${activityStream.totalElements eq 0}"><code:info message="project.no_activity_stream"/></c:if>
								<c:if test="${activityStream.totalElements gt 0}">
								</c:if>
								</div>
							</div>
							</div>
						</div>
						<div class="element-block">
							<div class="content">
							<div class="element">
								<div class="title"><spring:message code="project.case_inprogress"/></div>
								<div class="content">
								<c:if test="${caseInProgress.totalElements eq 0}"><code:info message="project.no_case"/></c:if>
								<c:if test="${caseInProgress.totalElements gt 0}">
									<table class="default-table case-table">
										<tr>
											<th class="fit-cell"></th>
											<th style="text-align: center;"><spring:message code="case.code"/></th>
											<th><spring:message code="case.summary"/></th>
											<th><spring:message code="case.status"/></th>
										</tr>
										<c:forEach items="${caseInProgress.content}" var="c">
										<tr>
											<td class="fit-cell"><code:enum image="${c.priority}"/></td>
											<td class="center"><a href="enterprise/pro_${project.code}/${c.code}">${c.code}</a></td>
											<td>${c.summary}</td>
											<td><code:enum value="${c.status}"/></td>
										</tr>
										</c:forEach>
										<tr><td colspan="4"><code:page page="${caseInProgress}" url="enterprise/pro_${project.code}/case"/></td></tr>
									</table>
								</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="element">
								<div class="title"><spring:message code="project.assigned_to_me"/></div>
								<div class="content">
								<c:if test="${assignedCase.totalElements eq 0}"><code:info message="project.no_case"/></c:if>
								<c:if test="${assignedCase.totalElements gt 0}">
									<table class="default-table case-table">
										<tr>
											<th class="fit-cell"></th>
											<th style="text-align: center;"><spring:message code="case.code"/></th>
											<th><spring:message code="case.summary"/></th>
											<th><spring:message code="case.status"/></th>
										</tr>
										<c:forEach items="${assignedCase.content}" var="c">
										<tr>
											<td class="fit-cell"><code:enum image="${c.priority}"/></td>
											<td class="center"><a href="enterprise/pro_${project.code}/${c.code}">${c.code}</a></td>
											<td>${c.summary}</td>
											<td><code:enum value="${c.status}"/></td>
										</tr>
										</c:forEach>
											<tr><td colspan="4"><code:page page="${assignedCase}" url="enterprise/pro_${project.code}/case"/></td></tr>
									</table>
								</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="element">
								<div class="title"><spring:message code="project.assignee_status"/></div>
								<div class="content">
								<c:if test="${fn:length(assigneeStatus) eq 0}"><code:info message="project.no_assignee_status"/></c:if>
								<c:if test="${fn:length(assigneeStatus) gt 0}">
									<table class="default-table center table-border-bottom">
										<tr>
											<th><spring:message code="case.assignee"/></th>
											<th><spring:message code="case.inprogress"/></th>
											<th><spring:message code="case.resolved"/></th>
											<th><spring:message code="case.closed"/></th>
											<th><spring:message code="case.total"/></th>
										</tr>
										<c:forEach items="${assigneeStatus}" var="status">
										<tr>
											<td class="left p_3"><code:user user="${status.user}" showImage="false" showSpace="false"/></td>
											<td>${status.inProgress}</td>
											<td>${status.resolved}</td>
											<td>${status.closed}</td>
											<td>${status.total}</td>
										</tr>
										</c:forEach>
									</table>
								</c:if>
								</div>
							</div>
							<div class="sep-30"></div>
							<div class="subelement">
								<div class="title"><spring:message code="project.status_summary"/></div>
								<div class="content">
								<c:if test="${fn:length(statusMap) eq 0}"><code:info message="project.no_status_summary"/></c:if>
								<c:if test="${fn:length(statusMap) gt 0}">
									<table class="default-table nowrap">
										<c:forEach items="${statusMap}" var="status">
										<tr>
											<td class="percent-key imglink"><code:enum value="${status.key}"/></td>
											<td class="percent-value">${status.value}</td>
											<td class="percent-percent"><code:percent number="${status.value}" total="${project.noCase}"/></td>
										</tr>
										</c:forEach>
									</table>
								</c:if>
								</div>
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
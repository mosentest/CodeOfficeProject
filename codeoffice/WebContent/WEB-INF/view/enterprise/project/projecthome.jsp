<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<div id="content">
	<div class="element">
		<div class="info"><spring:message code="project.home"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/projecthome_menu.jsp">
				<jsp:param name="menu" value="home"/>
			</jsp:include>
			<div class="maincontent">
				<div class="element-block">
					<div class="subelement">
						<div class="title"><spring:message code="project.c_assignedtome"/></div>
						<div class="content">
							<c:if test="${fn:length(assignedCases) eq 0}"><code:info message="project.nocases"/></c:if>
							<c:if test="${fn:length(assignedCases) gt 0}">
								<table class="default-table left">
									<tr>
										<th class="fit-cell"></th>
										<th class="fit-cell"><spring:message code="project.c_code"/></th>
										<th><spring:message code="project.c_summary"/></th>
										<th class="fit-cell"><spring:message code="project.c_status"/></th>
									</tr>
									<c:forEach items="assignedCases" var="c">
									<tr>
										<td class="fit-cell"><code:enum image="${c.priority}" /></td>
										<td class="fit-cell"><a href="enterprise/cas_${c.code}">${c.code}</a></td>
										<td>${c.summary}</td>
										<td class="fit-cell"><code:enum image="${c.status}"/></td>
									</tr>
									</c:forEach>
								</table>
							</c:if>
						</div>
					</div>
					<div class="sep-30"></div>
					<div class="subelement">
						<div class="title"><spring:message code="project.worknotes"/></div>
						<div class="content">
							<c:if test="${fn:length(workNotes) eq 0}"><code:info message="project.noworknotes"/></c:if>
							<c:if test="${fn:length(workNotes) gt 0}">
								<table class="default-table left">
									<tr>
										<th class="fit-cell"><spring:message code="project.w_updated"/></th>
										<th><spring:message code="project.w_summary"/></th>
										<th class="fit-cell"><spring:message code="project.w_creator"/></th>
									</tr>
									<c:forEach items="${workNotes}" var="workNote">
									<tr>
										<td class="fit-cell"><code:date date="${workNote.update}" format="yy-MM-dd HH:mm"/></td>
										<td>${workNote.summary}</td>
										<td class="fit-cell"><code:user user="${workNote.creator}"/></td>
									</tr>
									</c:forEach>
								</table>
							</c:if>
						</div>
					</div>
					<div class="sep-30"></div>
					<div class="subelement">
						<div class="title"><spring:message code="project.statussummary"/></div>
						<div class="content">
							<c:if test="${fn:length(statusSummary) eq 0}"><code:info message="project.nostatusavailable"/></c:if>
							<c:if test="${fn:length(statusSummary) gt 0}">
								<table class="default-table">
									<tr>
										<th class="percent-key"></th>
										<th class="percent-value"></th>
										<th class="percent-percent"></th>
									</tr>
									<c:forEach items="${statusSummary}" var="status">
									<tr>
										<td class="percent-key"><code:enum value="${status.key}"/></td>
										<td class="percent-value">${status.value}</td>
										<td class="percent-percent">
											<code:percent number="${status.value}" total="${totalCaseCount}"/>
										</td>
									</tr>
									</c:forEach>
								</table>
							</c:if>
						</div>
					</div>
				</div>
			
				<div class="element-block">
					<div class="subelement">
						<div class="title"><spring:message code="project.p_cases"/></div>
						<div class="content">
							<c:if test="${casePage.totalElements eq 0}"><code:info message="project.nocases"/></c:if>
							<c:if test="${casePage.totalElements gt 0}">
								<table class="default-table">
									<tr>
										<th class="fit-cell"></th>
										<th class="fit-cell"><spring:message code="project.c_code"/></th>
										<th><spring:message code="project.c_summary"/></th>
										<th class="fit-cell"><spring:message code="project.c_assignee"/></th>
										<th class="fit-cell"><spring:message code="project.c_status"/></th>
									</tr>					
									<c:forEach items="${casePage.content}" var="c">
									<tr>
										<td class="fit-cell"><img src="img/enterprise/office/${c.priority.code}.png"/></td>
										<td class="fit-cell"><a href="enterprise/cas_${c.code}">${c.code}</a></td>
										<td>${c.summary}</td>
										<td class="fit-cell"><code:user user="${c.assignee}" showImage="false" showSpace="false"/></td>
										<td class="fit-cell"><img src="img/enterprise/office/${c.status.code}.png"/></td>
									</tr>
									</c:forEach>				
								</table>
							</c:if>
						</div>
					</div>
					<div class="sep-30"></div>
					<div class="subelement">
						<div class="title"><spring:message code="project.currentprojects"/></div>
						<div class="content">
							<c:if test="${fn:length(currentProjects) eq 0}"><code:info message="project.youarenotworkingonproject"/></c:if>
							<c:if test="${fn:length(currentProjects) gt 0}">
								<table class="default-table">
									<tr>
										<th class="fit-cell"><spring:message code="project.p_code"/></th>
										<th><spring:message code="project.p_name"/></th>
									</tr>
									<c:forEach items="${currentProjects}" var="project">
										<tr>
											<td class="fit-cell"><a href="enterprise/pro_${project.code}">${project.code}</a></td>
											<td>${project.name}</td>
										</tr>
									</c:forEach>
								</table>
							</c:if>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
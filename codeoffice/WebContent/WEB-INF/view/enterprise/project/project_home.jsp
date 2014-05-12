<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="co" uri="http://www.codeoffice.com/colib"%>
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
							<c:if test="${fn:length(assignedCases) eq 0}">
								<div class="info-element imglink">
									<img src="img/info.png"/>
									<span><spring:message code="project.nocases"/></span>
								</div>
							</c:if>
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
										<td class="fit-cell"><co:caseEnum value="${c.priority}" imageOnly="true"/></td>
										<td class="fit-cell"><a href="enterprise/project/case/${c.code}">${c.code}</a></td>
										<td>${c.summary}</td>
										<td class="fit-cell"><co:caseEnum value="${c.status}" imageOnly="true"/></td>
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
							<c:if test="${fn:length(workNotes) eq 0}">
								<div class="info-element imglink">
									<img src="img/info.png"/>
									<span><spring:message code="project.noworknotes"/></span>
								</div>
							</c:if>
							<c:if test="${fn:length(workNotes) gt 0}">
								<table class="default-table left">
									<tr>
										<th class="fit-cell"><spring:message code="project.w_updated"/></th>
										<th><spring:message code="project.w_summary"/></th>
										<th class="fit-cell"><spring:message code="project.w_creator"/></th>
									</tr>
									<c:forEach items="${workNotes}" var="workNote">
									<tr>
										<td class="fit-cell"><co:date date="${workNote.update}" format="yy-MM-dd HH:mm"/></td>
										<td>${workNote.summary}</td>
										<td class="fit-cell"><co:user user="${workNote.creator}"/></td>
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
							<c:if test="${fn:length(statusSummary.keySet) eq 0}">
								<div class="info-element imglink">
									<img src="img/info.png"/>
									<span><spring:message code="project.nostatusavailable"/></span>
								</div>
							</c:if>
							<c:if test="${fn:length(statusSummary.keySet) gt 0}">
								<table class="default-table">
									<tr>
										<th class="percent-key"></th>
										<th class="percent-value"></th>
										<th class="percent-percent"></th>
									</tr>
									<c:forEach items="${statusSummary.keySet}" var="key">
									<tr>
										<td class="percent-key"><img src="img/enterprise/office/${key.code}.png"/></td>
										<td class="percent-value">${statusSummary[key]}</td>
										<td class="percent-percent">
											<co:percent number="${statusSummary[key]}" total="${totalCaseCount}"/>
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
						<div class="title"><spring:message code="project.allcase"/></div>
						<div class="content">
							<c:if test="${fn:length(allCases) eq 0}">
								<div class="info-element imglink">
									<img src="img/info.png"/>
									<span><spring:message code="project.nocases"/></span>
								</div>
							</c:if>
							<c:if test="${fn:length(allCases) gt 0}">
								<table class="default-table">
									<tr>
										<th class="fit-cell"></th>
										<th class="fit-cell"><spring:message code="project.c_code"/></th>
										<th><spring:message code="project.c_summary"/></th>
										<th class="fit-cell"><spring:message code="project.c_assignee"/></th>
										<th class="fit-cell"><spring:message code="project.c_status"/></th>
									</tr>					
									<c:forEach items="${allCases}" var="c">
									<tr>
										<td class="fit-cell"><img src="img/enterprise/office/${c.priority.code}.png"/></td>
										<td class="fit-cell"><a href="enterprise/project/case/${c.code}">${c.code}</a></td>
										<td>${c.summary}</td>
										<td class="fit-cell"><co:user user="${c.assignee}" showImage="false" showSpace="false"/></td>
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
							<c:if test="${fn:length(currentProjects) eq 0}">
								<div class="info-element imglink">
									<img src="img/info.png"/>
									<span><spring:message code="project.youarenotworkingonproject"/></span>
								</div>
							</c:if>
							<c:if test="${fn:length(currentProjects) gt 0}">
								<table class="default-table">
									<tr>
										<th class="fit-cell"><spring:message code="project.p_code"/></th>
										<th><spring:message code="project.p_name"/></th>
									</tr>
									<c:forEach items="${currentProjects}" var="project">
										<tr>
											<td class="fit-cell"><a href="enterprise/project/${project.code}">${project.code}</a></td>
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
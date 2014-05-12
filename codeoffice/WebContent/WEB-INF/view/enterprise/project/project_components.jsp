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
				<jsp:param name="menu" value="components"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/office/icon_component.png"/><span class="titlespan"><spring:message code="project.p_components"/></span></div>
					<div class="content">
						<c:if test="${fn:length(components) eq 0}">
							<div class="info-element imglink">
								<img src="img/info.png"/>
								<span><spring:message code="project.nocomponents"/></span>
							</div>
						</c:if>
						<c:if test="${fn:length(components) gt 0}">
						<table class="default-table left-header">
						<tr>
							<th></th>
							<th><spring:message code="project.m_name"/></th>
							<th><spring:message code="project.m_lead"/></th>
							<th><spring:message code="project.m_default_reporter"/></th>
							<th><spring:message code="project.m_default_assignee"/></th>
							<th><spring:message code="project.m_nocase"/></th>
							<th><spring:message code="project.m_description"/></th>
						</tr>
						<c:forEach items="${components}" var="component">
						<tr>
							<td><img src="img/office/icon_component.png" width="20" height="20"/></td>
							<td><a href="enterprise/project/${project.code}/component/${component.id}">${component.name}</a></td>
							<td><co:user user="${component.lead}"/></td>
							<td><co:user user="${component.defaultReporter}"/></td>
							<td><co:user user="${component.defaultAssignee}"/></td>
							<td>${component.noCase}</td>
							<td>${component.description}</td>
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
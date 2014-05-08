<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/view/header.jsp" />
<div id="main_div">
<div class="dashboard_element form">
	<div class="dashboard_element_title"><spring:message code="project.createproject"/></div>
	<div class="dashboard_element_content">
		<table>
			<thead>
				<tr>
					<th class="standardHeader"><spring:message code="projectList.code"/></th>
					<th class="standardHeader"><spring:message code="projectList.name"/></th>
					<th class="standardHeader"><spring:message code="projectList.summary"/></th>
					<th class="standardHeader"><spring:message code="projectList.description"/></th>
					<th class="standardHeader"><spring:message code="projectList.createdate"/></th>
					<th class="standardHeader"><spring:message code="projectList.updatedate"/></th>
				</tr>
			</thead>
			<c:forEach items="${projectList}" var="project">
				<tr>
					<td>${project.code}</td>
					<td>${project.name}</td>
					<td>
						<c:if test="${fn:length(project.summary) > 10}">${fn:substring(project.summary, 0, 10)}...</c:if>
						<c:if test="${fn:length(project.summary) <= 10}">${project.summary}</c:if>
					</td>
					<td>
						<c:if test="${fn:length(project.description) > 30}">${fn:substring(project.description, 0, 30)}...</c:if>
						<c:if test="${fn:length(project.description) <= 30}">${project.description}</c:if>
					</td>
					<td><fmt:formatDate value="${project.create}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${project.update}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
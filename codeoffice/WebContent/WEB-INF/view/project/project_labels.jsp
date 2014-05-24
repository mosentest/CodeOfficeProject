<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/project_menu.jsp">
				<jsp:param name="menu" value="labels"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="assets/img/office/icon_labels.png"/><span class="titlespan"><spring:message code="project.labels"/></span></div>
					<div class="content">
						<c:if test="${fn:length(labels) eq 0}"><code:info title="project.no_labels"/></c:if>
						<c:if test="${fn:length(labels) gt 0}">
						<table class="default-table center">
							<tr>
								<th><spring:message code="label.label"/></th>
								<th><spring:message code="label.case_count"/></th>
							</tr>
							<c:forEach items="${labels}" var="label">
							<tr>
								<td><a href="enterprise/pro_${project.code}/l_${label.label}">${label.label}</a></td>
								<td>${label.count}</td>
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
<jsp:include page="/WEB-INF/view/footer.jsp" />
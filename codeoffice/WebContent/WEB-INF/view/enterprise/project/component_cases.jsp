<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<script type="text/javascript">
	function submitGoto() {
		var value = $('#goto').val();
		if (isNaN(value)) {
			alert("Invalid page");
			return;
		}
		if (value <= 1) {
			window.location = 'enterprise/pro_${project.code}/case';
		} else {
			window.location = 'enterprise/pro_${project.code}/case/' + value;
		}
	}
</script>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/component_menu.jsp">
				<jsp:param name="menu" value="cases"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/office/icon_cases.png"/><span class="titlespan"><spring:message code="component.cases"/></span></div>
					<div class="content">
						<c:if test="${relatedCase.totalElements eq 0}"><code:info message="component.no_case"/></c:if>
						<c:if test="${relatedCase.totalElements gt 0}">
							<div class="element">
								<div class="title"><spring:message code="component.related_cases"/></div>
								<div class="content">
								<table class="default-table case-table">
									<tr>
										<th class="fit-cell"></th>
										<th style="text-align: center;"><spring:message code="case.code"/></th>
										<th><spring:message code="case.summary"/></th>
										<th><spring:message code="case.updated"/></th>
										<th><spring:message code="case.assignee"/></th>
										<th><spring:message code="case.status"/></th>
									</tr>
									<c:forEach items="${relatedCase.content}" var="c">
									<tr>
										<td class="fit-cell"><code:enum value="${c.priority}"/></td>
										<td class="center"><a href="enterprise/pro_${project.code}/${c.code}">${c.code}</a></td>
										<td>${c.summary}</td>
										<td><code:date date="${c.update}"/></td>
										<td><code:user user="${c.assignee}" showImage="false" showSpace="false"/></td>
										<td><code:enum value="${c.status}"/></td>
									</tr>
									</c:forEach>
									<tr><td colspan="6"><code:page page="${relatedCase}" url="enterprise/pro_${project.code}/case"/></td></tr>
								</table>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="co" uri="http://www.codeoffice.com/colib"%>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<script type="text/javascript">
	function submit() {
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
			<jsp:include page="/WEB-INF/view/enterprise/project/project_menu.jsp">
				<jsp:param name="menu" value="cases"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/office/icon_cases.png"/><span class="titlespan"><spring:message code="project.p_cases"/></span></div>
					<div class="content">
						<c:if test="${casePage.totalElements eq 0}"><spring:message var="message" code="project.nocases"/><co:info message="${message}"/></c:if>
						<c:if test="${casePage.totalElements gt 0}">
							<div class="element">
								<div class="title">&nbsp;</div>
								<div class="content">
								<table class="default-table case-table">
									<tr>
										<th></th>
										<th style="text-align: center;"><spring:message code="project.c_code"/></th>
										<th><spring:message code="project.c_summary"/></th>
										<th><spring:message code="project.c_updated"/></th>
										<th><spring:message code="project.c_assignee"/></th>
										<th><spring:message code="project.c_status"/></th>
									</tr>
									<c:forEach items="${casePage.content}" var="c">
									<tr>
										<td class="center"><co:caseEnum value="${c.priority}"/></td>
										<td class="center"><a href="enterprise/pro_${project.code}/${c.code}">${c.code}</a></td>
										<td>${c.summary}</td>
										<td><co:date date="${c.update}"/></td>
										<td><co:user user="${c.assignee}" showImage="false" showSpace="false"/></td>
										<td><co:caseEnum value="${c.status}"/><span class="text"><spring:message code="${c.status.code}"/></span></td>
									</tr>
									</c:forEach>
									<tr><td colspan="6">
									<div class="page-holder">
										<span><spring:message code="project.totalelements" arguments="${casePage.totalElements}"/></span>
										<span class="minorspace"></span>
										<span><a href="enterprise/pro_${project.code}/case"><spring:message code="project.firstpage"/></a></span>
										<span class="minorspace"></span>
										<c:if test="${casePage.number + 1 le 1}">
										<span><spring:message code="project.previouspage"/></span>
										</c:if>
										<c:if test="${casePage.number + 1 gt 1}">
										<span><a href="enterprise/pro_${project.code}/case/${casePage.number}"><spring:message code="project.previouspage"/></a></span>
										</c:if>
										<span class="minorspace"></span>
										<span>${casePage.number + 1}&nbsp;/&nbsp;${casePage.totalPages}</span>
										<span class="minorspace"></span>
										<c:if test="${casePage.number + 1 eq casePage.totalPages}">
										<span><spring:message code="project.nextpage"/></span>
										</c:if>
										<c:if test="${casePage.number + 1 lt casePage.totalPages}">
										<span><a href="enterprise/pro_${project.code}/case/${casePage.number + 2}"><spring:message code="project.nextpage"/></a></span>
										</c:if>
										<span class="minorspace"></span>
										<span><a href="enterprise/pro_${project.code}/case/${casePage.totalPages}"><spring:message code="project.lastpage"/></a></span>
										<span class="minorspace"></span>
										<span>
											<span><spring:message code="project.goto"/></span>
											<span class="minorspace"></span>
											<input type="text" id="goto" style="width: 36px" maxlength="3"/>
											<span class="minorspace"></span>
											<input class="button" value="<spring:message code="project.go"/>" style="width: 50px;" onclick="submit();"/>
										</span>
									</div>
									</td></tr>
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
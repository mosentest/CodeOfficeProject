<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<script type="text/javascript">
$(document).ready(function() {
	$('.datepicker').datepicker({
		showOn: 'button',
		dateFormat: 'yy-mm-dd',
		buttonImage: 'img/icon_calendar.png',
		buttonText: 'Show calendar',
		buttonImageOnly: true
	});
});
</script>
<link rel="stylesheet" type="text/css" href="css/project.css">
<spring:message var="text_update" code="application.update"/>
<spring:message var="text_create" code="application.create"/>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/version_menu.jsp"/>
			<div class="maincontent">
				<div class="element">
					<div class="title"><spring:message code="${edit ? 'version.edit_version' : 'version.create_version'}"/></div>
					<div class="content">
						<form:form action="" id="merge-form" modelAttribute="version" method="POST">
						<table class="default-table">
							<c:if test="${edit}"><tr><td><form:hidden path="id"/></td></tr></c:if>
							<tr>
								<td class="label-header"><spring:message code="version.code"/></td>
								<td><form:input path="code"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="version.estimated_start_date"/></td>
								<td class="imglink"><form:input path="estimatedStart" cssClass="datepicker"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="version.estimated_release_date"/></td>
								<td class="imglink"><form:input path="estimatedRelease" cssClass="datepicker"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="version.delayed_to"/></td>
								<td class="imglink"><form:input path="delay" cssClass="datepicker"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="version.description"/></td>
								<td><form:textarea path="description" cols="30" rows="6"/></td>
							</tr>
							<tr>
								<td colspan="2"><input class="button largebutton" type="submit" value="${edit ? text_update : text_create}"/></td>
							</tr>
						</table>
						</form:form>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
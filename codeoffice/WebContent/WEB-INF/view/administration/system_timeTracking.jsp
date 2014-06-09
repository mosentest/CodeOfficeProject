<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="system"/>
	</jsp:include>
</div>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/system_menu.jsp">
		<jsp:param name="menu" value="timetracking"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title imglink">
					<span><spring:message code="administration.system.timeTracking.title"/></span>
					<input type="button" class="button" onclick="javascript:url('/administration/timeTracking/edit.html');" value="<spring:message code="application.edit"/>"/>
				</div>
				<div class="sub-element-description"><spring:message code="administration.system.timeTracking.description"/></div>
			</div>
			<div class="sub-element-content">
				<table class="form-table">
					<tr>
						<td class="form-label-col"><spring:message code="entity.timeTrackingSettings.enabled"/>:</td>
						<td class="form-input-col"><code:toggle value="${timeTrackingSettings.enabled}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.timeTrackingSettings.workHoursPerDay"/>:</td>
						<td class="form-input-col">${timeTrackingSettings.workHoursPerDay}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.timeTrackingSettings.workDaysPerWeek"/>:</td>
						<td class="form-input-col">${timeTrackingSettings.workDaysPerWeek}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.timeTrackingSettings.timeEstimatesDisplayFormat"/>:</td>
						<td class="form-input-col">${timeTrackingSettings.timeEstimatesDisplayFormat}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.timeTrackingSettings.defaultTimeTrackingUnit"/>:</td>
						<td class="form-input-col">${timeTrackingSettings.defaultTimeTrackingUnit}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
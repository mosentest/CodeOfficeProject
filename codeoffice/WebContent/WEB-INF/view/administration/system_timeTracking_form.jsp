<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<div class="sub-element-title"><spring:message code="administration.system.timeTracking.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.system.timeTracking.description"/></div>
			</div>
			<div class="sub-element-content">
				<form:form action="administration/timeTracking/edit" modelAttribute="timeTrackingSettings" method="POST">
					<table class="form-table">
						<form:hidden path="id"/>
						<tr>
							<td class="form-label-col"><spring:message code="entity.timeTrackingSettings.enabled"/>:</td>
							<td class="form-input-col"><code:toggle value="${timeTrackingSettings.enabled}" path="enabled"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="entity.timeTrackingSettings.workHoursPerDay"/>:</td>
							<td class="form-input-col">
								<form:select path="workHoursPerDay">
									<c:forEach items="${hoursPerDay}" var="index">
										<option value="${index}" ${index eq timeTrackingSettings.workHoursPerDay ? 'selected' : ''} >${index}</option>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="entity.timeTrackingSettings.workDaysPerWeek"/>:</td>
							<td class="form-input-col">
								<form:select path="workDaysPerWeek">
									<c:forEach items="${daysPerWeek}" var="index">
										<option value="${index}" ${index eq timeTrackingSettings.workDaysPerWeek ? 'selected' : ''} >${index}</option>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="entity.timeTrackingSettings.timeEstimatesDisplayFormat"/>:</td>
							<td class="form-input-col"><form:input path="timeEstimatesDisplayFormat"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="entity.timeTrackingSettings.defaultTimeTrackingUnit"/>:</td>
							<td class="form-input-col">
								<form:select path="defaultTimeTrackingUnit">
									<c:forEach items="${timeTrackingUnit}" var="unit">
										<option value="${unit}" ${unit eq timeTrackingSettings.defaultTimeTrackingUnit ? 'selected' : ''} >${unit}</option>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" class="largebutton button" value="<spring:message code="application.save"/>"/></td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
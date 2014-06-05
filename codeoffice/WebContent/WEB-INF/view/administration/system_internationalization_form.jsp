<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="system"/>
	</jsp:include>
</div>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/system_menu.jsp">
		<jsp:param name="menu" value="internationalization"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.system.internationalizationSettings.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.system.internationalizationSettings.description"/></div>
			</div>
			<div class="sub-element-content">
				<form:form action="administration/internationalization/edit" modelAttribute="internationalizationSettings" method="POST">
					<table class="form-table">
						<form:hidden path="id"/>
						<tr class="form-title-row">
							<td colspan="2"><spring:message code="administration.system.internationalizationSettings.INTERNATIONALIZATIONSETTINGS"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="entity.internationalizationSettings.defaultLocale"/>:</td>
							<td class="form-input-col">
								<form:select path="defaultLocaleString">
									<c:forEach items="${supportedLocale}" var="locale">
										<option value="${locale}" ${internationalizationSettings.defaultLocale eq locale ? 'selected=selected' : ''} >
										<c:set var="localeString" scope="page">${locale.language}_${locale.country}</c:set>
										<spring:message code="${localeString}"/></option>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="entity.internationalizationSettings.defaultTimeZone"/>:</td>
							<td class="form-input-col">
								<form:select path="defaultTimeZoneID">
									<c:forEach items="${supportedTimeZone}" var="timeZone">
										<option value="${timeZone.ID}" ${internationalizationSettings.defaultTimeZone eq timeZone ? 'selected=selected' : ''} >
										${timeZone.displayName} (${timeZone.ID} ${codefunction:timeOffsetString(timeZone)})</option>
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
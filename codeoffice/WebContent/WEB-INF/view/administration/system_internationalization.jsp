<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
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
				<div class="sub-element-title imglink">
					<span><spring:message code="administration.system.internationalizationSettings.title"/></span>
					<input type="button" class="button" onclick="javascript:url('/administration/internationalization/edit.html');" value="<spring:message code="application.edit"/>"/>
				</div>
				<div class="sub-element-description"><spring:message code="administration.system.internationalizationSettings.description"/></div>
			</div>
			<div class="sub-element-content">
				<table class="form-table">
					<tr class="form-title-row">
						<td colspan="2"><spring:message code="administration.system.internationalizationSettings.INTERNATIONALIZATIONSETTINGS"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.internationalizationSettings.defaultLocale"/>:</td>
						<td class="form-input-col">
							<c:set var="localeString">${internationalizationSettings.defaultLocale.language}_${internationalizationSettings.defaultLocale.country}</c:set>
							<spring:message code="${localeString}"/>
						</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.internationalizationSettings.defaultTimeZone"/>:</td>
						<td class="form-input-col">
							<c:set var="timeZone" value="${internationalizationSettings.defaultTimeZone}"/>
							${timeZone.displayName}&nbsp;(${timeZone.ID}&nbsp;${codefunction:timeOffsetString(timeZone)})
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="settings.enterprise_settings"/></div>
<div id="content">
	<jsp:include page="/WEB-INF/view/settings/settings_menu.jsp">
		<jsp:param name="menu" value="global"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="settings.global_settings"/></div>
				<div class="sub-element-description">Edit your enterprise global settings.</div>
			</div>
			<div class="sub-element-content">
			
				<form:form action="" modelAttribute="globalSettings" method="POST">
					<table class="form-table">
						<tr class="form-title-row">
							<td colspan="3"><spring:message code="settings.global.GENERALSETTINGS"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.title"/>:</td>
							<td class="form-input-col"><form:input path="title"/></td>
							<td class="form-description-col">description for column</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.enablePublicMode"/>:</td>
							<td class="form-input-col"><form:checkbox path="enablePublicMode"/></td>
							<td class="form-description-col">description for column</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.maxAuthenticationAllowed"/>:</td>
							<td class="form-input-col" colspan="2"><form:input path="maxAuthenticationAllowed"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.captchaOnSignUp"/>:</td>
							<td class="form-input-col" colspan="2"><form:checkbox path="captchaOnSignUp"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.emailFormat"/>:</td>
							<td class="form-input-col" colspan="2"><form:input path="emailFormat"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.introduction"/>:</td>
							<td class="form-input-col" colspan="2"><form:textarea path="introduction" cols="30" rows="3"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.enableContactAdministrator"/>:</td>
							<td class="form-input-col" colspan="2"><form:checkbox path="enableContactAdministrator"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.contactAdministratorMessage"/>:</td>
							<td class="form-input-col" colspan="2"><form:input path="contactAdministratorMessage"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.enableLogoutConfirmation"/></td>
							<td class="form-input-col"><form:checkbox path="enableLogoutConfirmation"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.enableInlineEdit"/></td>
							<td class="form-input-col"><form:checkbox path="enableInlineEdit"/></td>
						</tr>
						<tr class="form-title-row">
							<td colspan="3"><spring:message code="settings.global.GENERALOPTIONS"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.enableVote"/>:</td>
							<td class="form-input-col" colspan="2"><form:checkbox path="enableVote"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.enableWatch"/>:</td>
							<td class="form-input-col" colspan="2"><form:checkbox path="enableWatch"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.maxProjectNameLength"/>:</td>
							<td class="form-input-col" colspan="2"><form:input path="maxProjectNameLength"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.maxProjectKeyLength"/>:</td>
							<td class="form-input-col" colspan="2"><form:input path="maxProjectKeyLength"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.enableUnassignedIssue"/>:</td>
							<td class="form-input-col" colspan="2"><form:checkbox path="enableUnassignedIssue"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.emailVisibility"/>:</td>
							<td class="form-input-col" colspan="2"><form:select path="emailVisibility" items="${emailVisibilities}" /></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="settings.global.commentVisibility"/>:</td>
							<td class="form-input-col" colspan="2"><form:select path="commentVisibility" items="${commentVisibilities}" /></td>
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
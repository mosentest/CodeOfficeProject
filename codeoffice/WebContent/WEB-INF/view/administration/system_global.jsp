<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="system"/>
	</jsp:include>
</div>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/system_menu.jsp">
		<jsp:param name="menu" value="global"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title">
					<spring:message code="administration.system.globalSettings"/>
					<input type="button" class="button" onclick="javascript:url('/administration/global/edit.html');" value="<spring:message code="application.edit"/>"/>
				</div>
				<div class="sub-element-description">Edit your enterprise global settings.</div>
			</div>
			<div class="sub-element-content">
				<table class="form-table">
					<tr class="form-title-row">
						<td colspan="3"><spring:message code="administration.global.GENERALSETTINGS"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.title"/>:</td>
						<td class="form-input-col">${globalSettings.title}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.enablePublicMode"/>:</td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enablePublicMode}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.maxAuthenticationAllowed"/>:</td>
						<td class="form-input-col">${globalSettings.maxAuthenticationAllowed}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.captchaOnSignUp"/>:</td>
						<td class="form-input-col"><code:toggle value="${globalSettings.captchaOnSignUp}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.emailFormat"/>:</td>
						<td class="form-input-col">${globalSettings.emailFormat}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.introduction"/>:</td>
						<td class="form-input-col">${globalSettings.introduction}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.enableContactAdministrator"/>:</td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enableContactAdministrator}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.contactAdministratorMessage"/>:</td>
						<td class="form-input-col">${globalSettings.contactAdministratorMessage}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.enableLogoutConfirmation"/></td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enableLogoutConfirmation}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.enableInlineEdit"/></td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enableInlineEdit}"/></td>
					</tr>
					<tr class="form-title-row">
						<td colspan="3"><spring:message code="administration.global.GENERALOPTIONS"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.enableVote"/>:</td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enableVote}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.global.enableWatch"/>:</td>
							<td class="form-input-col"><code:toggle value="${globalSettings.enableWatch}"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.maxProjectNameLength"/>:</td>
							<td class="form-input-col">${globalSettings.maxProjectNameLength}</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.maxProjectKeyLength"/>:</td>
							<td class="form-input-col">${globalSettings.maxProjectKeyLength}</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.enableUnassignedIssue"/>:</td>
							<td class="form-input-col"><code:toggle value="${globalSettings.enableUnassignedIssue}"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.emailVisibility"/>:</td>
							<td class="form-input-col">${globalSettings.emailVisibility}</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.commentVisibility"/>:</td>
							<td class="form-input-col">${globalSettings.commentVisibility}</td>
						</tr>
					</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
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
		<jsp:param name="menu" value="global"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title imglink">
					<span><spring:message code="administration.system.global.title"/></span>
					<input type="button" class="button" onclick="javascript:url('/administration/global/edit.html');" value="<spring:message code="application.edit"/>"/>
				</div>
				<div class="sub-element-description"><spring:message code="administration.system.global.description"/></div>
			</div>
			<div class="sub-element-content">
				<table class="form-table">
					<tr class="form-title-row">
						<td colspan="2"><spring:message code="administration.system.global.GENERALSETTINGS"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.title"/>:</td>
						<td class="form-input-col">${globalSettings.title}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.enablePublicMode"/>:</td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enablePublicMode}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.maxAuthenticationAllowed"/>:</td>
						<td class="form-input-col">${globalSettings.maxAuthenticationAllowed}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.captchaOnSignUp"/>:</td>
						<td class="form-input-col"><code:toggle value="${globalSettings.captchaOnSignUp}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.emailFormat"/>:</td>
						<td class="form-input-col">${globalSettings.emailFormat}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.introduction"/>:</td>
						<td class="form-input-col">${globalSettings.introduction}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.enableContactAdministrator"/>:</td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enableContactAdministrator}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.contactAdministratorMessage"/>:</td>
						<td class="form-input-col">${globalSettings.contactAdministratorMessage}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.enableLogoutConfirmation"/></td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enableLogoutConfirmation}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.enableInlineEdit"/></td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enableInlineEdit}"/></td>
					</tr>
					<tr class="form-title-row">
						<td colspan="2"><spring:message code="administration.system.global.GENERALOPTIONS"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.enableVote"/>:</td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enableVote}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.enableWatch"/>:</td>
							<td class="form-input-col"><code:toggle value="${globalSettings.enableWatch}"/></td>
						</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.maxProjectNameLength"/>:</td>
						<td class="form-input-col">${globalSettings.maxProjectNameLength}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.maxProjectKeyLength"/>:</td>
						<td class="form-input-col">${globalSettings.maxProjectKeyLength}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.enableUnassignedIssue"/>:</td>
						<td class="form-input-col"><code:toggle value="${globalSettings.enableUnassignedIssue}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.emailVisibility"/>:</td>
						<td class="form-input-col">${globalSettings.emailVisibility}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="entity.globalSettings.commentVisibility"/>:</td>
						<td class="form-input-col">${globalSettings.commentVisibility}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
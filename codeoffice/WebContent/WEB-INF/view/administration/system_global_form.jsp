<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<div class="sub-element-title"><spring:message code="administration.system.globalSettings"/></div>
				<div class="sub-element-description">Edit your enterprise global settings.</div>
			</div>
			<div class="sub-element-content">
				<form:form action="administration/global/edit" modelAttribute="globalSettings" method="POST">
					<table class="form-table">
						<tr><td><form:hidden path="id"/></td></tr>
						<tr class="form-title-row">
							<td colspan="3"><spring:message code="administration.global.GENERALSETTINGS"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.title"/>:</td>
							<td class="form-input-col"><input type="text" name="title" value="${globalSettings.title}"/></td>
							<td class="form-description-col">description for column</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.enablePublicMode"/>:</td>
							<td class="form-input-col"><code:toggle value="${globalSettings.enablePublicMode}" path="enablePublicMode"/></td>
							<td class="form-description-col">description for column</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.maxAuthenticationAllowed"/>:</td>
							<td class="form-input-col" colspan="2"><form:input path="maxAuthenticationAllowed"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.captchaOnSignUp"/>:</td>
							<td class="form-input-col" colspan="2"><code:toggle value="${globalSettings.captchaOnSignUp}" path="captchaOnSignUp"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.emailFormat"/>:</td>
							<td class="form-input-col" colspan="2"><form:input path="emailFormat"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.introduction"/>:</td>
							<td class="form-input-col" colspan="2"><form:textarea path="introduction" cols="30" rows="3"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.enableContactAdministrator"/>:</td>
							<td class="form-input-col" colspan="2"><code:toggle value="${globalSettings.enableContactAdministrator}" path="enableContactAdministrator"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.contactAdministratorMessage"/>:</td>
							<td class="form-input-col" colspan="2"><form:input path="contactAdministratorMessage"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.enableLogoutConfirmation"/></td>
							<td class="form-input-col" colspan="2"><code:toggle value="${globalSettings.enableLogoutConfirmation}" path="enableLogoutConfirmation"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.enableInlineEdit"/></td>
							<td class="form-input-col" colspan="2"><code:toggle value="${globalSettings.enableInlineEdit}" path="enableInlineEdit"/></td>
						</tr>
						<tr class="form-title-row">
							<td colspan="3"><spring:message code="administration.global.GENERALOPTIONS"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.enableVote"/>:</td>
							<td class="form-input-col" colspan="2"><code:toggle value="${globalSettings.enableVote}" path="enableVote"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.enableWatch"/>:</td>
							<td class="form-input-col" colspan="2"><code:toggle value="${globalSettings.enableWatch}" path="enableWatch"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.maxProjectNameLength"/>:</td>
							<td class="form-input-col" colspan="2"><form:input path="maxProjectNameLength"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.maxProjectKeyLength"/>:</td>
							<td class="form-input-col" colspan="2"><form:input path="maxProjectKeyLength"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.enableUnassignedIssue"/>:</td>
							<td class="form-input-col" colspan="2"><code:toggle value="${globalSettings.enableUnassignedIssue}" path="enableUnassignedIssue"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.emailVisibility"/>:</td>
							<td class="form-input-col" colspan="2">
								<form:select path="emailVisibility">
									<c:forEach items="${emailVisibilities}" var="visibility">
										<spring:message var="label" code="${visibility.key}"/>
										<option value="${visibility}" ${visibility eq globalSettings.emailVisibility ? 'selected=selected' : ''}>${label}</option>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.global.commentVisibility"/>:</td>
							<td class="form-input-col" colspan="2">
								<form:select path="commentVisibility">
									<c:forEach items="${commentVisibilities}" var="visibility">
										<spring:message var="label" code="${visibility.key}"/>
										<option value="${visibility}" ${visibility eq globalSettings.commentVisibility ? 'selected=selected' : ''}>${label}</option>
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
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
		<jsp:param name="menu" value="announcement"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title">
					<spring:message code="administration.system.announcementSettings"/>
					<input type="button" class="button" onclick="javascript:url('/administration/announcement/edit.html');" value="<spring:message code="application.edit"/>"/>
				</div>
				<div class="sub-element-description">Edit your announcement settings.</div>
			</div>
			<div class="sub-element-content">
				<table class="form-table">
					<tr class="form-title-row">
						<td colspan="2"><spring:message code="administration.announcement.ANNOUNCEMENTSETTINGS"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.announcement.enabled"/>:</td>
						<td class="form-input-col"><code:toggle value="${announcementSettings.enabled}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.announcement.announcement"/>:</td>
						<td class="form-input-col">${announcementSettings.announcement}</td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.announcement.announcementLevel"/>:</td>
						<td class="form-input-col"><spring:message code="${announcementSettings.announcementLevel.key}"/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.announcement.enablePublicMode"/>:</td>
						<td class="form-input-col"><code:toggle value="${announcementSettings.enablePublicMode}"/></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
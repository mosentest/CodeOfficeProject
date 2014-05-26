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
		<jsp:param name="menu" value="announcement"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.system.announcementSettings"/></div>
				<div class="sub-element-description">Edit your announcement settings.</div>
			</div>
			<div class="sub-element-content">
				<form:form action="administration/announcement/edit" modelAttribute="announcementSettings" method="POST">
					<table class="form-table">
						<tr><td><form:hidden path="id"/></td></tr>
						<tr class="form-title-row">
							<td colspan="3"><spring:message code="administration.announcement.ANNOUNCEMENTSETTINGS"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.announcement.enabled"/>:</td>
							<td class="form-input-col"><code:toggle value="${announcementSettings.enabled}" path="enabled"/></td>
						</tr>
						<tr class="form-description-row"><td></td><td>Description for enabled</td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.announcement.announcement"/>:</td>
							<td class="form-input-col" colspan="2"><form:textarea path="announcement" cols="30" rows="3"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.announcement.enablePublicMode"/>:</td>
							<td class="form-input-col"><code:toggle value="${announcementSettings.enablePublicMode}" path="enablePublicMode"/></td>
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
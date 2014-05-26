<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div id="leftmenu">
<ul class="vertical-tab">
<security:authorize access="isAuthenticated()">
	<li class="tab-title"><spring:message code="administration.system.title.general"/></li>
	<security:authorize access="hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')">
	<li class="tab ${param.menu eq 'global' ? 'active' : ''}"><a class="link" href="administration/global.html"><spring:message code="administration.system.globalSettings"/></a></li>
	<li class="tab ${param.menu eq 'advancedglobal' ? 'active' : ''}"><a class="link" href="administration/advancedGlobal.html"><spring:message code="administration.system.advancedGlobalSettings"/></a></li>
	<li class="tab ${param.menu eq 'internationalization' ? 'active' : ''}"><a class="link" href="administration/internationalization.html"><spring:message code="administration.system.internationalizationSettings"/></a></li>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
	<li class="tab ${param.menu eq 'announcement' ? 'active' : ''}"><a class="link" href="administration/announcement.html"><spring:message code="administration.system.announcementSettings"/></a></li>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_GLOBAL_SHARED_PROJECT')">
	<li class="tab ${param.menu eq 'sharedobjects' ? 'active' : ''}"><a class="link" href="administration/sharedObjects.html"><spring:message code="administration.system.sharedObjects"/></a></li>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')">
		<li class="tab-title"><spring:message code="administration.system.title.security"/></li>
		<li class="tab ${param.menu eq 'globalpermission' ? 'active' : ''}"><a class="link" href="administration/globalPermission.html"><spring:message code="administration.system.globalPermissionSettings"/></a></li>
		<li class="tab ${param.menu eq 'projectpermission' ? 'active' : ''}"><a class="link" href="administration/projectPermission.html"><spring:message code="administration.system.projectPermissionSettings"/></a></li>
	</security:authorize>
</security:authorize>
</ul>
</div>
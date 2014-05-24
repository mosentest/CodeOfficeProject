<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div id="leftmenu">
	<ul class="vertical-tab">
		<security:authorize access="isAuthenticated()">
			<li class="tab ${param.menu eq 'home' ? 'active' : ''}"><a href="settings/home.html"><spring:message code="settings.home"/></a></li>
			<li class="tab-title"><spring:message code="settings.general_settings"/></li>
			<security:authorize access="hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')">
			<li class="tab ${param.menu eq 'global' ? 'active' : ''}"><a href="settings/global.html"><spring:message code="settings.global_settings"/></a></li>
			<li class="tab ${param.menu eq 'globaladvanced' ? 'active' : ''}"><a href="settings/globaladvanced.html"><spring:message code="settings.global_advanced_settings"/></a></li>
			<li class="tab ${param.menu eq 'internationalization' ? 'active' : ''}"><a href="settings/internationalization.html"><spring:message code="settings.internationalization_settings"/></a></li>
			</security:authorize>
			<security:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
			<li class="tab ${param.menu eq 'announcement' ? 'active' : ''}"><a href="settings/announcement.html"><spring:message code="settings.announcement_settings"/></a></li>
			</security:authorize>
			<security:authorize access="hasRole('ROLE_GLOBAL_SHARED_PROJECT')">
			<li class="tab ${param.menu eq 'sharedobjects' ? 'active' : ''}"><a href="settings/sharedobjects.html"><spring:message code="settings.shared_objects"/></a></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN', 'ROLE_GLOBAL_ADMIN', 'ROLE_GLOBAL_PROJECT_ADMIN')">
				<li class="tab-title"><spring:message code="settings.project_settings"/></li>
				<li class="tab ${param.menu eq 'generalproject' ? 'active' : ''}"><a href="settings/generalproject.html"><spring:message code="settings.general_project_settings"/></a></li>
				<li class="tab ${param.menu eq 'issuelink' ? 'active' : ''}"><a href="settings/issuelink.html"><spring:message code="settings.issuelink_settings"/></a></li>
				<li class="tab ${param.menu eq 'subtask' ? 'active' : ''}"><a href="settings/subtask.html"><spring:message code="settings.subtask_settings"/></a></li>
				<li class="tab ${param.menu eq 'attachment' ? 'active' : ''}"><a href="settings/attachment.html"><spring:message code="settings.attachment_settings"/></a></li>
				<li class="tab ${param.menu eq 'timetracking' ? 'active' : ''}"><a href="settings/timetracking.html"><spring:message code="settings.timetracking_settings"/></a></li>
			</security:authorize>
			<security:authorize access="hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')">
				<li class="tab-title"><spring:message code="settings.security_settings"/></li>
				<li class="tab ${param.menu eq 'globalpermission' ? 'active' : ''}"><a href="settings/globalpermission.html"><spring:message code="settings.global_permission_settings"/></a></li>
				<li class="tab ${param.menu eq 'projectpermission' ? 'active' : ''}"><a href="settings/projectpermission.html"><spring:message code="settings.project_permission_settings"/></a></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN', 'ROLE_GLOBAL_ADMIN', 'ROLE_GLOBAL_BROWSE_USER')">
				<li class="tab-title"><spring:message code="settings.user_settings"/></li>
				<security:authorize access="hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN', 'ROLE_GLOBAL_ADMIN', 'ROLE_GLOBAL_BROWSE_USER', 'ROLE_GLOBAL_USER')">
				<li class="tab ${param.menu eq 'user' ? 'active' : ''}"><a href="settings/user.html"><spring:message code="settings.users_settings"/></a></li>
				</security:authorize>
				<li class="tab ${param.menu eq 'usergroup' ? 'active' : ''}"><a href="settings/usergroup.html"><spring:message code="settings.usergroups_settings"/></a></li>
				<security:authorize access="hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')">
				<li class="tab ${param.menu eq 'usersession' ? 'active' : ''}"><a href="settings/usersession.html"><spring:message code="settings.user_session_settings"/></a></li>
				</security:authorize>
			</security:authorize>
		</security:authorize>
	</ul>
</div>
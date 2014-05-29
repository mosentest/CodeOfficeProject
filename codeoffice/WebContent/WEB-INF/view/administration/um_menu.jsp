<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div id="leftmenu">
<ul class="vertical-navigation">
<security:authorize access="isAuthenticated()">
	<li class="tab-title"><spring:message code="administration.um.title.general"/></li>
	<security:authorize access="hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')">
	<li class="tab ${param.menu eq 'usersessions' ? 'active' : ''}"><a class="link" href="administration/userSessions.html"><spring:message code="administration.um.userSessions"/></a></li>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_GLOBAL_BROWSE_USER')">
	<li class="tab ${param.menu eq 'users' ? 'active' : ''}"><a class="link" href="administration/users.html"><spring:message code="administration.um.users"/></a></li>
	<li class="tab ${param.menu eq 'usergroups' ? 'active' : ''}"><a class="link" href="administration/userGroups.html"><spring:message code="administration.um.userGroups"/></a></li>
	</security:authorize>
</security:authorize>
</ul>
</div>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="leftmenu">
<c:set var="selectedClass">class="selected"</c:set>
<ul class="vertical-navigation">
<security:authorize access="isAuthenticated()">
	<li ${param.menu eq 'home' ? selectedClass : ''}><a class="link" href="administration/userManagement.html"><spring:message code="administration.home"/></a></li>
	<div class="vertical-navigation-title"><spring:message code="administration.um.menu.title.general"/></div>
	<li ${param.menu eq 'usersessions' ? selectedClass : ''}><a class="link" href="administration/userSessions.html"><spring:message code="administration.um.menu.userSessions"/></a></li>
	<li ${param.menu eq 'users' ? selectedClass : ''}><a class="link" href="administration/users.html"><spring:message code="administration.um.menu.users"/></a></li>
	<li ${param.menu eq 'usergroups' ? selectedClass : ''}><a class="link" href="administration/userGroups.html"><spring:message code="administration.um.menu.userGroups"/></a></li>
</security:authorize>
</ul>
</div>
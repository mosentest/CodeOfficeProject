<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="leftmenu">
<c:set var="selectedClass">class="selected"</c:set>
<ul class="vertical-navigation">
<security:authorize access="isAuthenticated()">
	<li ${param.menu eq 'home' ? selectedClass : ''}><a class="link" href="administration/project.html"><spring:message code="administration.home"/></a></li>
	<li class="vertical-navigation-title"><spring:message code="administration.project.title.general"/></li>
	<li ${param.menu eq 'permissionscheme' ? selectedClass : ''}><a class="link" href="administration/permissionSchemes.html"><spring:message code="administration.project.permissionSchemes"/></a></li>
	<li ${param.menu eq 'projectrole' ? selectedClass : ''}><a class="link" href="administration/projectRoles.html"><spring:message code="administration.project.projectRoles"/></a></li>
	<li ${param.menu eq 'workflow' ? selectedClass : ''}><a class="link" href="administration/workFlows.html"><spring:message code="administration.project.workflows"/></a></li>
</security:authorize>
</ul>
</div>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="leftmenu">
<c:set var="selectedClass">class="selected"</c:set>
<ul class="vertical-navigation">
<security:authorize access="isAuthenticated()">
	<li ${param.menu eq 'home' ? selectedClass : ''}><a class="link" href="administration/system.html"><spring:message code="administration.home"/></a></li>
	<li ${param.menu eq 'global' ? selectedClass : ''}><a class="link" href="administration/global.html"><spring:message code="administration.system.globalSettings"/></a></li>
	<li ${param.menu eq 'advancedglobal' ? selectedClass : ''}><a class="link" href="administration/advancedGlobal.html"><spring:message code="administration.system.advancedGlobalSettings"/></a></li>
	<li ${param.menu eq 'internationalization' ? selectedClass : ''}><a class="link" href="administration/internationalization.html"><spring:message code="administration.system.internationalizationSettings"/></a></li>
	<li ${param.menu eq 'announcement' ? selectedClass : ''}><a class="link" href="administration/announcement.html"><spring:message code="administration.system.announcementSettings"/></a></li>
	<li ${param.menu eq 'sharedobjects' ? selectedClass : ''}><a class="link" href="administration/sharedObjects.html"><spring:message code="administration.system.sharedObjects"/></a></li>
	<div class="vertical-navigation-title"><spring:message code="administration.system.title.security"/></div>
	<li ${param.menu eq 'globalpermission' ? selectedClass : ''}><a class="link" href="administration/globalPermission.html"><spring:message code="administration.system.globalPermissionSettings"/></a></li>
</security:authorize>
</ul>
</div>
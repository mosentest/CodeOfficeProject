<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div id="leftmenu">
<ul class="vertical-navigation">
<security:authorize access="isAuthenticated()">
	<li class="${param.menu eq 'home' ? 'selected' : ''}"><a class="link" href="administration.home"><spring:message code="administration.home"/></a></li>
	<div class="vertical-navigation-title"><spring:message code="administration.issue.menu.title.issueTypes"/></div>
	<li class="${param.menu eq 'type' ? 'selected' : ''}"><a class="link" href="administration/types.html"><spring:message code="administration.issue.menu.issueTypes"/></a></li>
	<li class="${param.menu eq 'typeScheme' ? 'selected' : ''}"><a class="link" href="administration/typeSchemes.html"><spring:message code="administration.issue.menu.issueTypeSchemes"/></a></li>
	<li class="${param.menu eq 'subtask' ? 'selected' : ''}"><a class="link" href="administration/subtasks.html"><spring:message code="administration.issue.menu.subtasks"/></a></li>
	<li class="${param.menu eq 'link' ? 'selected' : ''}"><a class="link" href="administration/links.html"><spring:message code="administration.issue.menu.issueLinks"/></a></li>
	<div class="vertical-navigation-title"><spring:message code="administration.issue.title.screens"/></div>
	<li class="${param.menu eq 'screen' ? 'selected' : ''}"><a class="link" href="administration/screen.html"><spring:message code="administration.issue.menu.screen"/></a></li>
	<li class="${param.menu eq 'screenScheme' ? 'selected' : ''}"><a class="link" href="administration/screenScheme.html"><spring:message code="administration.issue.smenu.creenScheme"/></a></li>
	<li class="${param.menu eq 'issueTypeScreenScheme' ? 'selected' : ''}"><a class="link" href="administration/issueTypeScreenScheme.html"><spring:message code="administration.issue.menu.issueTypeScreenScheme"/></a></li>
	<div class="vertical-navigation-title"><spring:message code="administration.issue.menu.title.fields"/></div>
	<li class="${param.menu eq 'customField' ? 'selected' : ''}"><a class="link" href="administration/customField.html"><spring:message code="administration.issue.menu.customField"/></a></li>
	<li class="${param.menu eq 'fieldConfiguration' ? 'selected' : ''}"><a class="link" href="administration/fieldConfiguration.html"><spring:message code="administration.issue.menu.fieldConfiguration"/></a></li>
	<li class="${param.menu eq 'fieldConfigurationScheme' ? 'selected' : ''}"><a class="link" href="administration/fieldConfigurationScheme.html"><spring:message code="administration.issue.menu.fieldConfigurationScheme"/></a></li>
	<div class="vertical-navigation-title"><spring:message code="administration.issue.menu.title.issueAttributes"/></div>
	<li class="${param.menu eq 'status' ? 'selected' : ''}"><a class="link" href="administration/status.html"><spring:message code="administration.issue.menu.status"/></a></li>
	<li class="${param.menu eq 'resolution' ? 'selected' : ''}"><a class="link" href="administration/resolutions.html"><spring:message code="administration.issue.menu.resolutions"/></a></li>
	<li class="${param.menu eq 'priority' ? 'selected' : ''}"><a class="link" href="administration/priorities.html"><spring:message code="administration.issue.menu.priorities"/></a></li>
</security:authorize>
</ul>
</div>
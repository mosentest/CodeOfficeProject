<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div id="leftmenu">
<ul class="vertical-navigation">
<security:authorize access="isAuthenticated()">
	<li class="${param.menu eq 'home' ? 'selected' : ''}"><a class="link" href="administration.home"><spring:message code="administration.home"/></a></li>
	<div class="vertical-navigation-title"><spring:message code="administration.issue.title.issueTypes"/></div>
	<li class="${param.menu eq 'issueType' ? 'selected' : ''}"><a class="link" href="administration/issueTypes.html"><spring:message code="administration.issue.issueTypes"/></a></li>
	<li class="${param.menu eq 'issueTypeScheme' ? 'selected' : ''}"><a class="link" href="administration/issueTypeSchemes.html"><spring:message code="administration.issue.issueTypeSchemes"/></a></li>
	<li class="${param.menu eq 'subtask' ? 'selected' : ''}"><a class="link" href="administration/subtasks.html"><spring:message code="administration.issue.subtasks"/></a></li>
	<li class="${param.menu eq 'issueLink' ? 'selected' : ''}"><a class="link" href="administration/issueLinks.html"><spring:message code="administration.issue.issueLinks"/></a></li>
	<div class="vertical-navigation-title"><spring:message code="administration.issue.title.screens"/></div>
	<li class="${param.menu eq 'screen' ? 'selected' : ''}"><a class="link" href="administration/screen.html"><spring:message code="administration.issue.screen"/></a></li>
	<li class="${param.menu eq 'screenScheme' ? 'selected' : ''}"><a class="link" href="administration/screenScheme.html"><spring:message code="administration.issue.screenScheme"/></a></li>
	<li class="${param.menu eq 'issueTypeScreenScheme' ? 'selected' : ''}"><a class="link" href="administration/issueTypeScreenScheme.html"><spring:message code="administration.issue.issueTypeScreenScheme"/></a></li>
	<div class="vertical-navigation-title"><spring:message code="administration.issue.title.fields"/></div>
	<li class="${param.menu eq 'customField' ? 'selected' : ''}"><a class="link" href="administration/customField.html"><spring:message code="administration.issue.customField"/></a></li>
	<li class="${param.menu eq 'fieldConfiguration' ? 'selected' : ''}"><a class="link" href="administration/fieldConfiguration.html"><spring:message code="administration.issue.fieldConfiguration"/></a></li>
	<li class="${param.menu eq 'fieldConfigurationScheme' ? 'selected' : ''}"><a class="link" href="administration/fieldConfigurationScheme.html"><spring:message code="administration.issue.fieldConfigurationScheme"/></a></li>
	<div class="vertical-navigation-title"><spring:message code="administration.issue.title.issueAttributes"/></div>
	<li class="${param.menu eq 'status' ? 'selected' : ''}"><a class="link" href="administration/status.html"><spring:message code="administration.issue.status"/></a></li>
	<li class="${param.menu eq 'resolution' ? 'selected' : ''}"><a class="link" href="administration/resolutions.html"><spring:message code="administration.issue.resolutions"/></a></li>
	<li class="${param.menu eq 'priority' ? 'selected' : ''}"><a class="link" href="administration/priorities.html"><spring:message code="administration.issue.priorities"/></a></li>
</security:authorize>
</ul>
</div>
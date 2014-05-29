<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div id="leftmenu">
<ul class="vertical-navigation">
<security:authorize access="isAuthenticated()">
	<li class="tab-title"><spring:message code="administration.issue.title.issueTypes"/></li>
	<li class="tab ${param.menu eq 'issueType' ? 'active' : ''}"><a class="link" href="administration/issueType.html"><spring:message code="administration.issue.issueType"/></a></li>
	<li class="tab ${param.menu eq 'issueTypeScheme' ? 'active' : ''}"><a class="link" href="administration/issueTypeScheme.html"><spring:message code="administration.issue.issueTypeScheme"/></a></li>
	<li class="tab ${param.menu eq 'subtask' ? 'active' : ''}"><a class="link" href="administration/subtask.html"><spring:message code="administration.issue.subtask"/></a></li>
	<li class="tab-title"><spring:message code="administration.issue.title.screens"/></li>
	<li class="tab ${param.menu eq 'screen' ? 'active' : ''}"><a class="link" href="administration/screen.html"><spring:message code="administration.issue.screen"/></a></li>
	<li class="tab ${param.menu eq 'screenScheme' ? 'active' : ''}"><a class="link" href="administration/screenScheme.html"><spring:message code="administration.issue.screenScheme"/></a></li>
	<li class="tab ${param.menu eq 'issueTypeScreenScheme' ? 'active' : ''}"><a class="link" href="administration/issueTypeScreenScheme.html"><spring:message code="administration.issue.issueTypeScreenScheme"/></a></li>
	<li class="tab-title"><spring:message code="administration.issue.title.fields"/></li>
	<li class="tab ${param.menu eq 'customField' ? 'active' : ''}"><a class="link" href="administration/customField.html"><spring:message code="administration.issue.customField"/></a></li>
	<li class="tab ${param.menu eq 'fieldConfiguration' ? 'active' : ''}"><a class="link" href="administration/fieldConfiguration.html"><spring:message code="administration.issue.fieldConfiguration"/></a></li>
	<li class="tab ${param.menu eq 'fieldConfigurationScheme' ? 'active' : ''}"><a class="link" href="administration/fieldConfigurationScheme.html"><spring:message code="administration.issue.fieldConfigurationScheme"/></a></li>
	<li class="tab-title"><spring:message code="administration.issue.title.issueAttributes"/></li>
	<li class="tab ${param.menu eq 'status' ? 'active' : ''}"><a class="link" href="administration/status.html"><spring:message code="administration.issue.status"/></a></li>
	<li class="tab ${param.menu eq 'resolution' ? 'active' : ''}"><a class="link" href="administration/resolution.html"><spring:message code="administration.issue.resolution"/></a></li>
	<li class="tab ${param.menu eq 'priority' ? 'active' : ''}"><a class="link" href="administration/priority.html"><spring:message code="administration.issue.priority"/></a></li>
</security:authorize>
</ul>
</div>
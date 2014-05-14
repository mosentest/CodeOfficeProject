<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="leftmenu">
	<ul class="vertical-tab">
		<li class="tab ${param.menu eq 'summary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/m_${component.code}"><spring:message code="project.m_summary"/></a></li>
		<li class="tab ${param.menu eq 'casesummary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/m_${component.code}/summary"><spring:message code="project.m_casesummary"/></a></li>
		<li class="tab ${param.menu eq 'cases' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/m_${component.code}/case"><spring:message code="project.m_cases"/></a></li>
	</ul>
</div>
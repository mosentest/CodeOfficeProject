<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="leftmenu">
	<ul class="vertical-navigation">
		<li class="tab ${param.menu eq 'summary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/m_${component.code}"><spring:message code="component.summary"/></a></li>
		<li class="tab ${param.menu eq 'casesummary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/m_${component.code}/summary"><spring:message code="component.case_summary"/></a></li>
		<li class="tab ${param.menu eq 'cases' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/m_${component.code}/case"><spring:message code="component.cases"/></a></li>
	</ul>
</div>
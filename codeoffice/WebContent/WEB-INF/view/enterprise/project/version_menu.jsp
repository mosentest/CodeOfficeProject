<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="leftmenu">
	<ul class="vertical-tab">
		<li class="tab ${param.menu eq 'summary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/v_${version.code}"><spring:message code="project.v_summary"/></a></li>
		<li class="tab ${param.menu eq 'releasesummary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/v_${version.code}/release"><spring:message code="project.v_releasesummary"/></a></li>
		<li class="tab ${param.menu eq 'relatedsummary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/v_${version.code}/related"><spring:message code="project.v_relatedsummary"/></a></li>
		<li class="tab ${param.menu eq 'cases' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/v_${version.code}/case"><spring:message code="project.v_cases"/></a></li>
		<li class="tab ${param.menu eq 'releasenote' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/v_${version.code}/releasenote"><spring:message code="project.v_releasenote"/></a></li>
	</ul>
</div>
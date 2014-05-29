<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="leftmenu">
	<ul class="vertical-navigation">
		<li class="tab ${param.menu eq 'summary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/v_${version.code}"><spring:message code="version.summary"/></a></li>
		<li class="tab ${param.menu eq 'releasesummary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/v_${version.code}/crelease"><spring:message code="version.release_case_summary"/></a></li>
		<li class="tab ${param.menu eq 'relatedsummary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/v_${version.code}/crelated"><spring:message code="version.related_case_summary"/></a></li>
		<li class="tab ${param.menu eq 'cases' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/v_${version.code}/case"><spring:message code="version.cases"/></a></li>
		<li class="tab ${param.menu eq 'releasenote' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/v_${version.code}/releasenote"><spring:message code="version.releasenote"/></a></li>
	</ul>
</div>
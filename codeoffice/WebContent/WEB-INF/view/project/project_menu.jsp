<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="leftmenu">
	<ul class="vertical-tab">
		<li class="tab ${param.menu eq 'dashboard' ? 'active' : ''}"><a href="enterprise/pro_${project.code}"><spring:message code="project.dashboard"/></a></li>
		<li class="tab ${param.menu eq 'summary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/summary"><spring:message code="project.summary"/></a></li>
		<li class="tab ${param.menu eq 'roadmap' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/roadmap"><spring:message code="project.roadmap"/></a></li>
		<li class="tab ${param.menu eq 'casesummary' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/casesummary"><spring:message code="project.case_summary"/></a></li>
		<li class="tab ${param.menu eq 'cases' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/case"><spring:message code="project.cases"/></a></li>
		<li class="tab ${param.menu eq 'rolegroup' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/rolegroup"><spring:message code="project.rolegroup"/></a></li>
		<li class="tab ${param.menu eq 'versions' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/versions"><spring:message code="project.versions"/></a></li>
		<li class="tab ${param.menu eq 'components' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/components"><spring:message code="project.components"/></a></li>
		<li class="tab ${param.menu eq 'labels' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/labels"><spring:message code="project.labels"/></a></li>
		<li class="tab ${param.menu eq 'notes' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/notes"><spring:message code="project.notes"/></a></li>
		<li class="tab ${param.menu eq 'changelog' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/changelog"><spring:message code="project.changelog"/></a></li>
		<li class="tab ${param.menu eq 'source' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/source"><spring:message code="project.source"/></a></li>
		<li class="tab ${param.menu eq 'reviews' ? 'active' : ''}"><a href="enterprise/pro_${project.code}/reviews"><spring:message code="project.reviews"/></a></li>
	</ul>
</div>
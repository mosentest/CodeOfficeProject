<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="leftmenu">
	<ul class="vertical-tab">
		<li class="${param.menu eq 'home' ? 'active' : ''}"><a href="enterprise/project"><spring:message code="project.home"/></a></li>
		<li class="${param.menu eq 'projectcategories' ? 'active' : ''}"><a href="enterprise/project/category"><spring:message code="project.projectcategories"/></a></li>
		<li class="${param.menu eq 'list' ? 'active' : ''}"><a href="enterprise/project/active"><spring:message code="project.activeprojects"/></a></li>
		<li class="sep-30"></li>
		<security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')">
			<li class="${param.menu eq 'newcategory' ? 'active' : ''}"><a href="enterprise/project/newcategory"><spring:message code="project.createcategory"/></a></li>
			<li class="${param.menu eq 'newproject' ? 'active' : ''}"><a href="enterprise/project/newproject"><spring:message code="project.createproject"/></a></li>
		</security:authorize>
	</ul>
</div>
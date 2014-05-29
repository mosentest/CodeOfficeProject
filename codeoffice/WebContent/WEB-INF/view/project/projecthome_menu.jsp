<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="leftmenu">
	<ul class="vertical-navigation">
		<li class="tab ${param.menu eq 'home' ? 'active' : ''}"><a href="enterprise/project"><spring:message code="project.home"/></a></li>
		<li class="tab ${param.menu eq 'projectcategories' ? 'active' : ''}"><a href="enterprise/category"><spring:message code="category.project_categories"/></a></li>
		<li class="empty-tab"></li>
		<security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')">
			<li class="tab ${param.menu eq 'newcategory' ? 'active' : ''}"><a href="enterprise/category/create"><spring:message code="category.create_category"/></a></li>
			<li class="tab ${param.menu eq 'newproject' ? 'active' : ''}"><a href="enterprise/project/create"><spring:message code="project.create_project"/></a></li>
		</security:authorize>
	</ul>
</div>
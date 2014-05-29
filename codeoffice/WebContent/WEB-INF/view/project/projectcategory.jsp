<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<spring:message var="text_edit" code="application.edit"/>
<spring:message var="text_delete" code="application.delete"/>
<div id="content">
	<div class="element">
		<div class="info"><spring:message code="project.home"/></div>
		<div class="content">
			<div class="leftmenu">
				<ul class="vertical-navigation">
					<li class="tab"><a href="enterprise/project"><spring:message code="project.home"/></a></li>
					<security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')">
						<li class="tab ${param.menu eq 'newcategory' ? 'active' : ''}">
							<a href="enterprise/category/create"><spring:message code="category.create_category"/></a>
						</li>
					</security:authorize>
					<li class="empty-tab"></li>
					<li class="tab ${empty projectCategoryId ? 'active' : ''}">
						<a href="enterprise/category"><spring:message code="category.all_categories"/></a>
					</li>
					<c:forEach items="${projectCategoryNames}" var="projectCategory">
						<li class="tab ${projectCategoryId eq projectCategory.id ? 'active' : ''}">
							<a href="enterprise/category/${projectCategory.id}">${projectCategory.name}</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="maincontent">
				<c:forEach items="${projectCategories}" var="projectCategory">
					<div class="mainelement w-100p">
						<div class="title imglink">
							<span>${projectCategory.name}</span>
							<c:if test="${projectCategory.id ne 0}">
								<span class="minorspace"></span>
								<a href="enterprise/category/${projectCategory.id}/edit"><img src="assets/img/icon_edit.png" title="${text_edit}"/></a>
								<c:if test="${fn:length(projectCategory.projects) eq 0}">
									<span class="minorspace"></span>
									<a href="enterprise/category/${projectCategory.id}/delete"><img src="assets/img/icon_remove.png" title="${text_delete}"/></a>
								</c:if>
							</c:if>
						</div>
						<div class="content">
							<c:if test="${fn:length(projectCategory.projects) eq 0}">
								<code:info title="category.category_has_no_project" arguments="${projectCategory.name}"/>
							</c:if>
							<c:if test="${fn:length(projectCategory.projects) gt 0}">
							<table class="default-table center">
								<tr>
									<th class="fit-cell left"></th>
									<th><spring:message code="project.code"/></th>
									<th><spring:message code="project.name"/></th>
									<th class="left"><spring:message code="project.lead"/></th>
									<th><spring:message code="project.case_count"/></th>
									<th><spring:message code="project.user_count"/></th>
								</tr>
								<c:forEach items="${projectCategory.projects}" var="project">
									<tr>
										<td class="fit-cell"><img src="${project.iconPath == null ? 'img/office/project_icon.png' : project.iconPath}"/></td>
										<td class="fit-cell center"><a href="enterprise/pro_${project.code}">${project.code}</a></td>
										<td>${project.name}</td>
										<td class="left"><code:user user="${project.lead}"/></td>
										<td>${project.noCase}</td>
										<td>${project.noUser}</td>
									</tr>
								</c:forEach>
							</table>
							</c:if>
						</div>
					</div>
					<div class="sep-30"></div>
				</c:forEach>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
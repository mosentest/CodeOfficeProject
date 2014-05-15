<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<spring:message var="text_edit" code="page.edit"/>
<spring:message var="text_remove" code="page.remove"/>
<spring:message var="text_nocase" code="project.p_nocase"/>
<spring:message var="text_p_name" code="project.p_name"/>
<spring:message var="text_p_code" code="project.p_code"/>
<spring:message var="text_p_nouser" code="project.p_nouser"/>
<spring:message var="text_p_lead" code="project.p_lead"/>
<div id="content">
	<div class="element">
		<div class="info"><spring:message code="project.home"/></div>
		<div class="content">
			<div class="leftmenu">
				<ul class="vertical-tab">
					<li class="tab"><a href="enterprise/project"><spring:message code="project.home"/></a></li>
					<security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')">
						<li class="tab ${param.menu eq 'newcategory' ? 'active' : ''}">
							<a href="enterprise/category/create"><spring:message code="project.createcategory"/></a>
						</li>
					</security:authorize>
					<li class="empty-tab"></li>
					<li class="tab ${empty projectCategoryId ? 'active' : ''}">
						<a href="enterprise/category"><spring:message code="project.allcategory"/></a>
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
								<a href="enterprise/category/${projectCategory.id}/edit"><img src="img/icon_edit.png" title="${text_edit}"/></a>
								<c:if test="${fn:length(projectCategory.projects) eq 0}">
									<span class="minorspace"></span>
									<a href="enterprise/category/${projectCategory.id}/delete"><img src="img/icon_remove.png" title="${text_remove}"/></a>
								</c:if>
							</c:if>
						</div>
						<div class="content">
							<c:if test="${fn:length(projectCategory.projects) eq 0}">
								<code:info message="project.noprojectsforcategory" arguments="${projectCategory.name}"/>
							</c:if>
							<c:if test="${fn:length(projectCategory.projects) gt 0}">
							<table class="default-table center">
								<tr>
									<th class="fit-cell left"></th>
									<th>${text_p_code}</th>
									<th>${text_p_name}</th>
									<th class="left">${text_p_lead}</th>
									<th>${text_p_nocase}</th>
									<th>${text_p_nouser}</th>
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
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<div id="content">
	<div class="element">
		<div class="info"><spring:message code="project.home"/></div>
		<div class="content">
			<div class="leftmenu">
				<ul class="vertical-tab">
					<li class="tab"><a href="enterprise/project"><spring:message code="project.home"/></a></li>
					<security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')">
						<li class="tab ${edit ? '' : 'active'}">
							<a href="enterprise/category/create"><spring:message code="category.create_category"/></a>
						</li>
					</security:authorize>
					<li class="empty-tab"></li>
					<li class="tab">
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
				<div class="element">
					<div class="title"><spring:message code="${edit ? 'category.update_category' : 'category.create_category'}"/></div>
					<div class="content">
						<c:set var="url">
							<c:if test="${edit}">${projectCategoryId}/edit</c:if>
							<c:if test="${not edit}">create</c:if>
						</c:set>
						<form:form action="enterprise/category/${url}" method="POST" modelAttribute="projectCategory">
						<table class="form-table">
							<tr>
								<td><spring:message code="category.name"/>:</td>
								<td><form:input path="name"/></td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="submit" class="button" value="<spring:message code="${edit ? 'category.update_category' : 'category.create_category'}"/>"/>
								</td>
							</tr>
						</table>
						</form:form>
						<c:if test="${fn:length(projectCategory.projects) gt 0}">
						<div class="subelement">
							<div class="title"><spring:message code="category.related_projects"/>:</div>
							<div class="content">
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
							</div>
						</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
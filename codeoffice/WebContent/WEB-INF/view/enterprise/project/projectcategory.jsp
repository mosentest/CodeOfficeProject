<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="co" uri="http://www.codeoffice.com/colib"%>
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
						<li class="tab ${param.menu eq 'newcategory' ? 'active' : ''}">
							<a href="enterprise/newpcategory"><spring:message code="project.createcategory"/></a>
						</li>
					</security:authorize>
					<li class="empty-tab"></li>
					<li class="tab ${empty projectCategoryId ? 'active' : ''}">
						<a href="enterprise/pcategory"><spring:message code="project.allcategory"/></a>
					</li>
					<c:forEach items="${projectCategoryNames}" var="projectCategory">
						<li class="tab ${projectCategoryId eq projectCategory.id ? 'active' : ''}">
							<a href="enterprise/pcategory/${projectCategory.id}">${projectCategory.name}</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="maincontent">
				<c:forEach items="${projectCategories}" var="projectCategory">
					<div class="mainelement w-100p">
						<div class="title">${projectCategory.name}</div>
						<div class="content">
							<c:if test="${fn:length(projectCategory.projects) eq 0}">
								<div class="info-element imglink">
									<img src="img/info.png"/>
									<span><spring:message code="project.noprojectsforcategory" arguments="${projectCategory.name}"/></span>
								</div>
							</c:if>
							<c:if test="${fn:length(projectCategory.projects) gt 0}">
							<table class="default-table">
								<tr>
									<th class="fit-cell left"></th>
									<th class="fit-cell left"><spring:message code="project.p_code"/></th>
									<th class="center"><spring:message code="project.p_name"/></th>
									<th class="left"><spring:message code="project.p_lead"/></th>
								</tr>
								<c:forEach items="${projectCategory.projects}" var="project">
									<tr>
										<td class="fit-cell"><img src="${project.iconPath == null ? 'img/office/project_icon.png' : project.iconPath}"/></td>
										<td class="fit-cell center"><a href="enterprise/project/${project.code}">${project.code}</a></td>
										<td class="center">${project.name}</td>
										<td><co:user user="${project.lead}"/></td>
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
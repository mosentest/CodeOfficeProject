<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="user"/>
	</jsp:include>
</div>
<spring:message var="text_edit" code="application.edit"/>
<spring:message var="text_delete" code="application.delete"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/um_menu.jsp">
		<jsp:param name="menu" value="usergroups"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title">${userGroup.name}
				<security:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
				<c:if test="${not userGroup.defaultGroup}">
				<input type="button" class="button" onclick="javascript:url('/administration/userGroup/${userGroup.name}/edit.html');" value="<spring:message code="application.edit"/>"/>
				<input type="button" class="button" onclick="javascript:url('/administration/userGroup/${userGroup.name}/delete');" value="<spring:message code="application.delete"/>"/>
				</c:if>
				</security:authorize>
				</div>
				<div class="sub-element-description">${userGroup.description}</div>
			</div>
			<div class="sub-element-content">
				<table class="form-table">
					<tr>
						<td class="form-label-col"><spring:message code="administration.um.group.userCount"/></td>
						<td class="form-input-col">${userGroup.userCount}</td>
					</tr>
					<tr class="form-title-row">
						<td><spring:message code="administration.um.group.GLOBALPERMISSONS"/></td>
						<c:if test="${fn:length(userGroup.globalPermissions) eq 0}">
						<td><spring:message code="application.none"/></td>
						</c:if>
					</tr>
					<c:if test="${fn:length(userGroup.globalPermissions) gt 0 }">
					<tr>
						<td class="form-label-col"></td>
						<td class="form-input-col">
							<ul class="info-ul-list">
							<c:forEach items="${userGroup.globalPermissions}" var="permission">
								<li><a class="link" href="administration/globalPermissions.html#${permission.globalPermission}">
									<spring:message code="${permission.globalPermission.key}"/></a></li>
							</c:forEach>
							</ul>
						</td>
					</tr>
					</c:if>
					<tr class="form-title-row">
						<td><spring:message code="administration.um.group.PROJECTPERMISSONS"/></td>
						<c:if test="${fn:length(userGroup.projectPermissions) eq 0}">
						<td><spring:message code="application.none"/></td>
						</c:if>
					</tr>
					<c:if test="${fn:length(userGroup.projectPermissions) gt 0 }">
					<tr>
						<td class="form-label-col"></td>
						<td class="form-input-col">
							<ul class="info-ul-list">
							<c:forEach items="${userGroup.projectPermissions}" var="permission">
								<li><a class="link" href="administration/projectPermissions.html#${permission.projectPermission}">
									<spring:message code="${permission.projectPermission.key}"/></a></li>
							</c:forEach>
							</ul>
						</td>
					</tr>
					</c:if>
				</table>
				<div class="sep-30"></div>
				<c:if test="${userPage.totalElements gt 0}">
				<div class="sub-element-title">
					<spring:message code="administration.um.group.members"/>
					<security:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
					<c:if test="${not userGroup.defaultGroup}">
					<input type="button" class="button" onclick="javascript:url('/administration/userGroup/${userGroup.name}/member.html');" value="<spring:message code="administration.um.group.editMembers"/>"/>
					</c:if>
					</security:authorize>
				</div>
				<form action="administration/userGroup/${userGroup.name}.html" method="GET">
				<input type="hidden" name="pageIndex" value="${pageIndex}"/>
				<table class="list-table">
					<tr class="list-table-page">
						<td colspan="5"><code:formPage page="${userPage}"/></td>
					</tr>
					<tr class="list-table-header">
						<td><spring:message code="administration.um.user.fullName"/></td>
						<td><spring:message code="administration.um.user.account"/></td>
						<td><spring:message code="administration.um.user.lastLogin"/></td>
						<td><spring:message code="administration.um.user.email"/></td>
						<td><spring:message code="administration.um.user.groups"/></td>
					</tr>
					<c:forEach items="${userPage.content}" var="user">
					<tr class="list-table-item">
						<td><code:user user="${user}" width="20" height="20"/></td>
						<td>${user.account}</td>
						<td><span class="description-info"><fmt:formatDate value="${user.login}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></span></td>
						<td><a class="link" href="mailto:${user.email}">${user.email}</a></td>
						<td><ul class="info-ul-list">
							<c:forEach items="${user.userGroups}" var="userGroup">
								<li><a class="link" href="administration/userGroup/${userGroup.name}.html">${userGroup.name}</a></li>
							</c:forEach>
							</ul>
						</td>
					</tr>
					</c:forEach>
				</table>
				</form>
				</c:if>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
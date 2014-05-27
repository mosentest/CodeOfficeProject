<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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
		<jsp:param name="menu" value="users"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.um.users"/></div>
				<div class="sub-element-description">View and Manage Users</div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<form action="administration/users.html" method="GET">
						<table class="filter-table">
							<tr class="filter-table-title">
								<td><spring:message code="application.filter"/></td>
							</tr>
							<tr class="filter-table-label">
								<td><spring:message code="administration.um.user.filter.accountmail"/></td>
								<td><spring:message code="administration.um.user.filter.name"/></td>
								<td><spring:message code="administration.um.user.filter.ingroup"/></td>
								<td><spring:message code="administration.um.user.filter.userperpage"/></td>
							</tr>
							<tr class="filter-table-input">
								<td><input type="text" name="account" value="${account}"/></td>
								<td><input type="text" name="name" value="${name}"/></td>
								<td><select name="groupFilter">
									<option value=""><spring:message code="application.any"/></option>
									<c:forEach items="${groups}" var="group">
										<option value="${group.id}" ${group.id eq groupFilter ? 'selected=selected' : ''}>${group.name}</option>
									</c:forEach>
								</select></td>
								<td><select name="pageSize">
									<c:forEach items="${supportedListSize}" var="size">
										<option value="${size}" ${size eq pageSize ? 'selected=selected' : ''}>${size}</option>
									</c:forEach>
								</select></td>
							</tr>
							<tr class="filter-table-input">
								<td colspan="4"><input class="button" type="submit" onclick="submitForm(this);" value="<spring:message code="application.filter"/>" /></td>
							</tr>
						</table>
					</form>
				</div>
				<div class="list-content">
					<c:if test="${userPage.totalElements eq 0}"><spring:message code="administration.um.no_user_to_display"/></c:if>
					<c:if test="${userPage.totalElements gt 0}">
					<form action="administration/users.html" method="GET">
						<input type="hidden" id="pageIndex" name="pageIndex" value="${pageIndex}"/>
						<c:if test="${not empty pageSize}"><input type="hidden" name="pageSize" value="${pageSize}"/></c:if>
						<c:if test="${not empty groupFilter}"><input type="hidden" name="groupFilter" value="${groupFilter}"/></c:if>
						<c:if test="${not empty account}"><input type="hidden" name="account" value="${account}"/></c:if>
						<c:if test="${not empty name}"><input type="hidden" name="name" value="${name}"/></c:if>
						<table class="list-table">
							<tr class="list-table-page">
								<td colspan="9"><code:formPage page="${userPage}"/></td>
							</tr>
							<tr class="list-table-header">
								<td><spring:message code="administration.um.user.fullName"/></td>
								<td><spring:message code="administration.um.user.account"/></td>
								<td><spring:message code="administration.um.user.lastLogin"/></td>
								<td><spring:message code="administration.um.user.email"/></td>
								<td><spring:message code="administration.um.user.groups"/></td>
								<td><spring:message code="administration.um.user.globalPermissions"/></td>
								<td><spring:message code="administration.um.user.operations"/></td>
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
								<td><ul class="info-ul-list">
									<c:forEach items="${user.globalPermissions}" var="permission">
										<li><a class="link" href="administration/globalPermission/${permission}.html"><spring:message code="${permission.key}"/></a></li>
									</c:forEach>
									</ul>
								</td>
								<td>
									<a class="link" href="#"><spring:message code="administration.um.groups"/></a><span class="minorspace">&#183;</span>
									<a class="link" href="#"><spring:message code="administration.um.projectRoles"/></a>
									<security:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
									<c:if test="${not userGroup.defaultGroup}">
										<span class="minorspace">&#183;</span>
										<a class="link" href="#">${text_edit}</a><span class="minorspace">&#183;</span>
										<a class="link" href="#">${text_delete}</a>
									</c:if>
									</security:authorize>
								</td>
							</tr>
							</c:forEach>
						</table>
					</form>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
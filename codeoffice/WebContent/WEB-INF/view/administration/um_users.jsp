<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="user"/>
	</jsp:include>
</div>
<spring:message var="text_edit" code="application.edit"/>
<spring:message var="text_delete" code="application.delete"/>

<spring:message var="text_firstName" code="entity.user.firstName"/>
<spring:message var="text_lastName" code="entity.user.lastName"/>
<spring:message var="text_account" code="entity.user.account"/>
<spring:message var="text_login" code="entity.user.login"/>
<spring:message var="text_email" code="entity.user.email"/>
<spring:message var="text_userGroups" code="entity.user.userGroups"/>
<spring:message var="text_globalPermissions" code="entity.user.globalPermissions"/>
<spring:message var="text_operations" code="application.operations"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/um_menu.jsp">
		<jsp:param name="menu" value="users"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.um.user.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.um.user.description"/></div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<form action="administration/users.html" method="GET">
						<table class="filter-table">
							<tr class="filter-table-title">
								<td><spring:message code="application.filter"/></td>
							</tr>
							<tr class="filter-table-label">
								<td><spring:message code="administration.um.user.filter.accountAndMail"/></td>
								<td><spring:message code="administration.um.user.filter.name"/></td>
								<td><spring:message code="administration.um.user.filter.inGroup"/></td>
								<td><spring:message code="administration.um.user.filter.userPerPage"/></td>
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
					<c:if test="${userPage.totalElements eq 0}">no user</c:if>
					<c:if test="${userPage.totalElements gt 0}">
						<c:set var="params">
							<c:if test="${not empty pageSize}">pageSize=${pageSize},</c:if>
							<c:if test="${not empty groupFilter}">groupFilter=${groupFilter},</c:if>
							<c:if test="${not empty account}">account=${account},</c:if>
							<c:if test="${not empty name}">name=${name},</c:if>
						</c:set>
						<c:set var="pageParams">
							${params}
							<c:if test="${not empty sort}">sort=${sort},</c:if>
							<c:if test="${not empty descending}">descending=true,</c:if>
						</c:set>
						<c:set var="url" value="administration/users.html"/>
						<table class="list-table">
							<tr class="list-table-page">
								<td colspan="9"><code:formPage page="${userPage}" url="${url}" params="${pageParams}"/></td>
							</tr>
							<tr class="list-table-header">
								<td></td>
								<td><code:sortableColumn columnName="${text_firstName}" sortColumn="firstName"/></td>
								<td><code:sortableColumn columnName="${text_lastName}" sortColumn="lastName"/></td>
								<td><code:sortableColumn columnName="${text_login}" sortColumn="lastLogin"/></td>
								<td><code:sortableColumn columnName="${text_account}" sortColumn="account"/></td>
								<td><code:sortableColumn columnName="${text_email}" sortColumn="email"/></td>
								<td>${text_userGroups}</td>
								<td>${text_operations}</td>
							</tr>
							<c:forEach items="${userPage.content}" var="user">
							<tr class="list-table-item">
								<td><img src="assets/img/core/default-avatar.png" width="20" height="20"/></td>
								<td>${user.firstName}</td>
								<td>${user.lastName}</td>
								<td><span class="description-info"><fmt:formatDate value="${user.login}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></span></td>
								<td>${user.account}</td>
								<td><a class="link" href="mailto:${user.email}">${user.email}</a></td>
								<td>
									<ul class="info-ul-list">
									<c:forEach items="${user.userGroups}" var="userGroup">
										<li><a class="link" href="administration/userGroup.html?group=${userGroup.name}">${userGroup.name}</a></li>
									</c:forEach>
									</ul>
								</td>
								<td>
									<a class="link" href="#">${text_globalPermissions}</a><span class="minorspace">&#183;</span>
									<a class="link" href="#"><spring:message code="entity.user.projectRoles"/></a>
									<c:if test="${not userGroup.defaultGroup}">
										<span class="minorspace">&#183;</span>
										<a class="link" href="#">${text_edit}</a><span class="minorspace">&#183;</span>
										<a class="link" href="#">${text_delete}</a>
									</c:if>
								</td>
							</tr>
							</c:forEach>
						</table>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
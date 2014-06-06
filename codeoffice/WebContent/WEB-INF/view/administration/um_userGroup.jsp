<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="user"/>
	</jsp:include>
</div>
<c:set var="maskedURL" value="${codefunction:maskURL(userGroup.name)}"/>
<script>
	function submitFilter() {
		var urlString = 'administration/userGroup.html?group=${maskedURL}&';
		urlString += ('query=' + $("input[name='query']").val());
		url(urlString);
	}
</script>
<spring:message var="text_fullName" code="entity.user.fullName"/>
<spring:message var="text_groups" code="entity.user.userGroups"/>
<spring:message var="text_account" code="entity.user.account"/>
<spring:message var="text_email" code="entity.user.email"/>
<spring:message var="text_login" code="entity.user.login"/>

<spring:message var="text_edit" code="application.edit"/>
<spring:message var="text_delete" code="application.delete"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/um_menu.jsp">
		<jsp:param name="menu" value="usergroups"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title imglink">
				<span>${userGroup.name}</span>
				<c:if test="${not userGroup.defaultGroup}">
					<form class="inline-form" action="administration/userGroup/delete?group=${maskedURL}" method="POST">
					<input type="submit" onclick="javascript:confirmSubmit(event, 'Delete?');" class="button" value="<spring:message code="application.delete"/>"/>
					</form>
				</c:if>
				</div>
				<div class="sub-element-description">${userGroup.description}</div>
			</div>
			<div class="sub-element-content">
				<table class="form-table">
					<tr>
						<td class="form-label-col"><spring:message code="entity.userGroup.userCount"/></td>
						<td class="form-input-col">${userGroup.userCount}</td>
					</tr>
					<tr class="form-title-row">
						<td><spring:message code="entity.userGroup.globalPermissions"/></td>
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
				</table>
				<div class="sep-30"></div>
				<c:if test="${userPage.totalElements gt 0}">
				<div class="sub-element-title">
					<spring:message code="entity.userGroup.users"/>
					<security:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
					<c:if test="${not userGroup.defaultGroup}">
					<input type="button" class="button" onclick="javascript:url('/administration/userGroup/manage.html?group=${maskedURL}');" value="<spring:message code="entity.userGroup.users.manage"/>"/>
					</c:if>
					</security:authorize>
				</div>
				<div class="filter-content">
					<table class="filter-table">
						<tr class="filter-table-title">
							<td colspan="2"><spring:message code="application.filter"/></td>
						</tr>
						<tr class="filter-table-label">
							<td colspan="2"><spring:message code="administration.um.usergroup.filter.name"/></td>
						</tr>
						<tr class="filter-table-input">
							<td><input type="text" name="query" value="${query}"/></td>
							<td><input class="button" type="button" onclick="javascript:submitFilter();" value="<spring:message code="application.filter"/>" /></td>
						</tr>
					</table>
				</div>
				<table class="list-table">
					<c:set var="params">
						group=${maskedURL},
						<c:if test="${not empty pageSize}">pageSize=${pageSize},</c:if>
						<c:if test="${not empty query}">query=${query},</c:if>
					</c:set>
					<c:set var="pageParams">
						${params}
						<c:if test="${not empty sort}">sort=${sort},</c:if>
						<c:if test="${not empty descending}">descending=true,</c:if>
					</c:set>
					<c:set var="url" value="administration/userGroup.html"/>
					<tr class="list-table-page">
						<td colspan="5"><code:formPage page="${userPage}" url="${url}" params="${pageParams}"/></td>
					</tr>
					<tr class="list-table-header">
						<td>${text_fullName}</td>
						<td><code:sortableColumn columnName="${text_account}" sortColumn="account"/></td>
						<td><code:sortableColumn columnName="${text_login}" sortColumn="login"/></td>
						<td><code:sortableColumn columnName="${text_email}" sortColumn="email"/></td>
						<td>${text_groups}</td>
					</tr>
					<c:forEach items="${userPage.content}" var="user">
					<tr class="list-table-item">
						<td><code:user user="${user}" width="20" height="20"/></td>
						<td>${user.account}</td>
						<td><span class="description-info"><fmt:formatDate value="${user.login}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></span></td>
						<td><a class="link" href="mailto:${user.email}">${user.email}</a></td>
						<td><ul class="info-ul-list">
							<c:forEach items="${user.userGroups}" var="userGroup">
								<li><a class="link" href="administration/userGroup.html?group=${maskedURL}">${userGroup.name}</a></li>
							</c:forEach>
							</ul>
						</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
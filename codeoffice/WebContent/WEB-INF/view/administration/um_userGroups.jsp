<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="user"/>
	</jsp:include>
</div>
<spring:message var="text_edit_members" code="administration.um.group.editMembers"/>
<spring:message var="text_delete" code="application.delete"/>

<spring:message var="text_name" code="administration.um.group.name"/>
<spring:message var="text_description" code="administration.um.group.description"/>
<spring:message var="text_userCount" code="administration.um.group.userCount"/>
<spring:message var="text_globalPermissions" code="administration.um.group.globalPermissions"/>
<spring:message var="text_operations" code="administration.um.group.operations"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/um_menu.jsp">
		<jsp:param name="menu" value="usergroups"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.um.userGroups"/></div>
				<div class="sub-element-description">View and Manage User Groups</div>
			</div>
			
			<div class="sub-element-content">
				<div class="filter-content">
					<div class="fl-l">
						<form action="administration/userGroups.html" method="GET">
							<table class="filter-table">
								<tr class="filter-table-title">
									<td><spring:message code="application.filter"/></td>
								</tr>
								<tr class="filter-table-label">
									<td><spring:message code="administration.um.group.filter.name"/></td>
									<td><spring:message code="administration.um.group.filter.groupperpage"/></td>
								</tr>
								<tr class="filter-table-input">
									<td><input type="text" name="name" value="${name}"/></td>
									<td><select name="pageSize">
										<c:forEach items="${supportedListSize}" var="size">
											<option value="${size}" ${size eq pageSize ? 'selected=selected' : ''}>${size}</option>
										</c:forEach>
									</select></td>
								</tr>
								<tr class="filter-table-input">
									<td colspan="2"><input class="button" type="submit" onclick="submitForm(this);" value="<spring:message code="application.filter"/>" /></td>
								</tr>
							</table>
						</form>
					</div>
					<div class="fl-l">
						<form:form action="administration/userGroup/create" modelAttribute="userGroup" method="POST">
							<table class="filter-table">
								<tr class="filter-table-title">
									<td><spring:message code="administration.um.group.addgroup"/></td>
								</tr>
								<tr class="filter-table-label">
									<td><spring:message code="administration.um.group.name"/><span class="icon-required">&nbsp;</span><span class="minorspace"></span>
									<span class="description-info">format ([a-zA-Z]+((-)?[a-zA-Z])+)</span></td>
								</tr>
								<tr class="filter-table-input">
									<td><form:input class="long-field" path="name"/></td>
								</tr>
								<tr class="filter-table-input">
									<td><input class="button" type="submit" value="<spring:message code="administration.um.group.addgroup"/>" /></td>
								</tr>
							</table>
						</form:form>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="list-content">
					<c:set var="params">
						<c:if test="${not empty pageSize}">pageSize=${pageSize},</c:if>
						<c:if test="${not empty name}">name=${name},</c:if>
					</c:set>
					<c:set var="pageParams">
						${params}
						<c:if test="${not empty sort}">sort=${sort},</c:if>
						<c:if test="${not empty descending}">descending=true,</c:if>
					</c:set>
					<c:set var="url" value="administration/userGroups.html"/>
					<table class="list-table">
						<tr class="list-table-page">
							<td colspan="6"><code:formPage page="${userGroupPage}" url="${url}" params="${pageParams}"/></td>
						</tr>
						<tr class="list-table-header">
							<td><code:sortableColumn columnName="${text_name}" sortColumn="name"/></td>
							<td>${text_description}</td>
							<td><code:sortableColumn columnName="${text_userCount}" sortColumn="userCount"/></td>
							<td>${text_globalPermissions}</td>
							<td>${text_operations}</td>
						</tr>
						<c:forEach items="${userGroupPage.content}" var="userGroup">
						<tr class="list-table-item">
							<td class="info-value"><a class="link" href="administration/userGroup.html?group=${userGroup.name}">${userGroup.name}</a></td>
							<td>${userGroup.description}</td>
							<td class="info-value">${userGroup.userCount}</td>
							<td><ul class="info-ul-list">
								<c:forEach items="${userGroup.globalPermissions}" var="permission">
									<li><a class="link" href="administration/globalPermissions.html#${permission.globalPermission}">
										<spring:message code="${permission.globalPermission.key}"/></a></li>
								</c:forEach>
								</ul>
							</td>
							<td>
								<security:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
								<c:if test="${not userGroup.defaultGroup}">
									<a class="link" href="administration/userGroup/manage.html?group=${userGroup.name}">${text_edit_members}</a><span class="minorspace">&#183;</span>
									<a class="link" href="javascript:remoteSubmit(event, 'administration/userGroup/delete?group=${userGroup.name}', 'Delete?');">${text_delete}</a>
								</c:if>
								</security:authorize>
							</td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<form id="remoteForm" method="POST"></form>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
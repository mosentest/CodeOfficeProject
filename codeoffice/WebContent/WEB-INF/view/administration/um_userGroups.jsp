<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="user"/>
	</jsp:include>
</div>
<spring:message var="text_project_permissions" code="administration.um.group.projectPermissions"/>
<spring:message var="text_edit_members" code="administration.um.group.editMembers"/>
<spring:message var="text_edit" code="application.edit"/>
<spring:message var="text_delete" code="application.delete"/>
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
									<td><spring:message code="administration.um.group.name"/><span class="minorspace"></span>
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
					<c:if test="${userGroupPage.totalElements eq 0}"><spring:message code="administration.um.no_group_to_display"/></c:if>
					<c:if test="${userGroupPage.totalElements gt 0}">
					<form action="administration/users.html" method="GET">
						<input type="hidden" id="pageIndex" name="pageIndex" value="${pageIndex}"/>
						<c:if test="${not empty pageSize}"><input type="hidden" name="pageSize" value="${pageSize}"/></c:if>
						<c:if test="${not empty name}"><input type="hidden" name="name" value="${name}"/></c:if>
						<table class="list-table">
							<tr class="list-table-page">
								<td colspan="5"><code:formPage page="${userGroupPage}"/></td>
							</tr>
							<tr class="list-table-header">
								<td><spring:message code="administration.um.group.name"/></td>
								<td><spring:message code="administration.um.group.description"/></td>
								<td><spring:message code="administration.um.group.userCount"/></td>
								<td><spring:message code="administration.um.group.globalPermissions"/></td>
								<td><spring:message code="administration.um.group.operations"/></td>
							</tr>
							<c:forEach items="${userGroupPage.content}" var="userGroup">
							<tr class="list-table-item">
								<td class="info-value"><a class="link" href="administration/userGroup/${userGroup.name}.html">${userGroup.name}</a></td>
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
									<a class="link" href="#">${text_project_permissions}</a>
									<security:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
									<c:if test="${not userGroup.defaultGroup}">
										<span class="minorspace">&#183;</span>
										<a class="link" href="administration/userGroup/manage/${userGroup.name}.html">${text_edit_members}</a><span class="minorspace">&#183;</span>
										<a class="link" href="#">${text_edit}</a><span class="minorspace">&#183;</span>
										<a class="link" href="javascript:del('administrator/userGroup/${userGroup.name}/delete')">${text_delete}</a>
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
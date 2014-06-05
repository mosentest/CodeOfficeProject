<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="system"/>
	</jsp:include>
</div>
<script>
	$(document).ready(function() {
		$('#autocomplete').autocomplete({
			minLength: 2,
			source: function(request, response) {
				$.ajax({
					url: "ajax/search/users",
					type: 'GET',
					responseType: 'json',
					data: {
						query: $('#autocomplete').val()
					},
					success: function(data) {
						response(data);
					}
				});
			},
			focus: function(event, ui) {
				$('#autocomplete').val(ui.item.firstName + ", " + ui.item.lastName);
				return false;
			},
			select: function(event, ui) {
				$('#user-id').val(ui.item.id);
				return false;
			}
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a class='autocomplete-link'><span class='plain-label-info'>" + item.firstName + ", " + item.lastName + "</span>" + 
					"<span class='plain-label-description'>(" + item.email + ")</span></a>").appendTo(ul);
		};
	});
</script>
<spring:message var="text_view_users" code="administration.um.globalpermission.viewUser"/>
<spring:message var="text_reset" code="application.reset"/>
<spring:message var="text_remove" code="application.remove"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/system_menu.jsp">
		<jsp:param name="menu" value="globalpermission"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.system.globalPermission.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.system.globalPermission.description"/></div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<div class="fl-l">
					<form action="administration/globalPermission/addGroup" id="add-group-form" method="GET">
						<table class="filter-table">
							<tr class="filter-table-title">
								<td><spring:message code="entity.userGroup.add"/></td>
							</tr>
							<tr class="filter-table-label">
								<td><spring:message code="entity.globalPermissionSettings.globalPermission"/></td>
								<td><spring:message code="entity.userGroup"/></td>
							</tr>
							<tr class="filter-table-input">
								<td>
									<select name="permission" style="width: 250px; ">
										<c:forEach items="${globalPermissionSettings}" var="permission">
											<option value="${permission.globalPermission}"><spring:message code="${permission.globalPermission.key}"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select name="group">
										<c:forEach items="${userGroups}" var="group">
											<option value="${group.name}">${group.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr class="filter-table-input">
								<td colspan="2"><input class="button" type="submit" value="<spring:message code="application.grantPermission"/>" /></td>
							</tr>
						</table>
					</form>
					</div>
					<div class="fl-l">
					<form action="administration/globalPermission/addUser" id="add-user-form" method="GET">
						<table class="filter-table">
							<tr class="filter-table-title">
								<td><spring:message code="entity.user.add"/></td>
							</tr>
							<tr class="filter-table-label">
								<td><spring:message code="entity.globalPermissionSettings.globalPermission"/></td>
								<td><spring:message code="entity.user"/></td>
							</tr>
							<tr class="filter-table-input">
								<td>
									<select name="permission" style="width: 250px; ">
										<c:forEach items="${globalPermissionSettings}" var="permission">
											<option class="select-permission" value="${permission.globalPermission}"><spring:message code="${permission.globalPermission.key}"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<input type="hidden" name="user" id="user-id"/>
									<input type="text" name="name" id="autocomplete"/>
								</td>
							</tr>
							<tr class="filter-table-input">
								<td colspan="2"><input class="button" type="submit" value="<spring:message code="application.grantPermission"/>" /></td>
							</tr>
						</table>
					</form>
					</div>
					<div class="clearfix"></div>
					<a class="link" href="administration/globalPermission/scheme.html"><spring:message code="administration.system.globalpermission.viewScheme"/></a>
				</div>
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="entity.globalPermissionSettings.globalPermission"/></td>
						<td><spring:message code="entity.globalPermissionSettings.userGroups"/></td>
						<td><spring:message code="entity.globalPermissionSettings.users"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${globalPermissionSettings}" var="permission">
					<tr class="list-table-item" id="${permission.globalPermission}">
						<c:set var="description">${permission.globalPermission.key}.description</c:set>
						<td>
							<span class="title-info"><spring:message code="${permission.globalPermission.key}"/></span><br/>
							<span class="description-info"><spring:message code="${description}"/></span></td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${permission.userGroups}" var="group">
									<li>${group.name}<br/>
									<span class="info-ul-list-span">
									<a class="link" href="administration/userGroup.html?group=${group.name}">${text_view_users}</a><span class="minorspace">&#183;</span>
									<a class="link" href="administration/globalPermission/removeGroup?permission=${permission.globalPermission}&group=${group.name}">${text_remove}</a></span>
									</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${permission.users}" var="user">
									<li><code:user user="${user}" width="20" height="20" showLink="false"/><br/>
									<span class="info-ul-list-span"><a class="link" href="administration/globalPermission/removeUser?permission=${permission.globalPermission}&user=${user.id}">${text_remove}</a></span>
									</li>
								</c:forEach>
							</ul>
						</td>
						<td><a class="link" href="administration/globalPermission/reset?permission=${permission.globalPermission}">${text_reset}</a></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="project"/>
	</jsp:include>
</div>
<script>
	function removeLabel(id) {
		$('#label-' + id).remove();
	}
	
	$(document).ready(function() {
		$('#autocomplete').autocomplete({
			minLength: 3,
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
				var label = "<div id='label-" + ui.item.id + "' class='user-label closable-user-label'>";
				label += "<span class='user-label-info'>" + ui.item.firstName + ", " + ui.item.lastName + "</span>";
				label += "<span class='user-label-description'>(" + ui.item.email + ")</span>";
				label += "<span class='spanlink imglink' onclick='javascript:removeLabel(\"" + ui.item.id + "\");' title='remove'><img width=\"12\" height=\"12\" src=\"assets/img/information/icon-close.png\"/></span>";
				label += "<input type='hidden' name='users' value='" + ui.item.id + "'/>";
				label += "</div>";
				$('#user').append(label);
				$('#autocomplete').val('');
				return false;
			}
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a class='autocomplete-link'><span class='plain-label-info'>" + item.firstName + ", " + item.lastName + "</span>" + 
					"<span class='plain-label-description'>(" + item.email + ")</span></a>").appendTo(ul);
		};
	});
</script>
<c:set var="maskedURL" value="${codefunction:maskURL(permissionScheme.name)}"/>
<spring:message var="text_reset" code="application.reset"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/project_menu.jsp">
		<jsp:param name="menu" value="permissionscheme"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title imglink">
					<span>${permissionScheme.name}</span>
					<input type="submit" onclick="javascript:url('administration/permissionScheme/clone?scheme=${maskedURL}');"
						class="button" value="<spring:message code="application.clone"/>"/>
					<input type="submit" onclick="javascript:url('administration/permissionScheme/resetAll?scheme=${maskedURL}');"
						class="button" value="reset all"/>
				</div>
				<div class="sub-element-description">${permissionScheme.description}</div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<form action="administration/permissionScheme/associate" method="POST">
						<table class="filter-table">
							<tr class="filter-table-title">
								<td><spring:message code="application.associate"/></td>
							</tr>
							<tr class="filter-table-label">
								<td><spring:message code="entity.projectPermissionSettings.projectPermission"/></td>
								<td><spring:message code="entity.projectPermissionSettings.users"/></td>
								<td><spring:message code="entity.projectPermissionSettings.userGroups"/></td>
								<td><spring:message code="entity.projectPermissionSettings.projectRoles"/></td>
							</tr>
							<tr class="filter-table-input">
								<td>
								<input type="hidden" name="scheme" value="${permissionScheme.name}"/>
									<select name="permission" style="width: 250px; " size="6">
										<c:forEach items="${permissionScheme.projectPermissionSettings}" var="permission">
											<option value="${permission.projectPermission}"><spring:message code="${permission.projectPermission.key}"/></option>
										</c:forEach>
									</select>
								</td>
								<td class="form-top-col"><input type="text" id="autocomplete"/></td>
								<td>
									<select name="groups" multiple size="6">
										<c:forEach items="${userGroups}" var="group">
											<option value="${group.id}">${group.name}</option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select name="roles" multiple size="6">
										<c:forEach items="${projectRoles}" var="role">
											<option value="${role.id}">${role.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="4" id="user"></td>
							</tr>
							<tr class="filter-table-input">
								<td colspan="4"><input class="button" type="submit" value="<spring:message code="application.grantPermission"/>" /></td>
							</tr>
						</table>
					</form>
				</div>
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="entity.projectPermissionSettings.projectPermission"/></td>
						<td><spring:message code="entity.projectPermissionSettings.userGroups"/></td>
						<td><spring:message code="entity.projectPermissionSettings.projectRoles"/></td>
						<td><spring:message code="entity.projectPermissionSettings.users"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${permissionScheme.projectPermissionSettings}" var="settings">
					<tr class="list-table-item">
						<td>
							<span class="title-info"><spring:message code="${settings.projectPermission.key}"/></span><br />
							<c:set var="description" scope="page">${settings.projectPermission.key}.description</c:set>
							<span class="description-info"><spring:message code="${description}"/></span>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${settings.userGroups}" var="group">
									<li><a class="link" href="administration/userGroup.html?group=${codefunction:maskURL(group.name)}">${group.name}</a></li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${settings.projectRoles}" var="role">
									<li>${role.name}</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${settings.users}" var="user">
									<li><code:user user="${user}" width="20" height="20"/></li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<a class="link" href="administration/permissionScheme/reset?scheme=${maskedURL}&permission=${settings.projectPermission}">${text_reset}</a>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<form id="remoteForm" method="POST"></form>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
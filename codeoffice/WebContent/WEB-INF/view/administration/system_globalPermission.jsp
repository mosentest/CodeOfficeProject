<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="system"/>
	</jsp:include>
</div>
<script>
	$(document).ready(function() {
		$("#add-group-select").change(function() {
			var selected = $(this).find(':selected').val();
			if(selected != '') {
				$(this).find('.select-option-default').remove();
				$.ajax({
					url: "administration/globalPermission/" + selected + "/availableGroups",
					type: 'GET',
					responseType: 'json',
					success: function(data) {
						var select = $("select[name='userGroupName']");
						$("select[name='userGroupName'] option").remove();
						$.each(data, function(key, value) {
							console.log(value);
							select.append($("<option></option>").attr('value', value.name).text(value.name));
						});
					}
				});
			}
		});
		$('#add-user-select').change(function() {
			$(this).find('.select-option-default').remove();
			$('#autocomplete').removeAttr('disabled');
			$("#add-user-form input[name='id']").remove();
		});
		$('#autocomplete').autocomplete({
			minLength: 2,
			source: function(request, response) {
				;
				$.ajax({
					url: "administration/globalPermission/" + $("#add-group-select").find(':selected').val() + "/availableUsers",
					type: 'GET',
					responseType: 'json',
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
				$('#add-user-form').append("<input type=\"hidden\" name=\"id\" value=\"" + ui.item.id + "\"/>");
				return false;
			}
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a class='autocomplete-link'><span class='plain-label-info'>" + item.firstName + ", " + item.lastName + "</span>" + 
					"<span class='plain-label-description'>(" + item.email + ")</span></a>").appendTo(ul);
		};
	});
	function submitAddGroup(event) {
		if ($("select[name='userGroupName']").val() == null) {
			event.preventDefault();
			return;
		}
		$('#add-group-form').attr('action', 'administration/globalPermission/' + $("select[name='globalPermission']").find(':selected').val() + '/addGroup');
	}
	function submitAddUser(event) {
		if ($("#add-user-form input[name='id']").length == 0) {
			event.preventDefault();
			return;
		}
		$('#add-user-form').attr('action', 'administration/globalPermission/' + $("select[name='globalPermission']").find(':selected').val() + '/addUser');
	}
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
				<div class="sub-element-title"><spring:message code="administration.system.globalPermissionSettings"/></div>
				<div class="sub-element-description">Edit global permissions settings.</div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<div class="fl-l">
					<form action="l" id="add-group-form" method="GET">
						<table class="filter-table">
							<tr class="filter-table-title">
								<td><spring:message code="administration.um.globalpermission.addGroup"/></td>
							</tr>
							<tr class="filter-table-label">
								<td><spring:message code="administration.um.globalpermission.permission"/></td>
								<td><spring:message code="administration.um.globalpermission.group"/></td>
							</tr>
							<tr class="filter-table-input">
								<td>
									<select id="add-group-select" name="globalPermission" style="width: 250px; ">
										<option value="" class="select-option-default">---<spring:message code="administration.um.user.globalPermissions"/>---</option>
										<c:forEach items="${globalPermissionSettings}" var="permission">
											<option class="select-permission" value="${permission.globalPermission}"><spring:message code="${permission.globalPermission.key}"/></option>
										</c:forEach>
									</select>
								</td>
								<td><select name="userGroupName"></select></td>
							</tr>
							<tr class="filter-table-input">
								<td colspan="2"><input class="button" type="submit" onclick="submitAddGroup(event);" value="<spring:message code="administration.um.globalpermission.grantPermission"/>" /></td>
							</tr>
						</table>
					</form>
					</div>
					<div class="fl-l">
					<form action="" id="add-user-form" method="GET">
						<table class="filter-table">
							<tr class="filter-table-title">
								<td><spring:message code="administration.um.globalpermission.addUser"/></td>
							</tr>
							<tr class="filter-table-label">
								<td><spring:message code="administration.um.globalpermission.permission"/></td>
								<td><spring:message code="administration.um.globalpermission.user"/></td>
							</tr>
							<tr class="filter-table-input">
								<td>
									<select id="add-user-select" name="globalPermission" style="width: 250px; ">
										<option value="" class="select-option-default">---<spring:message code="administration.um.user.globalPermissions"/>---</option>
										<c:forEach items="${globalPermissionSettings}" var="permission">
											<option class="select-permission" value="${permission.globalPermission}"><spring:message code="${permission.globalPermission.key}"/></option>
										</c:forEach>
									</select>
								</td>
								<td><input type="text" name="name" id="autocomplete" disabled="disabled"/></td>
							</tr>
							<tr class="filter-table-input">
								<td colspan="2"><input class="button" type="submit" onclick="submitAddUser(event);" value="<spring:message code="administration.um.globalpermission.grantPermission"/>" /></td>
							</tr>
						</table>
					</form>
					</div>
					<div class="clearfix"></div>
				</div>
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.um.globalpermission.permission"/></td>
						<td><spring:message code="administration.um.globalpermission.groups"/></td>
						<td><spring:message code="administration.um.globalpermission.users"/></td>
						<td><spring:message code="administration.um.globalpermission.operations"/></td>
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
									<a class="link" href="administration/userGroup/${group.name}.html">${text_view_users}</a><span class="minorspace">&#183;</span>
									<a class="link" href="administration/globalPermission/${permission.globalPermission}/removeGroup?userGroupName=${group.name}">${text_remove}</a></span>
									</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${permission.users}" var="user">
									<li><code:user user="${user}" width="20" height="20" showLink="false"/><br/>
									<span class="info-ul-list-span"><a class="link" href="administration/globalPermission/${permission.globalPermission}/removeUser?id=${user.id}">${text_remove}</a></span>
									</li>
								</c:forEach>
							</ul>
						</td>
						<td><a class="link" href="administration/globalPermission/${permission.globalPermission}/reset">${text_reset}</a>	</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
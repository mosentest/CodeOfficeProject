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
<c:set var="maskedURL" value="${codefunction:maskURL(notificationScheme.name)}"/>
<spring:message var="text_viewUsers" code="application.application.viewUsers"/>
<spring:message var="text_reset" code="application.reset"/>
<spring:message var="text_associate" code="application.associate"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/project_menu.jsp">
		<jsp:param name="menu" value="permissionscheme"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title imglink">
					<span>${notificationScheme.name}</span>
					<input type="submit" onclick="javascript:url('administration/notificationScheme/clone?scheme=${maskedURL}');"
						class="button" value="<spring:message code="application.clone"/>"/>
					<input type="submit" onclick="javascript:url('administration/notificationScheme/resetAll?scheme=${maskedURL}');"
						class="button" value="<spring:message code="application.resetAll"/>"/>
				</div>
				<div class="sub-element-description">${notificationScheme.description}</div>
			</div>
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="entity.notification"/></td>
						<td><spring:message code="entity.notification.userGroups"/></td>
						<td><spring:message code="entity.notification.projectRoles"/></td>
						<td><spring:message code="entity.notification.users"/></td>
						<td><spring:message code="entity.notification.emails"/></td>
						<td><spring:message code="entity.notification.others"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${notificationScheme.notifications}" var="notification">
					<tr class="list-table-item">
						<td>
							<span class="title-info"><spring:message code="${notification.notificationType.key}"/></span><br />
							<c:set var="description" scope="page">${notification.notificationType.key}.description</c:set>
							<span class="description-info"><spring:message code="${description}"/></span>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${notification.userGroups}" var="group">
								<li class="imglink">
									<span>${group.name}</span><br />
									<a class="link" href="administration/userGroup.html?group=${codefunction:maskURL(group.name)}">${text_viewUsers}</a>
									<span class="minorspace">&#183;</span>
									<a class="link" href="administration/notificationScheme/deleteGroup?scheme=${maskURL}&group=${group.id}">${text_delete}</a>
								</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${notification.projectRoles}" var="role">
								<li class="imglink">
									<span>${role.name}</span>
									<a class="link" href="administration/notificationScheme/deleteRole?scheme=${maskURL}&role=${role.id}">${text_delete}</a>
								</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${notification.users}" var="user">
								<li class="imglink">
									<code:user user="${user}" width="20" height="20"/><br />
									<a class="link" href="administration/notificationScheme/deleteUser?scheme=${maskURL}&user=${user.id}">${text_delete}</a>
								</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${notification.emails}" var="email">
								<li class="imglink">
									<span>${email}</span><br/>
									<a class="link" href="administration/notificationScheme/deleteEmail?scheme=${maskURL}&email=${email}">${text_delete}</a>
								</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<ul class="info-ul-list">
							<c:if test="${notification.allGroups}"><li><spring:message code="entity.notification.allGroups"/></li></c:if>
							<c:if test="${notification.allRoles}"><li><spring:message code="entity.notification.allRoles"/></li></c:if>
							<c:if test="${notification.assignee}"><li><spring:message code="entity.notification.assignee"/></li></c:if>
							<c:if test="${notification.reporter}"><li><spring:message code="entity.notification.reporter"/></li></c:if>
							<c:if test="${notification.projectLead}"><li><spring:message code="entity.notification.projectLead"/></li></c:if>
							<c:if test="${notification.componentLead}"><li><spring:message code="entity.notification.componentLead"/></li></c:if>
							<c:if test="${notification.watchers}"><li><spring:message code="entity.notification.watchers"/></li></c:if>
							</ul>
						</td>
						<td>
							<a class="link" href="administration/notificationScheme/associate.html?scheme=${maskedURL}&notification=${notification.notificationType}">${text_associate}</a>
							<a class="link" href="administration/notificationScheme/reset?scheme=${maskedURL}&notification=${notification.notificationType}">${text_reset}</a>
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
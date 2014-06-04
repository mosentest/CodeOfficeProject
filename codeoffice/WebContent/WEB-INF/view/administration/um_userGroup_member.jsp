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
<script>
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
				var label = "<div id='label-" + ui.item.id + "' class='plain-label closable-label'>";
				label += "<span class='plain-label-info'>" + ui.item.firstName + ", " + ui.item.lastName + "</span>";
				label += "<span class='plain-label-description'>(" + ui.item.email + ")</span>";
				label += "<span class='spanlink imglink' onclick='javascript:removeLabel(\"" + ui.item.id + "\");' title='remove'><img width=\"12\" height=\"12\" src=\"assets/img/information/icon-close.png\"/></span>";
				label += "<input type='hidden' name='newUser' value='" + ui.item.id + "'/>";
				label += "<input type='hidden' name='id' value='" + ui.item.id + "'/>";
				label += "</div>";
				$('#new').append(label);
				return false;
			}
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a class='autocomplete-link'><span class='plain-label-info'>" + item.firstName + ", " + item.lastName + "</span>" + 
					"<span class='plain-label-description'>(" + item.email + ")</span></a>").appendTo(ul);
		};
	});
	
	function removeLabel(id) {
		$('#label-' + id).remove();
	}
	
	function moveToNew(id) {
		moveTo('new', id);
	}
	
	function moveToOriginal(id) {
		$('#input-' + id).attr('name', 'users');
		moveTo('original', id);
		$('#label-' + id + ' .icon-close').attr('onclick', "javascript:moveToRemoved('" + id +  "');");
	}
	
	function moveToRemoved(id) {
		$('#input-' + id).attr('name', 'removedUser');
		moveTo('removed', id);
		$('#label-' + id + ' .icon-close').attr('onclick', "javascript:moveToOriginal('" + id +  "');");
	}
	
	function moveTo(destination, id) {
		$('#label-' + id).appendTo('#' + destination);
	}
</script>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/um_menu.jsp">
		<jsp:param name="menu" value="usergroups"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title">${userGroupDTO.name}</div>
				<div class="sub-element-description">${userGroupDTO.description}</div>
			</div>
			<div class="sub-element-content">
				<form:form action="administration/userGroup/manage.html?group=${userGroupDTO.name}" modelAttribute="userGroupDTO" method="POST">
				<table class="form-table">
					<tr>
						<td class="form-label-col"><spring:message code="administration.um.group.adduser"/>:</td>
						<td class="form-input-col"><input type="text" id="autocomplete" name="search" placeholder="Enter Name/Email here.."/></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.um.group.newmembers"/>:</td>
						<td class="form-input-col" id="new"></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.um.group.removedmembers"/>:</td>
						<td class="form-input-col" id="removed"></td>
					</tr>
					<tr>
						<td class="form-label-col"><spring:message code="administration.um.group.members"/>:</td>
						<td class="form-input-col" id="original">
							<c:forEach items="${userGroupDTO.members}" var="user">
								<input type="hidden" id="input-${user.id}" name="user" value="${user.id}"/>
								<div id="label-${user.id}" class="plain-label closable-label">
									<span class="plain-label-info">${user.firstName}, ${user.lastName}</span>
									<span class="plain-label-description">(${user.email})</span>
									<span class="spanlink imglink" onclick="javascript:moveToRemoved('${user.id}')" title="remove"><img width="12" height="12" src="assets/img/information/icon-close.png"/></span>
								</div>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td class="form-label-col"></td>
						<td class="form-input-col imglink">
							<input type="submit" class="button" value="<spring:message code="application.save"/>"/>
							<a class="link" href="administration/userGroups.html"><spring:message code="application.cancel"/></a>
						</td>
					</tr>
				</table>
				</form:form>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
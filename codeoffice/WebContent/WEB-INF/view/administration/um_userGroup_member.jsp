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
		$("input[name='removedUsers']").click(function() {
			if ($("input[name='removedUsers']:not(:checked)").length > 0) {
				$('#select-all').prop('checked', false);
			} else {
				$('#select-all').prop('checked', true);
			}
		});
		$('#select-all').click(function() {
			$("input[name='removedUsers']").prop('checked', $(this).is(":checked"));
		});
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
				label += "<input type='hidden' name='newUsers' value='" + ui.item.id + "'/>";
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
</script>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/um_menu.jsp">
		<jsp:param name="menu" value="usergroups"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title">${userGroup.name}</div>
				<div class="sub-element-description">${userGroup.description}</div>
			</div>
			<div class="sub-element-content">
				<form:form action="administration/userGroup/manage?group=${userGroup.name}" modelAttribute="userGroup" method="POST">	
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
						<td class="form-label-col"></td>
						<td class="form-input-col imglink">
							<input type="submit" class="button" value="<spring:message code="application.save"/>"/>
							<a class="link" href="administration/userGroups.html"><spring:message code="application.cancel"/></a>
						</td>
					</tr>
				</table>
				<c:if test="${userPage.totalElements eq 0}"><code:info type="info" title="administration.um.usergroup.noUsers"/></c:if>
				<c:if test="${userPage.totalElements gt 0}">
				<div>Select users to delete</div>
				<c:set var="params">
					<c:if test="${not empty name}">query=${query},</c:if>
						<c:if test="${not empty sort}">sort=${sort},</c:if>
						<c:if test="${not ascending}">ascending=${ascending}</c:if>
				</c:set>
				<table class="list-table">
					<tr class="list-table-page"><code:formPage page="${userPage}" url="administration/userGroup/manage.html" params="${params}"/></tr>
					<tr class="list-table-header">
						<td class="center"><input type="checkbox" id="select-all"/></td>
						<td></td>
						<td><spring:message code="administration.um.usergroup.firstName"/></td>
						<td><spring:message code="administration.um.usergroup.lastName"/></td>
						<td><spring:message code="administration.um.usergroup.email"/></td>
					</tr>
					<c:forEach items="${userPage.content}" var="user">
					<tr class="list-table-item">
						<td class="center"><input type="checkbox" name="removedUsers" value="${user.id}"/></td>
						<td class="center"><img src="assets/img/core/default-avatar.png" width="20" height="20"/></td>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>${user.email}</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>
				</form:form>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
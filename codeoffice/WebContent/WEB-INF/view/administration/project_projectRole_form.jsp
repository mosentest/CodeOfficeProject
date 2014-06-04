<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
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
				var label = "<div id='label-" + ui.item.id + "' class='user-label closable-user-label'>";
				label += "<span class='user-label-info'>" + ui.item.firstName + ", " + ui.item.lastName + "</span>";
				label += "<span class='user-label-description'>(" + ui.item.email + ")</span>";
				label += "<span class='spanlink imglink' onclick='javascript:removeLabel(\"" + ui.item.id + "\");' title='remove'><img width=\"12\" height=\"12\" src=\"assets/img/information/icon-close.png\"/></span>";
				label += "<input type='hidden' name='newUsers' value='" + ui.item.id + "'/>";
				label += "</div>";
				$('#new').append(label);
				$('#autocomplete').val('');
				return false;
			}
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a class='autocomplete-link'><span class='plain-label-info'>" + item.firstName + ", " + item.lastName + "</span>" + 
					"<span class='plain-label-description'>(" + item.email + ")</span></a>").appendTo(ul);
		};
	});
</script>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/project_menu.jsp">
		<jsp:param name="menu" value="projectrole"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.project.projectrole.editProjectRole"/></div>
			</div>
			<div class="sub-element-content">
				<form:form action="administration/projectRole/edit?role=${projectRole.name}" modelAttribute="projectRole" method="POST">
					<form:hidden path="id"/>
					<form:hidden path="pageIndex"/>
					<form:hidden path="query"/>
					<table class="form-table">
						<code:formError errors="${formErrors}"/>
						<tr>
							<td class="form-label-col"><spring:message code="administration.project.projectrole.name"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="form-input-col"><form:input path="name"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.project.projectrole.description"/>:</td>
							<td class="form-input-col"><form:textarea path="description" cols="30" rows="3"/></td>
						</tr>
						<tr>
							<td class="form-label-col"><spring:message code="administration.project.projectrole.addMembers"/>:</td>
							<td class="form-input-col"><input type="text" id="autocomplete" placeholder="search by name/email.."/></td>
						</tr>
						<tr>
							<td></td>
							<td id="new"></td>
						</tr>
						<tr>
							<td></td>
							<td class="imglink">
								<input type="submit" class="largebutton button" value="<spring:message code="application.save"/>"/>
								<a class="link" href="administration/projectRoles.html"><spring:message code="application.cancel"/></a>
							</td>
						</tr>
					</table>
					<c:if test="${userPage.totalElements eq 0}"><code:info type="info" title="administration.project.projectrole.noUsers"/></c:if>
					<c:if test="${userPage.totalElements gt 0}">
					<div>Select users to delete</div>
					<table class="list-table">
						<c:set var="params">
							role=${projectRole.name}&
							<c:if test="${not empty query}">query=${query}</c:if>
						</c:set>
						<tr class="list-table-page"><code:formPage page="${userPage}" url="administration/projectRole/edit.html" params="${params}"/></tr>
						<tr class="list-table-header">
							<td class="center"><input type="checkbox" id="select-all"/></td>
							<td></td>
							<td><spring:message code="administration.project.projectrole.firstName"/></td>
							<td><spring:message code="administration.project.projectrole.lastName"/></td>
							<td><spring:message code="administration.project.projectrole.email"/></td>
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
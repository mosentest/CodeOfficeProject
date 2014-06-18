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
				label += "<span class='user-label-info'>" + ui.item.firstName + ", " + ui.item.lastName + "</span>";
				label += "<span class='user-label-description'>(" + ui.item.email + ")</span>";
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
	function submitFilter() {
		var urlString = 'administration/userGroup/manage.html?group=${codefunction:maskURL(userGroup.name)}&';
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
				<div class="sub-element-title">${userGroup.name}</div>
				<div class="sub-element-description">${userGroup.description}</div>
			</div>
			<div class="sub-element-content">
				<form:form action="administration/userGroup/manage?group=${userGroup.name}" modelAttribute="userGroup" method="POST">	
				<table class="form-table">
					<tr>
						<td class="form-label-col"><spring:message code="entity.user.add"/>:</td>
						<td class="form-input-col"><input type="text" id="autocomplete" name="search" placeholder="Enter Name/Email here.."/></td>
					</tr>
					<tr>
						<td class="form-label-col"></td>
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
				<c:if test="${userPage.totalElements eq 0}"><code:info type="info" title="application.noItemsFound"/></c:if>
				<c:if test="${userPage.totalElements gt 0}">
				<div>Select users to delete</div>
				<table class="list-table">
					<c:set var="params">
						group=${userGroup.name},
						<c:if test="${not empty pageSize}">pageSize=${pageSize},</c:if>
						<c:if test="${not empty query}">query=${query},</c:if>
					</c:set>
					<c:set var="pageParams">
						${params}
						<c:if test="${not empty sort}">sort=${sort},</c:if>
						<c:if test="${not empty descending}">descending=true,</c:if>
					</c:set>
					<c:set var="url" value="administration/userGroup/manage.html"/>
					<tr class="list-table-page">
						<td colspan="6"><code:formPage page="${userPage}" url="${url}" params="${pageParams}"/></td>
					</tr>
					<tr class="list-table-header">
						<td class="center"><input type="checkbox" id="select-all"/></td>
						<td>${text_fullName}</td>
						<td><code:sortableColumn columnName="${text_account}" sortColumn="account"/></td>
						<td><code:sortableColumn columnName="${text_login}" sortColumn="login"/></td>
						<td><code:sortableColumn columnName="${text_email}" sortColumn="email"/></td>
						<td>${text_groups}</td>
					</tr>
					<c:forEach items="${userPage.content}" var="user">
					<tr class="list-table-item">
						<td class="center"><input type="checkbox" name="removedUsers" value="${user.id}"/></td>
						<td><code:user user="${user}" width="20" height="20"/></td>
						<td>${user.account}</td>
						<td><span class="description-info"><fmt:formatDate value="${user.login}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></span></td>
						<td><a class="link" href="mailto:${user.email}">${user.email}</a></td>
						<td><ul class="info-ul-list">
							<c:forEach items="${user.userGroups}" var="userGroup">
								<li><a class="link" href="administration/userGroup.html?group=${codefunction:maskURL(userGroup.name)}">${userGroup.name}</a></li>
							</c:forEach>
							</ul>
						</td>
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
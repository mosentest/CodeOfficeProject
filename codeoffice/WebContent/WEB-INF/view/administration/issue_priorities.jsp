<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="issue"/>
	</jsp:include>
</div>
<script>
	function selectTypeIcon(icon) {
		$('#typeIcon-icon').attr('src', 'assets/img/office/priority/' + icon + ".png");
		$('#typeIcon-text').text(icon);
		$('.image-select-ul-list').css({'left' : '-9999px'});
		$("input[name='icon']").val(icon);
	}
	function showColorPanel(link) {
		var colorPanel = $('#color-panel');
		if (colorPanel.is(':visible')) {
			$(link).text('[Show Color Panel]');
			colorPanel.hide();
		} else {
			$(link).text('[Hide Color Panel]');
			colorPanel.show();
		}
	}
	$(document).ready(function() {
		$('.color-info-choose').click(function() {
			$('.color-info-choose').click(function() {
				var colorInfo = $('#color-info');
				var colorClass = $(this).attr('class').split(' ')[1];
				colorInfo.attr('class', colorInfo.attr('class').split(' ')[0] + " " + colorClass);
				$("input[name='color']").val(colorClass);
			});
		});
	});
</script>
<spring:message var="text_edit" code="application.edit" />
<spring:message var="text_delete" code="application.delete" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="priority"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.issue.issuepriority.title"/></div>
				<div class="sub-element-description"><spring:message code="administration.issue.issuepriority.description"/></div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<div class="fl-l">
					<form:form action="administration/priority/create" modelAttribute="issuePriority" method="POST">
						<table class="minor-form-table">
							<tr class="minor-form-title-row">
								<td colspan="2"><spring:message code="entity.issuePriority.create"/></td>
							</tr>
							<code:formError errors="${formErrors}"/>
							<tr>
								<td class="minor-form-label-col"><spring:message code="entity.issuePriority.name"/><span class="icon-required">&nbsp;</span></td>
								<td class="minor-form-input-col"><form:input path="name"/></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="entity.issuePriority.description"/></td>
								<td class="minor-form-input-col"><form:input path="description" class="long-field" /></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="entity.issuePriority.icon"/><span class="icon-required">&nbsp;</span></td>
								<td class="minor-form-input-col">
									<form:hidden path="icon" value="${icons[0]}"/>
									<span class="image-select-indicator imglink" id="typeIcon">
										<img id="typeIcon-icon" src="assets/img/office/priority/${icons[0]}.png"/>
										<span id="typeIcon-text" class="text">${icons[0]}</span>
										<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
									</span>
									<div class="image-select-ul-list">
										<ul>
											<c:forEach items="${icons}" var="icon">
											<li class="imglink" onclick="javascript:selectTypeIcon('${icon}');">
												<img src="assets/img/office/priority/${icon}.png"/>
												<span class="text">${icon}</span>
											</li>
											</c:forEach>
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="entity.issuePriority.color"/><span class="icon-required">&nbsp;</span></td>
								<td class="minor-form-input-col imglink">
									<form:hidden path="color" value="color-palette-primary-blue"/>
									<span class="color-info color-palette-primary-blue" id="color-info" ></span>
									<a class="link" href="javascript:void(0);" onclick="javascript:showColorPanel(this);">[Show Color Panel]</a>
								</td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" class="button" value="<spring:message code="application.create"/>"/></td>
							</tr>
						</table>
					</form:form>
					</div>
					<div class="fl-l" id="color-panel" style="display: none;"><code:colorTable/></div>
					<div class="clearfix"></div>
				</div>
				<c:if test="${fn:length(issuePriorities) eq 0}"><code:info type="info" title="application.noItemsFound"/></c:if>
				<c:if test="${fn:length(issuePriorities) gt 0}">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="entity.issuePriority.name"/></td>
						<td><spring:message code="entity.issuePriority.description"/></td>
						<td><spring:message code="entity.issuePriority.icon"/></td>
						<td><spring:message code="entity.issuePriority.color"/></td>
						<td><spring:message code="entity.issuePriority.order"/></td>
						<td><spring:message code="application.operations"/></td>
					</tr>
					<c:forEach items="${issuePriorities}" var="priority">
					<tr class="list-table-item">
						<td class="title-info">${priority.name}</td>
						<td class="description-info">${priority.description}</td>
						<td><img src="assets/img/office/priority/${priority.icon}.png"/></td>
						<td><span class="color-info ${priority.color}">&nbsp;</span></td>
						<td>${priority.order}</td>
						<td>
							<a class="link" href="administration/priority/edit.html?priority=${codefunction:maskURL(priority.name)}">${text_edit}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/priority/delete?priority=${codefunction:maskURL(priority.name)}', 'Delete?');">${text_delete}</a>
						</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<form id="remoteForm" method="POST"></form>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
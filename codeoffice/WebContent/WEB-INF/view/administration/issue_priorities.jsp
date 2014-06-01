<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
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
			var colorInfo = $('#color-info');
			console.log(colorInfo.attr('class'));
			console.log(colorInfo.attr('class').split(' ')[0]);
			console.log($(this).attr('class').split(' ')[1]);
			colorInfo.attr('class', colorInfo.attr('class').split(' ')[0] + " " + $(this).attr('class').split(' ')[1]);
			$("input[name='color']").val(rgb2hex(colorInfo.css('background-color')).slice(1));
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
				<div class="sub-element-title"><spring:message code="administration.issue.priorities"/></div>
				<div class="sub-element-description">Edit your enterprise global settings.</div>
			</div>
			<div class="sub-element-content">
				<div class="filter-content">
					<div class="fl-l">
					<form:form action="administration/priority/create" modelAttribute="issuePriority" method="POST">
						<table class="minor-form-table">
							<tr class="minor-form-title-row">
								<td colspan="2"><spring:message code="administration.issue.issuepriority.createPriority"/></td>
							</tr>
							<code:formError errors="${formErrors}"/>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.issue.issuepriority.name"/><span class="icon-required">&nbsp;</span></td>
								<td class="minor-form-input-col"><form:input path="name"/></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.issue.issuepriority.description"/></td>
								<td class="minor-form-input-col"><form:input path="description" class="long-field" /></td>
							</tr>
							<tr>
								<td class="minor-form-label-col"><spring:message code="administration.issue.issuepriority.icon"/><span class="icon-required">&nbsp;</span></td>
								<td class="minor-form-input-col">
									<form:hidden path="icon"/>
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
								<td class="minor-form-label-col"><spring:message code="administration.issue.issuepriority.color"/><span class="icon-required">&nbsp;</span></td>
								<td class="minor-form-input-col imglink">
									<form:hidden path="color" value="205081"/>
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
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.issue.issuepriority.name"/></td>
						<td><spring:message code="administration.issue.issuepriority.description"/></td>
						<td><spring:message code="administration.issue.issuepriority.icon"/></td>
						<td><spring:message code="administration.issue.issuepriority.color"/></td>
						<td><spring:message code="administration.issue.issuepriority.order"/></td>
						<td><spring:message code="administration.issue.issuepriority.operations"/></td>
					</tr>
					<c:forEach items="${issuePriorities}" var="priority">
					<tr class="list-table-item">
						<td class="title-info">${priority.name}</td>
						<td class="description-info">${priority.description}</td>
						<td><img src="assets/img/office/priority/${priority.icon}.png"/></td>
						<td><span class="color-info" style="background-color: #${priority.color};">&nbsp;</span></td>
						<td>${priority.order}</td>
						<td>
							<a class="link" href="administration/priority/edit.html?priority=${priority.name}">${text_edit}</a>
							<span class="minorspace">&#183;</span>
							<a class="link" href="javascript:remoteSubmit(event, 'administration/priority/delete?priority=${priority.name}', 'Delete?');">${text_delete}</a>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
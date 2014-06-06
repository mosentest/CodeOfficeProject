<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="issue"/>
	</jsp:include>
</div>
<script>
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

	function selectTypeIcon(icon) {
		$('#typeIcon-icon').attr('src', 'assets/img/office/status/' + icon + ".png");
		$('#typeIcon-text').text(icon);
		$('.image-select-ul-list').css({'left' : '-9999px'});
		$("input[name='icon']").val(icon);
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
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="status"/>
	</jsp:include>
	<div id="maincontent">
		<div class="panel-content">
			<div class="panel-element">
				<div class="panel-element-title">
					<div class="panel-element-info">${issueStatus.name}</div>
					<div class="panel-element-description">${issueStatus.description}</div>
				</div>
				<div class="panel-element-content">
					<form:form action="administration/status/edit?status=${codefunction:maskURL(issueStatus.name)}" modelAttribute="issueStatus" method="POST">
					<table class="minor-form-table">
						<form:hidden path="id"/>
						<code:formError errors="${formErrors}"/>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.issueStatus.name"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col"><form:input path="name"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.issueStatus.description"/>:</td>
							<td class="minor-form-input-col"><form:textarea path="description" rows="3" cols="30"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.issueStatus.icon"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col">
								<form:hidden path="icon"/>
								<span class="image-select-indicator imglink" id="typeIcon">
									<img id="typeIcon-icon" src="assets/img/office/status/${issueStatus.icon}.png"/>
									<span id="typeIcon-text" class="text">${issueStatus.icon}</span>
									<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
								</span>
								<div class="image-select-ul-list">
									<ul>
										<c:forEach items="${icons}" var="icon">
										<li class="imglink" onclick="javascript:selectTypeIcon('${icon}');">
											<img src="assets/img/office/status/${icon}.png"/>
											<span class="text">${icon}</span>
										</li>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.issueStatus.color"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col imglink">
								<form:hidden path="color"/>
								<span class="color-info ${issueStatus.color}" id="color-info" ></span>
								<a class="link" href="javascript:void(0);" onclick="javascript:showColorPanel(this);">[Show Color Panel]</a>
							</td>
						</tr>
						<tr id="color-panel" style="display: none;"><td colspan="2"><code:colorTable/></td></tr>
						<tr>
							<td></td>
							<td>
								<input type="submit" class="button largebutton" value="<spring:message code="application.save"/>">
								<a class="link" href="administration/status.html"><spring:message code="application.cancel"/></a>
							</td>
						</tr>
					</table>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
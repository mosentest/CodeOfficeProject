<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="issue"/>
	</jsp:include>
</div>
<script>
	function selectTypeIcon(icon) {
		$('#typeIcon-icon').attr('src', 'assets/img/office/type/' + icon + ".png");
		$('#typeIcon-text').text(icon);
		$('.image-select-ul-list').css({'left' : '-9999px'});
		$("input[name='icon']").val(icon);
	}
</script>
<spring:message var="text_standard" code="entity.issueType.standard" />
<spring:message var="text_subtask" code="entity.issueType.subTask" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="type"/>
	</jsp:include>
	<div id="maincontent">
		<div class="panel-content">
			<div class="panel-element">
				<div class="panel-element-title">
					<div class="panel-element-info">${issueType.name}</div>
					<div class="panel-element-description">${issueType.description}</div>
				</div>
				<div class="panel-element-content">
					<form:form action="administration/${issueType.standard ? 'type' : 'subtask'}/edit?type=${issueType.name}" modelAttribute="issueType" method="POST">
					<table class="minor-form-table">
						<form:hidden path="id"/>
						<code:formError errors="${formErrors}"/>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.issueType.name"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col"><form:input path="name"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.issueType.description"/>:</td>
							<td class="minor-form-input-col"><form:textarea path="description" rows="3" cols="30"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="entity.issueType.icon"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col">
								<form:hidden path="icon"/>
								<span class="image-select-indicator imglink" id="typeIcon">
									<img id="typeIcon-icon" src="assets/img/office/type/${issueType.icon}.png"/>
									<span id="typeIcon-text" class="text">${issueType.icon}</span>
									<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/>
								</span>
								<div class="image-select-ul-list">
									<ul>
										<c:forEach items="${icons}" var="icon">
										<li class="imglink" onclick="javascript:selectTypeIcon('${icon}');">
											<img src="assets/img/office/type/${icon}.png"/>
											<span class="text">${icon}</span>
										</li>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="submit" class="button largebutton" value="<spring:message code="application.save"/>">
								<a class="link" href="administration/${issueType.standard ? 'types' : 'subtasks'}.html"><spring:message code="application.cancel"/></a>
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
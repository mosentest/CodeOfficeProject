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
<spring:message var="text_standard" code="administration.issue.issuetype.standard" />
<spring:message var="text_subtask" code="administration.issue.issuetype.subtask" />
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/issue_menu.jsp">
		<jsp:param name="menu" value="link"/>
	</jsp:include>
	<div id="maincontent">
		<div class="panel-content">
			<div class="panel-element">
				<div class="panel-element-title">
					<div class="panel-element-info">${issueLink.name}</div>
				</div>
				<div class="panel-element-content">
					<form:form action="administration/link/edit?link=${issueLink.name}" modelAttribute="issueLink" method="POST">
					<table class="minor-form-table">
						<form:hidden path="id"/>
						<code:formError errors="${formErrors}"/>
						<tr>
							<td class="minor-form-label-col"><spring:message code="administration.issue.issuelink.name"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col"><form:input path="name"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="administration.issue.issuelink.outwardDescription"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col"><form:input path="outwardLink" cssClass="long-field"/></td>
						</tr>
						<tr>
							<td class="minor-form-label-col"><spring:message code="administration.issue.issuelink.inwardDescription"/><span class="icon-required">&nbsp;</span>:</td>
							<td class="minor-form-input-col"><form:input path="inwardLink" cssClass="long-field"/></td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="submit" class="button largebutton" value="<spring:message code="application.save"/>">
								<a class="link" href="administration/links.html"><spring:message code="application.cancel"/></a>
							</td>
						</tr>
					</table>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<form id="remoteForm" method="POST"></form>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
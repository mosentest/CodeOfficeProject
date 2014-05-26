<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="system"/>
	</jsp:include>
</div>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/system_menu.jsp">
		<jsp:param name="menu" value="advancedglobal"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.system.advancedGlobalSettings"/></div>
				<div class="sub-element-description">Edit your Advanced Global settings.</div>
			</div>
			<div class="sub-element-content">
				<form:form action="administration/advancedGlobal/edit" modelAttribute="advancedGlobalSettings" method="POST">
					<table class="key-value-table">
						<form:hidden path="id"/>
						<tr class="key-value-title-row">
						<td colspan="2"><spring:message code="administration.advancedglobal.ADVANCEDGLOBALSETTINGS"/></td>
						</tr>
						
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.attachmentNumberOfZipEntries"/></td>
							<td class="key-value-value-col"><form:input path="attachmentNumberOfZipEntries"/></td>
						</tr>
						<tr class="key-value-description-row"><td>description</td><td>range(10 - 50)</td></tr>
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.clonePrefix"/></td>
							<td class="key-value-value-col"><form:input path="clonePrefix"/></td>
						</tr>
						<tr class="key-value-description-row"><td colspan="2">description</td></tr>
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.dateTimeJavaFormat"/></td>
							<td class="key-value-value-col"><form:input path="dateTimeJavaFormat"/></td>
						</tr>
						<tr class="key-value-description-row"><td colspan="2">description</td></tr>
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.dateTimeJavascriptFormat"/></td>
							<td class="key-value-value-col"><form:input path="dateTimeJavascriptFormat"/></td>
						</tr>
						<tr class="key-value-description-row"><td colspan="2">description</td></tr>
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.dateJavaFormat"/></td>
							<td class="key-value-value-col"><form:input path="dateJavaFormat"/></td>
						</tr>
						<tr class="key-value-description-row"><td colspan="2">description</td></tr>
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.dateJavascriptFormat"/></td>
							<td class="key-value-value-col"><form:input path="dateJavascriptFormat"/></td>
						</tr>
						<tr class="key-value-description-row"><td colspan="2">description</td></tr>
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.tableColumnsSubtask"/></td>
							<td class="key-value-value-col"><form:input path="tableColumnsSubtask"/></td>
						</tr>
						<tr class="key-value-description-row"><td colspan="2">description</td></tr>
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.ascendingIssueActionOrder"/></td>
							<td class="key-value-value-col">
								<form:radiobutton path="ascendingIssueActionOrder" value="true"/><spring:message code="application.ascending"/>
								<form:radiobutton path="ascendingIssueActionOrder" value="false"/><spring:message code="application.descending"/>
							</td>
						</tr>
						<tr class="key-value-description-row"><td colspan="2">description</td></tr>
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.autocompleteMaxResults"/></td>
							<td class="key-value-value-col"><form:input path="autocompleteMaxResults"/></td>
						</tr>
						<tr class="key-value-description-row"><td colspan="2">description</td></tr>
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.activityStreamMaxResults"/></td>
							<td class="key-value-value-col"><form:input path="activityStreamMaxResults"/></td>
						</tr>
						<tr class="key-value-description-row"><td colspan="2">description</td></tr>
						<tr>
							<td class="key-value-key-col"><spring:message code="administration.advancedglobal.issueStreamMaxResults"/></td>
							<td class="key-value-value-col"><form:input path="issueStreamMaxResults"/></td>
						</tr>
						<tr class="key-value-description-row"><td colspan="2">description</td></tr>
						<tr>
							<td></td>
							<td><input type="submit" class="largebutton button" value="<spring:message code="application.save"/>"/></td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
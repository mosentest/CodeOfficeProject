<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
				<div class="sub-element-title">
					<spring:message code="administration.system.advancedGlobalSettings"/>
					<input type="button" class="button" onclick="javascript:url('/administration/advancedGlobal/edit.html');" value="<spring:message code="application.edit"/>"/>
				</div>
				<div class="sub-element-description">Edit your Advanced Global settings.</div>
			</div>
			<div class="sub-element-content">
				<table class="key-value-table">
					<tr class="key-value-title-row">
						<td colspan="2"><spring:message code="administration.advancedglobal.ADVANCEDGLOBALSETTINGS"/></td>
					</tr>
					
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.attachmentNumberOfZipEntries"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.attachmentNumberOfZipEntries}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.clonePrefix"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.clonePrefix}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.dateTimeJavaFormat"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.dateTimeJavaFormat}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.dateTimeJavascriptFormat"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.dateTimeJavascriptFormat}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.dateJavaFormat"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.dateJavaFormat}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.dateJavascriptFormat"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.dateJavascriptFormat}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.tableColumnsSubtask"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.tableColumnsSubtask}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.ascendingIssueActionOrder"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.ascendingIssueActionOrder}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.autocompleteMaxResults"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.autocompleteMaxResults}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.activityStreamMaxResults"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.activityStreamMaxResults}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
					<tr>
						<td class="key-value-key-col"><spring:message code="administration.advancedglobal.issueStreamMaxResults"/></td>
						<td class="key-value-value-col">${advancedGlobalSettings.issueStreamMaxResults}</td>
					</tr>
					<tr class="key-value-description-row"><td colspan="2">description</td></tr>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
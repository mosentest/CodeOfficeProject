<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
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
				<div class="sub-element-title imglink">
					<span><spring:message code="administration.system.advancedglobal.title"/></span>
					<input type="button" class="button" onclick="javascript:url('/administration/advancedGlobal/edit.html');" value="<spring:message code="application.edit"/>"/>
				</div>
				<div class="sub-element-description"><spring:message code="administration.system.advancedglobal.description"/></div>
			</div>
			<div class="sub-element-content">
				<table class="key-value-table">
					<tr class="key-value-title-row">
						<td colspan="2"><spring:message code="administration.system.advancedglobal.ADVANCEDGLOBALSETTINGS"/></td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.attachmentNumberOfZipEntries"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.attachmentNumberOfZipEntries}</td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.clonePrefix"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.clonePrefix}</td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.dateTimeJavaFormat"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.dateTimeJavaFormat}</td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.dateTimeJavascriptFormat"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.dateTimeJavascriptFormat}</td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.dateJavaFormat"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.dateJavaFormat}</td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.dateJavascriptFormat"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.dateJavascriptFormat}</td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.tableColumnsSubtask"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.tableColumnsSubtask}</td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.ascendingIssueActionOrder"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.ascendingIssueActionOrder}</td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.autocompleteMaxResults"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.autocompleteMaxResults}</td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.activityStreamMaxResults"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.activityStreamMaxResults}</td>
					</tr>
					<tr>
						<td class="key-value-key-col">
							<span class="title-info"><spring:message code="entity.advancedGlobalSettings.issueStreamMaxResults"/></span><br />
							<span class="description-info">Description</span>
						</td>
						<td class="key-value-value-col">${advancedGlobalSettings.issueStreamMaxResults}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />